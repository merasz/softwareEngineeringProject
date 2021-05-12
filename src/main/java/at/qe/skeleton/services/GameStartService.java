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

// handles the logic for the player selection phase
// after starting a game and before entering the game room,
// team-leaders get to select their players (if not already set beforehand)
@Component
@Scope("session")
public class GameStartService extends GameService {

    @Autowired
    private GamePlaySocketController gamePlaySocketController;

    private User user;
    private Game game;
    private Team team;

    //region Player join phase
    // start game by game creator
    public Game startGame(Game game, User user) throws IllegalArgumentException {
        game.setActive(true);
        this.user = user;
        this.game = game;
        joinTeam();
        getGameJoinController().onJoin(this.game);
        return game;
    }

    // join game for all other team representatives
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

    // get game object, choosing the latest game assigned to the same raspberry as the user
    public Game getActiveGame(User user) {
        return getGameRepository().findActiveGameByRaspberry(user.getRaspberry().getRaspberryId());
    }

    // when joining the game: get assigned to a team or get your team, when already assigned somewhere
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

    // assign a user to a team
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

    // select player in the GUI
    // assigns player to team and makes player unavailable in selection screen
    public Game selectPlayer(User user) {
        this.game = reloadGame(game);
        this.user = user;
        addUserToTeam(this.team);
        getGameJoinController().onSelect(user, game);
        return game;
    }

    // check if team is ready to play (all team seats occupied with players)
    public boolean teamReady() {
        return game.getTeamSize() == team.getTeamPlayers().size();
    }
    //endregion

    //region initialize and enter game
    // announce team ready to play, try to join if other teams ready too
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

    // enter game room, if all teams are ready to play and have run finishTeamAssign()
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

    // initialize game with new Score instances, start time and iterable player-list
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
    //endregion

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
