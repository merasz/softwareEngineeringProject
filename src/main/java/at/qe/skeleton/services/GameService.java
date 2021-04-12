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
import java.util.*;

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
    private Team currentTeam;
    private User currentPlayer;
    private List<TeamPlayer> teams;
    private Iterator<Team> iterateTeams;
    private Iterator<User> iteratePlayers;

    public Game createGame(int scoreToWin, int totalScore, Topic topic, int raspberryId, List<Team> teamList) {
        Game game = new Game(scoreToWin, totalScore, topic, raspberryId, Timestamp.valueOf(LocalDateTime.now()), teamList);
        //TODO: validate: at least 2 Teams with 2 Players each
        for (Team t : teamList) {
            for (User u : t.getTeamPlayers()) {
                Score s = new Score(u, t, game);
                scoreRepository.save(s);
            }
        }
        createPlayerOrdering(game);
        gameRepository.save(game);
        return game;
    }

    //TODO
    public Game nextTurn(Game game) {
        TeamPlayer tp = selectNextPlayer(game);
        currentTeam = tp.team;
        currentPlayer = tp.player;
        //selectNextTerm();
        game.incrementNrRound();
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

    private void createPlayerOrdering(Game game) {
        List<List<TeamPlayer>> tpList = new ArrayList<>();
        for (Team t : game.getTeamList()) {
            List<TeamPlayer> tP = new ArrayList<>();
            numPlayers += t.getTeamPlayers().size();

            for (User p : t.getTeamPlayers()) {
                tP.add(new TeamPlayer(p, t));
            }

            Collections.shuffle(tP);
            tpList.add(tP);
        }
        Collections.shuffle(teams);
        tpList.forEach(teams::addAll);
    }

    private TeamPlayer selectNextPlayer(Game game) {
        return teams.get((game.getNrRound() - 1) % numPlayers);
    }

    private class TeamPlayer {
        User player;
        Team team;

        TeamPlayer(User player, Team team) {
            this.player = player;
            this.team = team;
        }
    }
}
