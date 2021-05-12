package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;
    private String gameName;
    private int scoreToWin;
    private int totalScore;
    private int countPlayers;
    private int teamSize;
    private int nrRound;
    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pausedTime;

    @ManyToOne(optional = true)
    private Raspberry raspberry;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Team> teamList;

    //@ElementCollection
    //private List<Integer> deviceTeamIdList;

    @ManyToOne
    private Topic topic;

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<Score> scoreList;

    public Game() {
        this.teamList = new ArrayList<>();
        this.totalScore = 0;
        this.nrRound = 1;
        this.active = false;
        this.countPlayers = 0;
        this.teamSize = 0;
    }

    public Game(int scoreToWin, int countPlayers, int teamSize, Topic topic, Raspberry raspberry) {
        this.scoreToWin = scoreToWin;
        this.totalScore = 0;
        this.countPlayers = countPlayers;
        this.teamSize = teamSize;
        this.nrRound = 1;
        this.topic = topic;
        this.raspberry = raspberry;
        this.teamList = new ArrayList<>();
        this.active = false;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public Integer getGameId() { return gameId; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId.equals(game.gameId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.gameId);
        return hash;
    }
}
