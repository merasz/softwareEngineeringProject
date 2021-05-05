package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Scope("session")
public class ScoreService extends GameService {

    @Autowired
    UserService userService;

    @Autowired
    ScoreRepository scoreRepository;


    public List<User> getMostValuedUsers() {
        return scoreRepository.getTopPlayersUsernames();
    }

    public List<Integer> getMostValuedUserScores() {
        return scoreRepository.getTopPlayersScores();
    }


    public Map<User, Integer> getUsersWithScores() {
        List<User> users = getMostValuedUsers();
        List<Integer> scores = getMostValuedUserScores();
        Map<User, Integer> val = new LinkedHashMap<>();
        for (int i = 0; i < users.size(); i++) {
            val.put(users.get(i), scores.get(i));
        }
        return val;
    }

    public Map<User, Integer> getUsersWithScoreAGame() {
        List<User> users = scoreRepository.getTopPlayersInAGame();
        List<Integer> scores = scoreRepository.getTopPlayersInAGameScore();
        Map<User, Integer> val = new LinkedHashMap<>();
        for (int i = 0; i < users.size(); i++) {
            val.put(users.get(i), scores.get(i));
        }
        return val;
    }

    public Map<Team, Integer> getScoresForTeamByGame(Game game) {
        List<Team> teams = scoreRepository.getTeamsForGame(game.getGameId());
        List<Integer> scores = scoreRepository.getForTeamsByGameScore(game.getGameId(), teams);
        Map<Team, Integer> val = new LinkedHashMap<>();
        for (int i = 0; i < teams.size(); i++) {
            val.put(teams.get(i), scores.get(i));
        }
        return val;
    }

    public List<Score> getScoresForTeams(Game game) {
        return scoreRepository.getScoresForTeamsByGame(game);
    }
}
