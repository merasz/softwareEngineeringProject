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

    public List<Score> getBestScoresFromUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        List<Score> scores= scoreRepository.findGameScoresByUser(teams);
        scores.sort(compareByScore);
        int length = scores.size();
        return length > 5 ?  scores.subList(0,5) : scores.subList(0,length);
    }

    public List<Score> getLatestScoresFromUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        List<Score> scores= scoreRepository.findGameScoresByUser(teams);
        scores.sort(compareByDate);
        int length = scores.size();
        return length > 5 ?  scores.subList(0,5) : scores.subList(0,length);
    }

    public int getGameCountByUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        return scoreRepository.countGamesByTeam(teams);
    }

    public int getWonCountByUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        Integer wonGames = scoreRepository.countWonGamesByUser(teams);
        return wonGames == null ? 0 : wonGames.intValue();
    }

    public int getLostCountByUser(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        Integer lostGames = scoreRepository.countLostGamesByUser(teams);
        return lostGames == null ? 0 : lostGames.intValue();
    }

    public List<GameTopicCount> getWonGamesByTopics(User user) {
        List<Team> teams = teamService.getTeamsByPlayer(user);
        return topicGamesRepository.countWonGamesByUserAndTopic(teams);
    }

    Comparator<Score> compareByScore = new Comparator<Score>() {
        @Override
        public int compare(Score s1, Score s2) {
            return s1.getTotalRoundScore() - s2.getTotalRoundScore();
        }
    };

    Comparator<Score> compareByDate = new Comparator<Score>() {
        @Override
        public int compare(Score s1, Score s2) {
            return s1.getGame().getEndTime().compareTo(s2.getGame().getEndTime());
        }
    };

    public Team getTopTeamForGame(Game game) {
        return scoreRepository.getTopTeamInAGame(game).get(0);
    }
}
