package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @ElementCollection
    private List<Integer> deviceTeamIdList;

    @ManyToOne
    private Topic topic;

    @OneToMany(mappedBy = "game")
    private List<Score> scoreList;

    public Game() {
    }

    public Game(int gameId, int scoreToWin, int totalScore, Topic topic, int raspberryId,
                Date startTime, Date endTime, Date pausedTime, List<Team> teamList,
                List<Integer> deviceTeamIdList, List<Score> scoreList) {
        this.gameId = gameId;
        this.scoreToWin = scoreToWin;
        this.totalScore = totalScore;
        this.nrRound = 1;
        this.topic = topic;
        this.raspberryId = raspberryId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teamList = teamList;
        this.pausedTime = pausedTime;
        this.deviceTeamIdList = deviceTeamIdList;
        this.scoreList = scoreList;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getNrRound() {
        return nrRound;
    }

    public void incrementNrRound() {
        this.nrRound++;
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

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setScores(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public List<Score> getScores() {
        return scoreList;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Integer> getDeviceTeamIdList() {
        return deviceTeamIdList;
    }

    public void setDeviceTeamIdList(List<Integer> deviceTeamIdList) {
        this.deviceTeamIdList = deviceTeamIdList;
    }

    public Game(int scoreToWin, int totalScore, Topic topic, int raspberryId, Timestamp timestamp, List<Team> teamList) {
        this.nrRound = 1;
    }
}
