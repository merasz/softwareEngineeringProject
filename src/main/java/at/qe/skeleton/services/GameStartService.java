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

    //region Player join phase
    public Game startGame(Game game, User user) throws IllegalArgumentException {
        game.setActive(true);
        this.user = user;
        this.game = game;
        joinTeam();
        getGameJoinController().onJoin(this.game);
        return this.game;
    }

    public Game joinGame(Game game, User user) throws NoSuchElementException, IllegalArgumentException {
        this.user = user;
        this.game = game;
        if (game == null ) {
            throw new NoSuchElementException("No active game found. Ask a game manager to create a new game.");
        }
        joinTeam();
        getGameJoinController().onSelect(user, game);
        return game;
    }

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

    private void addUserToTeam(Team team) {
        if (!team.getTeamPlayers().contains(user)) {
            List<User> players = team.getTeamPlayers();
            players.add(user);
            team.setTeamPlayers(players);
            System.out.println("user: " + user.getUsername() + " -- team: " + team.getTeamId());
            System.out.println("team: " + team.getTeamPlayers().stream().map(User::getUsername).collect(Collectors.toList()));
        }
        this.team = getTeamService().saveTeam(team);
        this.game = saveGame(game);
        //this.game = reloadGame(game);
        System.out.println("game: " + game.getTeamList().stream().flatMap(t -> t.getTeamPlayers().stream()
                .map(User::getUsername)).collect(Collectors.toList()));
        System.out.println("team: " + team.getTeamPlayers().stream().map(User::getUsername).collect(Collectors.toList()));
        getGameJoinController().takeTeam(game, team);
    }

    public Game selectPlayer(User user) {
        this.game = reloadGame(game);
        this.user = user;
        addUserToTeam(this.team);
        getGameJoinController().onSelect(user, game);
        return game;
    }

    public boolean teamReady() {
        return game.getTeamSize() == team.getTeamPlayers().size();
    }
    //endregion

    //region initialize and enter game
    public Game finishTeamAssign(String teamName) throws IllegalArgumentException, IOException {
        this.game = reloadGame(game);
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException("Give your team a name first.");
        } else if (game.getTeamList().stream().filter(t -> t.getTeamName() != null)
                .anyMatch(t -> t.getTeamName().equals(teamName) && !t.getTeamId().equals(team.getTeamId()))) {
            throw new IllegalArgumentException("Sorry, another team already took this name.");
        } else if (!teamReady()) {
            throw new IllegalArgumentException("You haven't assigned enough team mates yet.");
        }
        this.team.setTeamName(teamName);
        getTeamService().saveTeam(team);

        return enterGame();
    }

    public Game enterGame() throws IOException {
        if (getGameJoinController().getAllTeamsReady(game, user)) {
            if (game.getStartTime() == null && game.getTeamList().get(0).getTeamId().equals(team.getTeamId())) {
                initializeGame(game);
                System.out.println("-------  initialized  -------");
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/game_room/gameRoom.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        }
        this.game = saveGame(game);
        return game;
    }

    private void initializeGame(Game game) {
        for (Team t : game.getTeamList()) {
            for (User u : t.getTeamPlayers()) {
                Score s = new Score(u, t, this.game);
                getScoreRepository().save(s);
            }
        }

        Date start = Timestamp.valueOf(LocalDateTime.now());
        if (this.game.getStartTime() == null) {
            this.game.setStartTime(start);
        } else {
            this.game.setPausedTime(start);
        }

        gamePlaySocketController.putTeamPlayerMap(game, createPlayerOrdering(game));
    }

    private Queue<TeamPlayer> createPlayerOrdering(Game game) {
        Queue<TeamPlayer> orderedPlayerList = new LinkedList<>();

        List<Team> teams = game.getTeamList();
        Collections.shuffle(teams);
        teams.forEach(t -> Collections.shuffle(t.getTeamPlayers()));
        IntStream.range(0, game.getTeamSize()).boxed().collect(Collectors.toList())
                .forEach(i ->
                        teams.forEach(t ->
                                orderedPlayerList.add(
                                        new TeamPlayer(t.getTeamPlayers().get(i), t))));
        return orderedPlayerList;
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
