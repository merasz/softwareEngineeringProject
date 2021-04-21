package at.qe.skeleton.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;
    private String gameName;
    private int scoreToWin;
    private int totalScore;
    private int countPlayers;
    private int teamSize;
    private int numberOfTeams;
    private int nrRound;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pausedTime;

    @ManyToOne(optional = true)
    private Raspberry raspberry;

    @ManyToMany
    private List<Team> teamList;

    @ElementCollection
    private List<Integer> deviceTeamIdList;

    @ManyToOne
    private Topic topic;

    @OneToMany(mappedBy = "game")
    private List<Score> scoreList;

    public Game() { }

    public Game(int scoreToWin, int countPlayers, Topic topic, Raspberry raspberry, Date startTime, List<Team> teamList) {
        this.scoreToWin = scoreToWin;
        this.totalScore = 0;
        this.countPlayers = countPlayers;
        this.nrRound = 1;
        this.topic = topic;
        this.raspberry = raspberry;
        this.startTime = startTime;
        this.teamList = teamList;
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

    public int getCountPlayers() {
        return countPlayers;
    }

    public int getNrRound() {
        return nrRound;
    }

    public void incrementNrRound() {
        this.nrRound++;
    }

    public Raspberry getRaspberry() {
        return raspberry;
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

    public int getGameId() { return gameId; }

    public String getGameName() { return gameName; }

    public List<Score> getScoreList() { return scoreList; }

    public void setGameName(String gameName) { this.gameName = gameName; }

    public void setCountPlayers(int countPlayers) { this.countPlayers = countPlayers; }

    public void setNrRound(int nrRound) { this.nrRound = nrRound; }

    public void setRaspberry(Raspberry raspberry) { this.raspberry = raspberry; }

    public void setScoreList(List<Score> scoreList) { this.scoreList = scoreList; }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getMaxPlayers() {
        return teamSize * numberOfTeams;
    }
}
