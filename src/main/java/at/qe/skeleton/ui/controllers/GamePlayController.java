package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Task;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.GamePlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
@Scope("view")
public class GamePlayController extends GameController implements Serializable {
    @Autowired
    private GamePlayService gamePlayService;

    private User user;
    private boolean paused = false;
    private int guessAccepted = 0;

    //region gaming round
    public void startRound() {
        Thread timerThread = new Thread(){
            @Override
            public void run() {
                gamePlayService.runGameRound(getGame());
                setGame(getGameStatsService().updateScores(getGame(), guessAccepted, getTermsService().getNextTerm(getGame())));
            }
        };
        timerThread.start();
    }

    public String getTimer() {
        return gamePlayService.getTimerString();
    }

    public void acceptAnswer(int guessAccepted) {
        //int guessedRight: -1 -> foul, 1 -> guessed right
        this.guessAccepted = guessAccepted;
        gamePlayService.setGuessAccepted(true);
    }
    //endregion

    public int countGuessesForWin() {
        return gamePlayService.countGuessesForWin(getGame());
    }

    //show score for player
    public List<Score> getPlayerScore() {
        return Collections.singletonList(getGameStatsService().getPlayerScore(getGame(), user));
    }

    //show scores for all teams
    public List<Score> getScoreboard() {
        return getGameStatsService().getTeamScores(getGame());
    }

    //TODO
    public Game nextRound() {
        //get new task
        return null;
    }

    public Task getTask() {
        return gamePlayService.getTask(getGame());
    }

    public Game endGame() {
        return gamePlayService.stopGame(getGame());
    }

    public Game pauseGame() {
        paused = !paused;
        return gamePlayService.pauseGame(getGame(), paused);
    }

    public boolean isPaused() {
        return paused;
    }
}
