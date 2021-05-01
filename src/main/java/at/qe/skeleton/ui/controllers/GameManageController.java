package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.GameStartService;
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
    private GameStartService gameStartService;

    /*
    private int scoreToWin;
    private int countPlayers;
    private int countTeams;
    private Topic topic;

    public String startGame() {
        try {
            setGame(gameStartService.startGame(getGame(), getUser()));
            return "/secured/game_room/join.xhtml?faces-redirect=true";
        } catch (NullPointerException e) {
            displayError("No available Game", "Please create a game first.");
            return "";
        }
    }

    public String startEnterGame() {
        try {
            setGame(gameStartService.startGame(getGame(), getUser()));
            return "/secured/game_room/gameRoom.xhtml?faces-redirect=true";
        } catch (NullPointerException e) {
            displayError("No available Game", "Please create a game first.");
            return "";
        }
    }

    public void deleteGame() {
        gameStartService.deleteGame(getGame());
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

    public Topic getTopic() {
        return topic;
    }
    */

    public String getTimePlayed() {
        try {
            return new SimpleDateFormat("hh:mm:ss")
                    .format(new Date(getGameStatsService().getSecondsPlayed(getGame()) * 1000L));
        } catch (UnsupportedOperationException e) {
            return "...unfinished";
        }
    }


    //region getter & setter



    //endregion
}
