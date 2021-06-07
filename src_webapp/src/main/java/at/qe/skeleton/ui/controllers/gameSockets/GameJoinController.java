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

    private Map<Game, List<String>> sendTo = new ConcurrentHashMap<>();
    private Map<Game, List<PlayerAvailability>> playerAvailabilities = new ConcurrentHashMap<>();
    private Map<Game, Boolean> allTeamsReady = new ConcurrentHashMap<>();
    private Map<Game, Set<Team>> teamTaken = new ConcurrentHashMap<>();
    private Map<Game, Set<Team>> teamAccepted = new ConcurrentHashMap<>();
    private Map<Game, Boolean> gameInitialized = new ConcurrentHashMap<>();

    /**
     * initialize when game creator joins
     * @param game
     */
    public void onJoin(Game game) {
        List<User> playerCircle = userRepository.findAllByRaspberryAndRaspberryNotNull(game.getRaspberry());

        // create list of players with their availabilities for each player circle
        if (!playerAvailabilities.containsKey(game)) {
            List<PlayerAvailability> playerAvailability = new CopyOnWriteArrayList<>();
            playerCircle.forEach(u -> playerAvailability.add(new PlayerAvailability(u, game)));
            playerAvailabilities.put(game, playerAvailability);
        }

        // set availability for users already assigned to a team to false
        List<User> assignedPlayers = game.getTeamList().stream()
                .flatMap(t -> t.getTeamPlayers().stream()).collect(Collectors.toList());
        playerAvailabilities.get(game).stream().filter(pa -> assignedPlayers.contains(pa.getUser()))
                .forEach(pa -> pa.setAvailable(false));

        // set-up other variables
        teamAccepted.put(game, ConcurrentHashMap.newKeySet());
        gameInitialized.put(game, false);

        // register for websocket messages
        sendTo.put(game, playerCircle.stream().map(User::getUsername).collect(Collectors.toList()));
        updateJoinChannel(game);
    }

    /**
     * update when team leaders assign players
     * @param user selected user
     */
    public void onSelect(User user, Game game) {
        playerAvailabilities.get(game).stream()
                .filter(pa -> pa.getUsername().equals(user.getUsername()) && pa.getGame().equals(game))
                .forEach(pa -> pa.setAvailable(false));
        updateJoinChannel(game);
    }

    public void updateJoinChannel(Game game) {
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo.get(game));
    }

    /**
     * claim a team, when entering player select phase
     * @param team team fetched by joined user
     */
    public void takeTeam(Game game, Team team) {
        teamTaken.computeIfAbsent(game, k -> ConcurrentHashMap.newKeySet());
        teamTaken.get(game).add(team);
    }

    /**
     * gets list of player availabilities (player free to select or already assigned to a team)
     * @return list of PlayerAvailability
     */
    public List<PlayerAvailability> getPlayerAvailability(Game game) {
        return Collections.unmodifiableList(getGamePlayerAvailabilities(game));
    }

    /**
     * updates set of Teams that have already been claimed
     * returns true if all teams have been claimed and all teams are ready to play
     * @param user selecting player
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
     * @return boolean
     */
    public boolean allReadyToStart(Game game) {
        return this.allTeamsReady.get(game)
                && teamAccepted.get(game).size() == game.getTeamList().size();
    }

    /**
     * updates if all teams are ready to play
     * teams are ready if all open team positions are assigned to players
     */
    public void updateTeamsReady(Game game) {
        boolean allPlayersAssigned = getGamePlayerAvailabilities(game).stream()
                .filter(pa -> !pa.isAvailable()).count() == game.getCountPlayers();
        this.allTeamsReady.put(game, allPlayersAssigned);
    }

    /**
     * reset all teams and player assignments
     */
    public void resetAssignments(Game game) {
        getGamePlayerAvailabilities(game).forEach(pa -> pa.setAvailable(true));
        allTeamsReady.put(game, false);
        teamAccepted.put(game, ConcurrentHashMap.newKeySet());
        updateJoinChannel(game);
    }

    /**
     * gets list of player availabilities by game (player free to select or already assigned to a team)
     * @return List<PlayerAvailability>
     */
    private List<PlayerAvailability> getGamePlayerAvailabilities(Game game) {
        return playerAvailabilities.get(game).stream().filter(pa -> pa.getGame().equals(game)).collect(Collectors.toList());
    }

    /**
     * returns true if given team is available
     * (when set of taken teams does not contain given team)
     * @return boolean
     */
    public boolean teamAvailable(Game game, Team team) {
        teamTaken.computeIfAbsent(game, k -> ConcurrentHashMap.newKeySet());
        return !teamTaken.get(game).contains(team);
    }

    /**
     * returns true if game is initialized
     * (when all teams are ready to join and game has been initialized in GameStartService)
     * @return boolean
     */
    public boolean isInitialized(Game game) {
        return gameInitialized.get(game);
    }

    /**
     * set to true if game has been initialized in GameStartService
     */
    public void setInitialized(Game game) {
        gameInitialized.put(game, true);
    }
}
