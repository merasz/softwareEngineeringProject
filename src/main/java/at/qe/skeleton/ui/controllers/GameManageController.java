package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.GameManageService;
import at.qe.skeleton.services.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Scope("view")
public class GameManageController extends GameController implements Serializable {
    @Autowired
    private GameManageService gameManageService;

    @Autowired
    private TermsService termsService;

    private int scoreToWin;
    private int countPlayers;
    private int countTeams;
    private Topic topic;

    //TODO: get connected Raspberry
    public Game createGame() {
        int raspberryId = 0;
        try {
            setGame(gameManageService.createGame(scoreToWin, countPlayers, topic, raspberryId, countTeams));
            displayInfo("Game created", "Game created successfully.");
        } catch (IllegalArgumentException e) {
            displayError("Game creation failed", e.getMessage());
        }
        return getGame();
    }

    public Game startGame() {
        return gameManageService.startGame(getGame());
    }

    public void deleteGame() {
        gameManageService.deleteGame(getGame());
        displayInfo("Game deleted", "Game deleted successfully.");
    }

    public void setScoreToWin(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    public void setTopic(Topic topic) {
        try {
            this.topic = termsService.setGameTopic(topic);
        } catch (IllegalArgumentException e) {
            displayError("Too few terms in topic", e.getMessage());
        }
    }

    public String getTimePlayed() {
        try {
            return new SimpleDateFormat("hh:mm:ss")
                    .format(new Date(getGameStatsService().getSecondsPlayed(getGame()) * 1000L));
        } catch (UnsupportedOperationException e) {
            return "...unfinished";
        }
    }
}
