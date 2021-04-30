package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Score implements Serializable {
    private static final long serialVersionUID = 1L;

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

    //TODO: add new parameters to constructor
    public Score(User user, Team team, Game game) {
        this.user = user;
        this.team = team;
        this.game = game;
    }

    public Score(int scoreId, int totalRoundScore, User user, Team team, Game game) {
        this.scoreId = scoreId;
        this.totalRoundScore = totalRoundScore;
        this.user = user;
        this.team = team;
        this.game = game;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
