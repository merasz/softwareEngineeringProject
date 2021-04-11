package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    private int scoreToWin;
    private String guessingField;
    private int teamId;
    private int virtualRoomId;
    private int totalScore;
    private int nrRound;
    private int raspberryId;

    @ManyToMany
    private List<Team> playerList;

    //private List<Integer> deviceTeamIdList;

    @ManyToOne
    private Topic topic;

    @OneToMany(mappedBy = "game")
    private List<Score> scores;

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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getNrRound() {
        return nrRound;
    }

    public void setNrRound(int nrRound) {
        this.nrRound = nrRound;
    }

    public int getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(int raspberryId) {
        this.raspberryId = raspberryId;
    }

    public List<Team> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Team> playerList) {
        this.playerList = playerList;
    }

    public Game(int gameId, int scoreToWin, String guessingField, int teamId, int virtualRoomId, int totalScore, int nrRound, int raspberryId, List<Team> playerList, List<Integer> deviceTeamIdList) {
        this.gameId = gameId;
        this.scoreToWin = scoreToWin;
        this.guessingField = guessingField;
        this.teamId = teamId;
        this.virtualRoomId = virtualRoomId;
        this.totalScore = totalScore;
        this.nrRound = nrRound;
        this.raspberryId = raspberryId;
        this.playerList = playerList;
    }

    public Game() {
    }
}
