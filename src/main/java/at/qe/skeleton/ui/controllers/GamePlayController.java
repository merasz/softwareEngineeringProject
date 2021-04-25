package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamInfo;
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
@Scope("session")
public class GamePlayController extends GameController implements Serializable {

    @Autowired
    private GamePlayService gamePlayService;

    private TeamInfo teamInfo;
    private Team team;
    private boolean paused = false;
    private int guessAccepted = 0;

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

    public void joinTeam(SelectEvent<TeamInfo> event) {
        this.teamInfo = event.getObject();
        try {
            gamePlayService.joinTeam(teamInfo.getTeam());
            this.team = teamInfo.getTeam();
        } catch (IllegalArgumentException e) {
            displayError("Team full", e.getMessage());
        }
    }

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

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(TeamInfo team) {
        this.teamInfo = team;
    }

    public Team getTeam() {
        return team;
    }

    //endregion
}
