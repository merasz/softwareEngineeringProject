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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Scope("application")
public class GameManageService extends GameService {
    @Autowired
    private TeamService teamService;

    private int registered = 0;

    //region create Game
    public Game createGame(int scoreToWin, int countPlayers, Topic topic, Raspberry raspberry, int countTeams) throws IllegalArgumentException {
        if (topic == null) {
            throw new IllegalArgumentException("Select a topic.");
        } else if (countTeams < 2) {
            throw new IllegalArgumentException("Create at least 2 Teams.");
        } else if (countPlayers % countTeams != 0) {
            throw new IllegalArgumentException("Teams need to have same number of players.");
        } else if (countPlayers / countTeams < 2) {
            throw new IllegalArgumentException("Teams must have at least 2 players.");
        } else {
            List<Team> teams = new ArrayList<>();
            for (int i = 0; i < countPlayers / countTeams; i++) {
                Team t = new Team();
                t.setTeamName("Team " + (i + 1));
                teams.add(t);
                teamService.saveTeam(t);
            }
            Game game = new Game(scoreToWin, countPlayers, topic, raspberry, teams);
            getGameRepository().save(game);
            teams.forEach(t -> t.setGame(game));
            teams.forEach(teamService::saveTeam);
            return game;
        }
    }

    //start game: all players have to press start
    public Game startGame(Game game, int countPlayers) {
        game.setActive(true);
        getGameStartController().onJoin(game);
        getGameRepository().save(game);
        return game;
    }

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
    //endregion

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
