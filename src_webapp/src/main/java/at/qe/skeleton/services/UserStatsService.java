package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TopicGamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Scope("application")
public class UserStatsService {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    TopicGamesRepository topicGamesRepository;

    @Autowired
    TeamService teamService;

    /**
     * Method to get top gamescores for a user
     * @param user User
     * @return List of Scores
     */
    public List<Score> getBestScoresFromUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        if(teams == null) return null;
        List<Score> scores= scoreRepository.findGameScoresByUser(teams);
        if(scores.size() == 0) return null;
        scores.sort(compareByScore);
        int length = scores.size();
        return length > 5 ?  scores.subList(0,5) : scores.subList(0,length);
    }

    /**
     * Method to get the last 5 gamescores for a user
     * @param user User
     * @return List of Scores
     */
    public List<Score> getLatestScoresFromUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        if(teams == null) return null;
        List<Score> scores= scoreRepository.findGameScoresByUser(teams);
        System.out.println(teams);
        System.out.println(scores);
        System.out.println(scores.size());
        if(scores.size() == 0) return null;
        scores.sort(compareByDate);
        int length = scores.size();
        return length > 5 ?  scores.subList(0,5) : scores.subList(0,length);
    }

    /**
     * Method to get the amount of games for a user
     * @param user User
     * @return Number of games
     */
    public int getGameCountByUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        return scoreRepository.countGamesByTeam(teams);
    }

    /**
     * Method to get amount of won games for a user
     * @param user User
     * @return Number of won games
     */
    public int getWonCountByUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        Integer wonGames = scoreRepository.countWonGamesByUser(teams);
        return wonGames == null ? 0 : wonGames.intValue();
    }

    /**
     * Method to get the amount of lost games for a user
     * @param user
     * @return Number of lost games
     */
    public int getLostCountByUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        Integer lostGames = scoreRepository.countLostGamesByUser(teams);
        return lostGames == null ? 0 : lostGames.intValue();
    }

    /**
     * Method to get the amount of won games per topic for a user
     * @param user
     * @return List of topics with won games
     */
    public List<GameTopicCount> getWonGamesByTopics(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        return topicGamesRepository.countWonGamesByUserAndTopic(teams);
    }

    /**
     * Comparator to compare Score Objects by score
     */
    Comparator<Score> compareByScore = new Comparator<Score>() {
        @Override
        public int compare(Score s1, Score s2) {
            return s1.getTotalRoundScore() - s2.getTotalRoundScore();
        }
    };

    /**
     *  Comparator to compare Score Objects by date
     */
    Comparator<Score> compareByDate = new Comparator<Score>() {
        @Override
        public int compare(Score s1, Score s2) {
            return s1.getGame().getEndTime().compareTo(s2.getGame().getEndTime());
        }
    };

    /**
     * Method to get the best team in a game
     * @param game Game
     * @return Best team in the given game
     */
    public Team getTopTeamForGame(Game game) {
        return scoreRepository.getTopTeamInAGame(game).get(0);
    }
}
