package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.GameManageService;
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

    private int scoreToWin;
    private int countPlayers;
    private int countTeams;
    private Topic topic;

    public Game createGame() {
        setUser();
        try {
            setGame(gameManageService.createGame(scoreToWin, countPlayers, topic, getUser().getRaspberry(), countTeams));
            displayInfo("Game created", "Game created successfully.");
        } catch (IllegalArgumentException e) {
            displayError("Game creation failed", e.getMessage());
        }
        return getGame();
    }

    public String startGame() {
        try {
            setGame(gameManageService.startGame(getGame(), getUser()));
            return "/secured/game_room/join.xhtml?faces-redirect=true";
        } catch (NullPointerException e) {
            displayError("No available Game", "Please create a game first.");
            return "";
        }
    }

    public String startEnterGame() {
        try {
            setGame(gameManageService.startGame(getGame(), getUser()));
            return "/secured/game_room/gameRoom.xhtml?faces-redirect=true";
        } catch (NullPointerException e) {
            displayError("No available Game", "Please create a game first.");
            return "";
        }
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
            this.topic = getTermsService().setTopic(topic);
        } catch (IllegalArgumentException e) {
            displayError("Too few terms", e.getMessage());
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

    public String test() {
        scoreToWin = 12;
        countPlayers = 4;
        countTeams = 2;
        topic = getTopicService().loadTopic("Animals");
        setGame(createGame());
        return startGame();
    }

    //region getter & setter
    public Topic getTopic() {
        return topic;
    }

    private boolean testCond = false;
    private String condMet;
    public boolean isTestCond() {
        return testCond;
    }
    public String getCondMet() {
        return testCond ? "condition met" : "waiting for something to happen...";
    }
    public String setTestCond() {
        this.testCond = true;
        return "/secured/welcome.xhtml?faces-redirect=true";
    }
    public void setCondMet(String condMet) {
        this.condMet = condMet;
    }

    //endregion
}
