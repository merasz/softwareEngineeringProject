package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Scope("view")
public class GameController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    @Autowired
    private TermsService termsService;

    private List<Game> games;
    private Game game;

    private int scoreToWin;
    private int totalScore;
    private int nrRound;
    private Topic topic;

    public List<Game> getGames() {
        games = gameService.getGameRepository().findAll();
        return games;
    }

    public void stopOrDeleteGame() {
        if (gameService.isFinished(game)) {
            deleteGame();
        } else {
            endGame();
        }
    }

    private void deleteGame() {
        gameService.deleteGame(game);
        displayInfo("Game deleted", "Game deleted successfully.");
    }

    //TODO: get connected Raspberry, get Teams connected with raspberry
    public Game startGame() {
        int raspberryId = 0;
        List<Team> teamList = null;

        displayInfo("Game started", "Game started successfully.");
        game = gameService.createGame(scoreToWin, totalScore, topic, raspberryId, teamList);
        return game;
    }

    //TODO: get time from timer
    public Game endGame() {
        int seconds = 0;

        displayInfo("Game stopped", "Game stopped successfully.");
        game = gameService.stopGame(game);
        return game;
    }

    public String getTimePlayed() {
        try {
            return new SimpleDateFormat("hh:mm:ss").format(new Date(gameService.getSecondsPlayed(game) * 1000L));
        } catch (UnsupportedOperationException e) {
            return "...unfinished";
        }
    }

    //TODO
    public Game nextRound(int guessedRight) {
        //get current task
        //int guessedRight: -1 -> foul, 0 -> not guessed, 1 -> guessed right
        //gameService.updateScores(game, guessedRight, termsService.getNextTerm(game), task);
        return null;
    }

    public void setScoreToWin(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setNrRound(int nrRound) {
        this.nrRound = nrRound;
    }

    public void setTopic(Topic topic) {
        try {
            this.topic = termsService.setGameTopic(topic);
        } catch (IllegalArgumentException e) {
            displayError("Too few terms in topic", e.getMessage());
        }
    }
}
