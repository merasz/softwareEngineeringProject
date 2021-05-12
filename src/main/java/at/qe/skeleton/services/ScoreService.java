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

    /**
     * Returns the overall top players
     * @return List of User
     */
    public List<User> getMostValuedUsers() {
        return scoreRepository.getTopPlayersUsernames();
    }

    /**
     * Returns the Scores of the top players
     * @return List of scores
     */
    public List<Integer> getMostValuedUserScores() {
        return scoreRepository.getTopPlayersScores();
    }

    /**
     * Returns Map of the top players consisting of the players and their scores
     * @return Map of User and their scores
     */
    public Map<User, Integer> getUsersWithScores() {
        List<User> users = getMostValuedUsers();
        List<Integer> scores = getMostValuedUserScores();
        Map<User, Integer> val = new LinkedHashMap<>();
        for (int i = 0; i < users.size(); i++) {
            val.put(users.get(i), scores.get(i));
        }
        return val;
    }

    /**
     * Returns Map of Players and their Score per Game
     * @return Map of User and their Score
     */
    public Map<User, Integer> getUsersWithScoreAGame() {
        List<User> users = scoreRepository.getTopPlayersInAGame();
        List<Integer> scores = scoreRepository.getTopPlayersInAGameScore();
        Map<User, Integer> val = new LinkedHashMap<>();
        for (int i = 0; i < users.size(); i++) {
            val.put(users.get(i), scores.get(i));
        }
        return val;
    }

    /**
     * Returns Scores per team for a game
     * @param game Game to get the scores for
     * @return
     */
    public List<Score> getScoresForTeams(Game game) {
        return scoreRepository.getScoresForTeamsByGame(game);
    }
}
