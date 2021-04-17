package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.GamePlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class GamePlayController extends GameController implements Serializable {
    @Autowired
    private GamePlayService gamePlayService;

    private User user;

    public int countGuessesForWin() {
        return gamePlayService.countGuessesForWin(getGame());
    }

    //show score for player
    public Score getPlayerScore() {
        return getGameStatsService().getPlayerScore(getGame(), user);
    }

    //show scores for all teams
    public List<Score> getScoreboard() {
        return getGameStatsService().getTeamScores(getGame());
    }

    //TODO
    public Game nextRound(int guessedRight) {
        //get current task
        //int guessedRight: -1 -> foul, 0 -> not guessed, 1 -> guessed right
        //gameService.updateScores(game, guessedRight, termsService.getNextTerm(game), task);
        return null;
    }

    //TODO: get time from timer
    public Game endGame() {
        int seconds = 0;
        displayInfo("Game stopped", "Game stopped successfully.");
        return gamePlayService.stopGame(getGame());
    }
}
