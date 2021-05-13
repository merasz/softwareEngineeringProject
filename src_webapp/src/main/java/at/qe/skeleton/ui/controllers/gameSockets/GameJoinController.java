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

/**
 * coordinates the player selection phase via websocket
 */
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
    private Map<Game, Boolean> gameInitialized = new ConcurrentHashMap<>();

    /**
     * initialize when game creator joins
     * @param game
     */
    public void onJoin(Game game) {
        List<User> playerCircle = userRepository.findAllByRaspberry(game.getRaspberry());
        sendTo.addAll(playerCircle.stream().map(User::getUsername).collect(Collectors.toList()));
        teamAccepted.put(game, ConcurrentHashMap.newKeySet());

        gameInitialized.put(game, false);

        List<User> assignedPlayers = game.getTeamList().stream()
                .flatMap(t -> t.getTeamPlayers().stream()).collect(Collectors.toList());

        for (User u : playerCircle) {
            PlayerAvailability pA = new PlayerAvailability(u, game);
            if (assignedPlayers.contains(u)) {
                pA.setAvailable(false);
            }
            playerAvailability.add(pA);
        }

        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    /**
     * update when team leaders assign players
     * @param user
     * @param game
     */
    public void onSelect(User user, Game game) {
        //updateGames(game);
        playerAvailability.stream()
                .filter(pa -> pa.getUsername().equals(user.getUsername()) && pa.getGame().equals(game))
                .forEach(pa -> pa.setAvailable(false));
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public void updateJoinChannel() {
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    /**
     * claim a team, when entering player select phase
     * @param game
     * @param team
     */
    public void takeTeam(Game game, Team team) {
        teamTaken.computeIfAbsent(game, k -> ConcurrentHashMap.newKeySet());
        teamTaken.get(game).add(team);
    }

    /**
     * gets list of player availabilities (player free to select or already assigned to a team)
     * @param game
     * @return
     */
    public List<PlayerAvailability> getPlayerAvailability(Game game) {
        return Collections.unmodifiableList(getGamePlayerAvailabilities(game));
    }

    /**
     * updates set of Teams that have already been claimed
     * returns true if all teams have been claimed and all teams are ready to play
     * @param game
     * @param user
     * @return boolean
     */
    public boolean updateReadyToStart(Game game, User user) {
        teamAccepted.get(game).add(game.getTeamList().stream()
                .filter(t -> t.getTeamPlayers().contains(user)).findFirst().get());
        return allReadyToStart(game);
    }

    /**
     * returns true if all teams have been claimed and all teams are ready to play
     * teams are ready if all open team positions are assigned to players
     * @param game
     * @return
     */
    public boolean allReadyToStart(Game game) {
        return this.allTeamsReady.get(game)
                && teamAccepted.get(game).size() == game.getTeamList().size();
    }

    /**
     * updates if all teams are ready to play
     * teams are ready if all open team positions are assigned to players
     * @param game
     */
    public void updateTeamsReady(Game game) {
        this.allTeamsReady.put(game,
                getGamePlayerAvailabilities(game).stream().filter(pa -> !pa.isAvailable()).count() == game.getCountPlayers());
    }

    /**
     * gets list of player availabilities by game (player free to select or already assigned to a team)
     * @param game
     * @return List<PlayerAvailability>
     */
    private List<PlayerAvailability> getGamePlayerAvailabilities(Game game) {
        return playerAvailability.stream().filter(pa -> pa.getGame().equals(game)).collect(Collectors.toList());
    }

    /**
     * returns true if given team is available
     * (when set of taken teams does not contain given team)
     * @param game
     * @param team
     * @return boolean
     */
    public boolean teamAvailable(Game game, Team team) {
        teamTaken.computeIfAbsent(game, k -> ConcurrentHashMap.newKeySet());
        return !teamTaken.get(game).contains(team);
    }

    /**
     * returns true if game is initialized
     * (when all teams are ready to join and game has been initialized in GameStartService)
     * @param game
     * @return
     */
    public boolean isInitialized(Game game) {
        return gameInitialized.get(game);
    }

    /**
     * set to true if game has been initialized in GameStartService
     * @param game
     */
    public void setInitialized(Game game) {
        gameInitialized.put(game, true);
    }

}
