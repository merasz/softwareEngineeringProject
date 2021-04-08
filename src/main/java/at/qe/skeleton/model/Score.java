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
    private User user;

    @ManyToOne
    private Team team;

    @ManyToMany
    private List<Term> guessedTerms;

    @ManyToMany
    private List<Term> notGuessedTerms;

    @ManyToOne
    private Game game;

    public Score() {
    }

    public Score(int scoreId, int totalRoundScore, Team team) {
        this.scoreId = scoreId;
        this.totalRoundScore = totalRoundScore;
        this.team = team;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Term> getGuessedTerms() {
        return guessedTerms;
    }

    public void setGuessedTerms(List<Term> guessedTerms) {
        this.guessedTerms = guessedTerms;
    }

    public List<Term> getNotGuessedTerms() {
        return notGuessedTerms;
    }

    public void setNotGuessedTerms(List<Term> notGuessedTerms) {
        this.notGuessedTerms = notGuessedTerms;
    }
}
