package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Raspberry;
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
            setGame(gameManageService.startGame(getGame(), countPlayers));
            return "/secured/game_room/join.xhtml?faces-redirect=true";
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
            this.topic = getTermsService().setGameTopic(topic);
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

    public void test() {
        System.out.println("\n-----\ntest\n-----\n");
        scoreToWin = 12;
        countPlayers = 4;
        countTeams = 2;
        setGame(createGame());
    }

    //region getter & setter
    public Topic getTopic() {
        return topic;
    }

    //endregion
}
