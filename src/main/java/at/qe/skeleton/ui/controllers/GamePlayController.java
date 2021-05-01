package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GamePlayService;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Scope("view")
public class GamePlayController extends GameController implements Serializable {

    @Autowired
    private GamePlayService gamePlayService;

    private PlayerAvailability player;
    private String teamName;
    private boolean teamComplete = false;
    private boolean paused = false;
    private int guessAccepted = 0;

    /*
    public String joinGame() {
        setUser();
        try {
            setGame(gamePlayService.joinGame(getUser()));
            return "/secured/game_room/join.xhtml";
        } catch (NoSuchElementException e) {
            displayError("No games", e.getMessage());
        }
        return "";
    }

    public void selectPlayer(SelectEvent<PlayerAvailability> event) {
        this.player = event.getObject();
        gamePlayService.selectPlayer(player.getUser());
    }

    public boolean getTeamReady() {
        return gamePlayService.teamReady();
    }

    public String startGame(boolean allTeamsReady) {
        try {
            gamePlayService.startGame(teamName);
            teamComplete = true;
            if (allTeamsReady) {
                return "/secured/game_room/gameRoom.xhtml?faces-redirect=true";
            }
        } catch (IllegalArgumentException e) {
            displayError("Not so fast", e.getMessage());
        }
        return "";
    }
    */

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
        return Collections.singletonList(getGameStatsService().getPlayerScore(getGame(), getUser()));
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

    public Game endGame() {
        return gamePlayService.stopGame(getGame());
    }

    public Game pauseGame() {
        paused = !paused;
        return gamePlayService.pauseGame(getGame(), paused);
    }

    //region getter & setter
    public Task getTask() {
        return gamePlayService.getTask(getGame());
    }

    public boolean isPaused() {
        return paused;
    }

    public List<Team> getTeams() {
        return getGame().getTeamList();
    }

    public PlayerAvailability getPlayer() {
        return player;
    }

    public void setPlayer(PlayerAvailability player) {
        this.player = player;
    }

    /*
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String name) {
        this.teamName = name;
    }

    public String getTeamSizeString() {
        return gamePlayService.getTeamSizeString();
    }

    public boolean isTeamComplete() {
        return teamComplete;
    }
     */


    //endregion
}
