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
    private Map<Game, Boolean> allTeamsReady = new ConcurrentHashMap<>();
    private Map<Game, Set<Team>> teamTaken = new ConcurrentHashMap<>();
    private Map<Game, Set<Team>> teamAccepted = new ConcurrentHashMap<>();

    // initialize when game creator joins
    public void onJoin(Game game) {
        List<User> playerCircle = userRepository.findAllByRaspberry(game.getRaspberry());
        sendTo.addAll(playerCircle.stream().map(User::getUsername).collect(Collectors.toList()));
        teamAccepted.put(game, ConcurrentHashMap.newKeySet());

        List<User> assignedPlayers = game.getTeamList().stream()
                .flatMap(t -> t.getTeamPlayers().stream()).collect(Collectors.toList());
        //System.out.println("assigned: " + assignedPlayers);
        for (User u : playerCircle) {
            PlayerAvailability pA = new PlayerAvailability(u, game);
            if (assignedPlayers.contains(u)) {
                pA.setAvailable(false);
            }
            playerAvailability.add(pA);
        }

        /*
        userRepository.findAllByRaspberry(game.getRaspberry()).stream()
                .map(u -> new PlayerAvailability(u, game))
                .collect(Collectors.toList())
                .forEach(pa -> {
                    if (game.getTeamList().stream()
                            .flatMap(t -> t.getTeamPlayers().stream())
                            .collect(Collectors.toList()).contains(pa.getUser())) {
                        pa.setAvailable(false);
                    }
                    playerAvailability.add(pa);
                });
        */

        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    // update when team leaders assign players
    public void onSelect(User user, Game game) {
        playerAvailability.stream()
                .filter(pa -> pa.getUsername().equals(user.getUsername()) && pa.getGame().equals(game))
                .forEach(pa -> pa.setAvailable(false));
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public void updateJoinChannel() {
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public void takeTeam(Game game, Team team) {
        teamTaken.computeIfAbsent(game, k -> ConcurrentHashMap.newKeySet());
        teamTaken.get(game).add(team);
    }

    public List<PlayerAvailability> getPlayerAvailability(Game game) {
        return Collections.unmodifiableList(getGamePlayerAvailabilities(game));
    }

    public boolean updateReadyToStart(Game game, User user) {
        //updateTeamsReady(game);
        teamAccepted.get(game).add(game.getTeamList().stream()
                .filter(t -> t.getTeamPlayers().contains(user)).findFirst().get());
        //System.out.println(teamAccepted.get(game).stream().map(Team::getTeamName).collect(Collectors.toList()));
        //System.out.println(allTeamsReady.get(game));
        return allReadyToStart(game);
    }

    public boolean allReadyToStart(Game game) {
        return this.allTeamsReady.get(game)
                && teamAccepted.get(game).size() == game.getTeamList().size();
    }

    public void updateTeamsReady(Game game) {
        this.allTeamsReady.put(game,
                getGamePlayerAvailabilities(game).stream().filter(pa -> !pa.isAvailable()).count() == game.getCountPlayers());
    }

    private List<PlayerAvailability> getGamePlayerAvailabilities(Game game) {
        return playerAvailability.stream().filter(pa -> pa.getGame().equals(game)).collect(Collectors.toList());
    }

    public boolean teamAvailable(Game game, Team team) {
        teamTaken.computeIfAbsent(game, k -> ConcurrentHashMap.newKeySet());
        return !teamTaken.get(game).contains(team);
    }

}
