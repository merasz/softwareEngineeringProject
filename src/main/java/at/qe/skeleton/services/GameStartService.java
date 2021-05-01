package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Scope("session")
public class GameStartService extends GameService {

    @Autowired
    private GamePlayService gamePlayService;

    @Autowired
    private UserService userService;

    private User user;
    private Game game;
    private Team team;
    private int registered = 0;

    public Game startGame(Game game, User user) {
        this.user = user;
        game.setActive(true);
        this.game = game;
        joinTeam();
        getGameJoinController().onJoin(game);
        return game;
    }

    public Game joinGame(User user) throws NoSuchElementException, IndexOutOfBoundsException {
        this.user = user;
        this.game = getGameRepository().findActiveGameByRaspberry(user.getRaspberry().getRaspberryId());

        if (game == null ) {
            throw new NoSuchElementException("No active game found. Ask a game manager to create a new game.");
        }
        joinTeam();
        getGameJoinController().onSelect(user);
        return game;
    }

    // when joining the game: get assigned to a team or get your team, when already assigned somewhere
    private void joinTeam() throws IndexOutOfBoundsException {
        // see if player is assigned to one of the teams
        Team team;
        List<Team> teams = game.getTeamList().stream().
                filter(t -> t.getTeamPlayers().stream().map(User::getUsername).collect(Collectors.toList()).contains(user.getUsername())).
                collect(Collectors.toList());

        if (!teams.isEmpty()) {
            // if player is already assigned to a team
            team = teams.get(0);
        } else {
            teams = game.getTeamList().stream().filter(t -> t.getTeamPlayers().isEmpty()).collect(Collectors.toList());
            if (!teams.isEmpty()) {
                // if there are any teams without any assignments
                team = teams.get(0);
            } else {
                // else get any remaining team
                team = game.getTeamList().get(0);
            }
        }
        addUserToTeam(team);
    }

    private void addUserToTeam(Team team) {
        System.out.println("add user to team");
        if (!team.getTeamPlayers().contains(user)) {
            team.getTeamPlayers().add(user);
            System.out.println(user.getUsername() + " added to " + team.getTeamName());
        }
        this.team = getTeamService().saveTeam(team);
        this.game = getGameRepository().save(game);
    }

    public void selectPlayer(User user) {
        this.user = user;
        addUserToTeam(this.team);
        getGameJoinController().onSelect(user);
    }

    public boolean teamReady() {
        return game.getTeamSize() == team.getTeamPlayers().size();
    }

    public void enterGame(String teamName) throws IllegalArgumentException {
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException("Give your team a name first.");
        } else if (!teamReady()) {
            throw new IllegalArgumentException("You haven't assigned enough team mates yet.");
        }
        this.team.setTeamName(teamName);
        getTeamService().saveTeam(team);
        getGameRepository().save(game);
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



    public void initializeGame(Game game) {
        //TODO: accept when all players have pressed start

        for (Team t : game.getTeamList()) {
            for (User u : t.getTeamPlayers()) {
                Score s = new Score(u, t, game);
                getScoreRepository().save(s);
            }
        }
        createPlayerOrdering(game);
        if (game.getStartTime() == null) {
            game.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            game.setPausedTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        game.setActive(true);
        getGameRepository().save(game);
    }

    private void createPlayerOrdering(Game game) {
        List<List<TeamPlayer>> tpList = new ArrayList<>();
        for (Team t : game.getTeamList()) {
            List<TeamPlayer> tP = new ArrayList<>();
            incrementNumPlayers(t.getTeamPlayers().size());

            for (User p : t.getTeamPlayers()) {
                tP.add(new TeamPlayer(p, t));
            }

            Collections.shuffle(tP);
            tpList.add(tP);
        }
        Collections.shuffle(tpList);
        IntStream.range(0, tpList.get(0).size()).boxed().collect(Collectors.toList()).stream()
                .map(i -> tpList.stream().map(t -> t.get(i)).collect(Collectors.toList()))
                .collect(Collectors.toList()).forEach(getPlayers()::addAll);
    }

    //TODO: validate all Teams same size, add user to team
    public void joinTeam(Game game, int teamNr) throws IllegalArgumentException {

    }

    public void deleteGame(Game game) {
        getGameRepository().delete(game);
        for (Score s : getScoreRepository().findAllByGame(game)) {
            getScoreRepository().delete(s);
        }
    }

    public boolean isFinished(Game game) {
        return game.getEndTime() != null;
    }
}
