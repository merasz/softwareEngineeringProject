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
import java.util.Random;

@Component
@Scope("application")
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    private final int PENALTY_POINTS = 1;
    private int numPlayers = 0;
    private Random r;
    private User currentPlayer;
    private int currentTeam;
    private List<User> usersPlayedAlready;

    public Game createGame(int scoreToWin, int totalScore, int nrRound, Topic topic, int raspberryId, List<Team> teamList) {
        Game game = new Game(scoreToWin, totalScore, nrRound, topic, raspberryId, Timestamp.valueOf(LocalDateTime.now()), teamList);
        for (Team t : teamList) {
            for (User u : t.getTeamPlayers()) {
                Score s = new Score(u, t, game);
                scoreRepository.save(s);
                numPlayers++;
            }
        }
        setCurrentPlayer(game);
        gameRepository.save(game);
        return game;
    }

    //TODO
    public Game nextTurn(Game game) {
        selectNextPlayer(game);
        //selectNextTerm();
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

    public Game updateScores(Game game, int guessedRight, Term term, Task task) {
        Score score = scoreRepository.findFirstByUserAndGame(currentPlayer, game);
        int currentPoints = score.getTotalRoundScore();

        //int guessedRight: -1 -> foul, 0 -> not guessed, 1 -> guessed right
        if (guessedRight == 1) {
            score.setTotalRoundScore(currentPoints + task.getPointsForTask());
            score.getGuessedTerms().add(term);
        } else if (guessedRight == -1) {
            score.setTotalRoundScore(currentPoints - PENALTY_POINTS);
            score.getNotGuessedTerms().add(term);
        } else {
            score.getNotGuessedTerms().add(term);
        }
        return game;
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

    private void setCurrentPlayer(Game game) {
        r = new Random();
        usersPlayedAlready = new ArrayList<>();
        currentTeam = r.nextInt(game.getTeamList().size());
        List<User> players = game.getTeamList().get(currentTeam).getTeamPlayers();
        int p = r.nextInt(players.size());
        User player = players.get(p);

        this.currentPlayer = player;
        this.usersPlayedAlready.add(player);
    }

    private void selectNextPlayer(Game game) {
        currentTeam = (currentTeam + 1) % game.getTeamList().size();
        List<User> players = game.getTeamList().get(currentTeam).getTeamPlayers();

        if (usersPlayedAlready.size() == numPlayers) {
            usersPlayedAlready = new ArrayList<>();
        }

        int p;
        User player;
        do {
            p = r.nextInt(players.size());
            player = players.get(p);
        } while (usersPlayedAlready.contains(player));

        this.currentPlayer = player;
        this.usersPlayedAlready.add(player);
    }
}
