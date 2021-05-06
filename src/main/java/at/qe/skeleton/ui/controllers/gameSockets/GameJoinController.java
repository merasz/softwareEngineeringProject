package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.repositories.UserRepository;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Controller
@Scope("application")
@CDIContextRelated
public class GameJoinController {

    @CDIAutowired
    private WebSocketManager webSocketManager;

    @Autowired
    private UserRepository userRepository;

    private List<String> sendTo = new CopyOnWriteArrayList<>();
    private List<PlayerAvailability> playerAvailability = new CopyOnWriteArrayList<>();
    private Map<Integer, Boolean> allTeamsReady = new ConcurrentHashMap<>();
    private Map<Integer, Set<Team>> teamTaken = new ConcurrentHashMap<>();
    private Map<Integer, Set<Team>> teamAccepted = new ConcurrentHashMap<>();

    // initialize when game creator joins
    public void onJoin(Game game) {
        List<User> playerCircle = userRepository.findAllByRaspberry(game.getRaspberry());
        sendTo.addAll(playerCircle.stream().map(User::getUsername).collect(Collectors.toList()));
        teamAccepted.put(game.getGameId(), ConcurrentHashMap.newKeySet());

        List<String> assignedPlayers = game.getTeamList().stream().
                flatMap(t -> t.getTeamPlayers().stream().map(User::getUsername)).collect(Collectors.toList());

        for (User u : playerCircle) {
            PlayerAvailability pA = new PlayerAvailability(u, game);
            if (assignedPlayers.contains(u.getUsername())) {
                pA.setAvailable(false);
            }
            playerAvailability.add(pA);
        }

        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    // update when team leaders assign players
    public void onSelect(User user, Game game) {
        playerAvailability.stream()
                .filter(pa -> pa.getUsername().equals(user.getUsername()) && pa.getGame().equals(game))
                .forEach(pa -> pa.setAvailable(false));
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public void takeTeam(Game game, Team team) {
        teamTaken.get(game.getGameId()).add(team);
    }

    public List<PlayerAvailability> getPlayerAvailability(Game game) {
        return Collections.unmodifiableList(getGamePlayerAvailabilities(game));
    }

    public boolean getAllTeamsReady(Game game, User user) {
        updateTeamsReady(game);
        teamAccepted.get(game.getGameId()).add(game.getTeamList().stream()
                .filter(t -> t.getTeamPlayers().contains(user)).findFirst().get());
        return this.allTeamsReady.get(game.getGameId())
                && teamAccepted.get(game.getGameId()).size() == game.getTeamList().size();
    }

    public void setAllTeamsReady() {
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    private void updateTeamsReady(Game game) {
        this.allTeamsReady.put(game.getGameId(),
                getGamePlayerAvailabilities(game).stream().noneMatch(PlayerAvailability::isAvailable));
    }

    private List<PlayerAvailability> getGamePlayerAvailabilities(Game game) {
        return playerAvailability.stream().filter(pa -> pa.getGame().equals(game)).collect(Collectors.toList());
    }

    public boolean teamAvailable(Game game, Team team) {
        teamTaken.computeIfAbsent(game.getGameId(), k -> ConcurrentHashMap.newKeySet());
        return !teamTaken.get(game.getGameId()).contains(team);
    }

}
