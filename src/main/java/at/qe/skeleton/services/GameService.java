package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("application")
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public Game createGame(int scoreToWin, int totalScore, int nrRound, Topic topic, int raspberryId, List<Team> teamList) {
        Game game = new Game(scoreToWin, totalScore, nrRound, topic, raspberryId, Timestamp.valueOf(LocalDateTime.now()), teamList);
        gameRepository.save(game);
        return game;
    }

    public Game stopGame(Game game) {
        game.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        return game;
    }

    public Game pauseGame(Game game) {
        game.setPausedTime(Timestamp.valueOf(LocalDateTime.now()));
        return game;
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
        for (Score s : scoreRepository.findAllByGame(game)) {
            scoreRepository.delete(s);
        }
    }

    public boolean isFinished(Game game) {
        return game.getEndTime() != null;
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

    //total game score stats
    public Score getGameScores(Game game) {
        return sumScores(game.getScores());
    }

    //scores per team
    public List<Score> getTeamScores(Game game) {
        List<Score> scores = new ArrayList<>();
        for (Team t : game.getTeamList()) {
            scores.add(sumScores(scoreRepository.findAllByGameAndTeam(game, t)));
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

    public GameRepository getGameRepository() {
        return gameRepository;
    }
}
