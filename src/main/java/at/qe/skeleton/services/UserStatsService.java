package at.qe.skeleton.services;

import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.ScoreRepository;
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

    public List<Score> getBestScoresFromUser(User user) {
        List<Score> scores= scoreRepository.findGameScoresByUser(user);
        scores.sort(compareByScore);
        int length = scores.size();
        return length > 5 ?  scores.subList(0,5) : scores.subList(0,length);
    }

    public List<Score> getLatestScoresFromUser(User user) {
        List<Score> scores= scoreRepository.findGameScoresByUser(user);
        scores.sort(compareByDate);
        int length = scores.size();
        return length > 5 ?  scores.subList(0,5) : scores.subList(0,length);
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
}
