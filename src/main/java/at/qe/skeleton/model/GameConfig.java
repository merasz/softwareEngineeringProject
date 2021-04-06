package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class GameConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    private int scoreToWin;
    private String guessingField;
    private int teamId;
    private int virtualRoomId;

    @ManyToOne
    private Team team;

    @ManyToOne
    private GuessingTopic guessingTopic;

    //@OneToOne
    //private VirtualRoom virtualRoom;

    public GameConfig() {
    }

    public GameConfig(int gameId, int scoreToWin, String guessingField, int teamId, int virtualRoomId) {
        this.gameId = gameId;
        this.scoreToWin = scoreToWin;
        this.guessingField = guessingField;
        this.teamId = teamId;
        this.virtualRoomId = virtualRoomId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getScoreToWin() {
        return scoreToWin;
    }

    public void setScoreToWin(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    public String getGuessingField() {
        return guessingField;
    }

    public void setGuessingField(String guessingField) {
        this.guessingField = guessingField;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getVirtualRoomId() {
        return virtualRoomId;
    }

    public void setVirtualRoomId(int virtualRoomId) {
        this.virtualRoomId = virtualRoomId;
    }
}
