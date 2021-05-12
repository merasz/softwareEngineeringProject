package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.ui.controllers.gameSockets.GamePlaySocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Scope("session")
public class GameStartService extends GameService {

    @Autowired
    private GamePlaySocketController gamePlaySocketController;

    private User user;
    private Game game;
    private Team team;

    /**
     * returns the game which got selected to start
     * game gets initialized
     * @param game
     * @param user
     * @return
     * @throws IllegalArgumentException
     */
    public Game startGame(Game game, User user) throws IllegalArgumentException {
        game.setActive(true);
        this.user = user;
        this.game = game;
        joinTeam();
        getGameJoinController().onJoin(this.game);
        return game;
    }

    /**
     * returns a game if it got selected
     * @param game
     * @param user
     * @return
     * @throws NoSuchElementException
     * @throws IllegalArgumentException
     */
    public Game joinGame(Game game, User user) throws NoSuchElementException, IllegalArgumentException {
        this.user = user;
        this.game = reloadGame(game);
        if (this.game == null ) {
            throw new NoSuchElementException("No active game found. Ask a game manager to create a new game.");
        }
        joinTeam();
        getGameJoinController().onSelect(user, this.game);
        return this.game;
    }

    /**
     * returns the active game where the user is assigned
     * @param user
     * @return game object
     */
    public Game getActiveGame(User user) {
        return getGameRepository().findActiveGameByRaspberry(user.getRaspberry().getRaspberryId());
    }

    /**
     * when joining the game: get assigned to a team or get your team, when already assigned somewhere
     * @throws IllegalArgumentException
     */
    private void joinTeam() throws IllegalArgumentException {
        // see if player is assigned to one of the teams
        Team team;
        Optional<Team> optTeam = game.getTeamList().stream().filter(t -> t.getTeamPlayers().contains(user)).findFirst();

        if (optTeam.isPresent()) {
            // if player is already assigned to a team
            team = optTeam.get();
        } else {
            optTeam = game.getTeamList().stream().filter(t -> getGameJoinController().teamAvailable(game, t)).findFirst();
            if (optTeam.isPresent()) {
                // if there are any untaken teams
                team = optTeam.get();
            } else {
                throw new IllegalArgumentException("Get together with another team or ask the game manager to redistribute.");
            }
        }
        addUserToTeam(team);
    }

    /**
     * assign a user to a team for a game
     * @param team
     */
    private void addUserToTeam(Team team) {
        if (!team.getTeamPlayers().contains(user)) {
            List<User> players = team.getTeamPlayers();
            players.add(user);
            team.setTeamPlayers(players);
        }
        this.team = getTeamService().saveTeam(team);
        this.game = reloadGame(game);
        getGameJoinController().takeTeam(game, team);
    }

    /**
     * returns a game for the selected user
     * @param user
     * @return game object
     */
    public Game selectPlayer(User user) {
        this.game = reloadGame(game);
        this.user = user;
        addUserToTeam(this.team);
        getGameJoinController().onSelect(user, game);
        return game;
    }

    /**
     * checks if all conditions are success to start the game and wipe to the gameroom
     * @return boolean
     */
    public boolean teamReady() {
        return game.getTeamSize() == team.getTeamPlayers().size();
    }

    /**
     * returns a playable game
     * @param teamName
     * @return game object
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public Game finishTeamAssign(String teamName) throws IllegalArgumentException, IOException {
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException("Give your team a name first.");
        } else if (game.getTeamList().stream().filter(t -> t.getTeamName() != null)
                .anyMatch(t -> t.getTeamName().equals(teamName) && !t.getTeamId().equals(team.getTeamId()))) {
            throw new IllegalArgumentException("Sorry, another team already took this name.");
        } else if (!teamReady()) {
            throw new IllegalArgumentException("You haven't assigned enough team mates yet.");
        }
        this.team.setTeamName(teamName);
        this.team = getTeamService().saveTeam(team);
        this.game = reloadGame(game);
        getGameJoinController().updateTeamsReady(game);

        return enterGame();
    }

    /**
     * enter a game
     * @return game
     * @throws IOException
     */
    public Game enterGame() throws IOException {
        this.game = reloadGame(game);
        if (getGameJoinController().updateReadyToStart(game, user)) {
            if (game.getStartTime() == null && !getGameJoinController().isInitialized(game)) {
                //System.out.println("-------  initialized  -------");
                initializeGame(game);
                getGameJoinController().setInitialized(game);
                this.game = saveGame(game);
                gamePlaySocketController.initGame(game);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/game_room/gameRoom.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        }
        return game;
    }

    /**
     * initializes a game
     * @param game
     */
    private void initializeGame(Game game) {
        // initialize scores
        for (Team t : game.getTeamList()) {
            t.getTeamPlayers().forEach(u -> getScoreRepository().save(new Score(u, t, game)));
        }

        // set start time
        Date start = Timestamp.valueOf(LocalDateTime.now());
        if (game.getStartTime() == null) {
            game.setStartTime(start);
        } else {
            game.setPausedTime(start);
        }

        // create flat ordered list of players
        Queue<TeamPlayer> orderedPlayerList = new LinkedList<>();
        List<Team> teams = game.getTeamList();
        Collections.shuffle(teams);
        teams.forEach(t -> Collections.shuffle(t.getTeamPlayers()));
        IntStream.range(0, game.getTeamSize()).boxed().collect(Collectors.toList())
                .forEach(i ->
                        teams.forEach(t ->
                                orderedPlayerList.add(
                                        new TeamPlayer(t.getTeamPlayers().get(i), t))));
        gamePlaySocketController.putTeamPlayerMap(game, orderedPlayerList);
    }

    //region getter & setter
    public Game getGame() {
        return game;
    }

    public String getTeamSizeString() {
        return team.getTeamPlayers().size() + " of " + game.getTeamSize() + " players assigned.";
    }

    public Team getTeam() {
        return team;
    }
    //endregion
}
