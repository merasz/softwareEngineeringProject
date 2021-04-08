package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    private int scoreToWin;
    private int totalScore;
    private int nrRound;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pausedTime;

    //TODO: change type to Raspberry
    private int raspberryId;

    @ManyToMany
    private List<Team> teamList;

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

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> playerList) {
        this.teamList = playerList;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<Score> getScores() {
        return scores;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPausedTime() {
        return pausedTime;
    }

    public void setPausedTime(Date pausedTime) {
        this.pausedTime = pausedTime;
    }

    public Game(int scoreToWin, int totalScore, int nrRound, Topic topic, int raspberryId, Date startTime, List<Team> teamList) {
        this.scoreToWin = scoreToWin;
        this.totalScore = totalScore;
        this.nrRound = nrRound;
        this.topic = topic;
        this.raspberryId = raspberryId;
        this.startTime = startTime;
        this.teamList = teamList;
    }

    public Game() {
    }
}
