package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("application")
public class GameStatsService extends GameService {

    public Game updateScores(Game game, int guessedRight, Term term, Task task) {
        Score score = getScoreRepository().findFirstByUserAndGame(getCurrentPlayer(), game);
        int currentPoints = score.getTotalRoundScore();

        //int guessedRight: -1 -> foul, 0 -> not guessed, 1 -> guessed right
        if (guessedRight == 1) {
            score.setTotalRoundScore(currentPoints + task.getPointsForTask());
            score.getGuessedTerms().add(term);
        } else if (guessedRight == -1) {
            score.setTotalRoundScore(currentPoints + PENALTY_POINTS);
            score.getNotGuessedTerms().add(term);
        } else {
            score.getNotGuessedTerms().add(term);
        }
        return game;
    }

    public int getSecondsPlayed(Game game) throws UnsupportedOperationException {
        if (game.getEndTime() == null) {
            if (game.getPausedTime() == null) {
                throw new UnsupportedOperationException();
            } else {
                LocalDateTime start = new Timestamp(game.getStartTime().getTime()).toLocalDateTime();
                return (int) start.until(game.getPausedTime().toInstant(), ChronoUnit.SECONDS);
            }
        } else {
            LocalDateTime start = new Timestamp(game.getStartTime().getTime()).toLocalDateTime();
            return (int) start.until(game.getEndTime().toInstant(), ChronoUnit.SECONDS);
        }
    }

    public Score getPlayerScore(Game game, User user) {
        return getScoreRepository().findFirstByUserAndGame(user, game);
    }

    //total game score stats
    public Score getGameScores(Game game) {
        return sumScores(game.getScores());
    }

    //scores per team
    public List<Score> getTeamScores(Game game) {
        List<Score> scores = new ArrayList<>();
        for (Team t : game.getTeamList()) {
            scores.add(sumScores(getScoreRepository().findAllByGameAndTeam(game, t)));
        }
        return scores;
    }

    private Score sumScores(List<Score> scores) {
        Score score = new Score();
        int totalScore = 0;
        List<Term> guessedTerms = new ArrayList<>();
        List<Term> notGuessedTerms = new ArrayList<>();

        for (Score s : scores) {
            totalScore += s.getTotalRoundScore();
            guessedTerms.addAll(s.getGuessedTerms());
            notGuessedTerms.addAll(s.getNotGuessedTerms());
        }

        score.setTotalRoundScore(totalScore);
        score.setGuessedTerms(guessedTerms);
        score.setNotGuessedTerms(notGuessedTerms);
        return score;
    }
}
