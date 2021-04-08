package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Score implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scoreId;

    private int totalRoundScore;

    @ManyToOne
    private Team team;

    @ManyToMany
    private List<Term> guessedTerms;

    @ManyToMany
    private List<Term> notGuessedTerms;

    private int virtualRoomId;

    @ManyToOne
    private Game game;

    public Score() {
    }

    public Score(int scoreId, int totalRoundScore, Team team, int virtualRoomId) {
        this.scoreId = scoreId;
        this.totalRoundScore = totalRoundScore;
        this.team = team;
        this.virtualRoomId = virtualRoomId;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getTotalRoundScore() {
        return totalRoundScore;
    }

    public void setTotalRoundScore(int totalRoundScore) {
        this.totalRoundScore = totalRoundScore;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getVirtualRoomId() {
        return virtualRoomId;
    }

    public void setVirtualRoomId(int virtualRoomId) {
        this.virtualRoomId = virtualRoomId;
    }
}
