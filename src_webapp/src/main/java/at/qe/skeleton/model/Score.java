package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne
    private Game game;

    /**
     * Constructor for Spring
     */
    public Score() {
        this.totalRoundScore = 0;
    }

    /**
     * Constructor to use the score with a specified user, team and game.
     * @param user the user related to the score
     * @param team the team related to the score
     * @param game the game related to the score
     */
    public Score(User user, Team team, Game game) {
        this.user = user;
        this.team = team;
        this.game = game;
    }

    /**
     * Constructor to use the score with specific details
     * @param scoreId the id related to the score
     * @param totalRoundScore the total score of the round
     * @param team the team related to the score
     * @param game the game related to the score
     */
    public Score(int scoreId, Long totalRoundScore, Team team, Game game) {
        this.scoreId = scoreId;
        this.totalRoundScore = totalRoundScore.intValue();
        this.team = team;
        this.game = game;
    }

    /**
     * Get the score's id
     * @return an int representing the score's id
     */
    public int getScoreId() {
        return scoreId;
    }

    /**
     * Set the score's id
     * @param scoreId an int representing the score's id
     */
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * Get the total score of the round
     * @return an int representing the total score of the round
     */
    public int getTotalRoundScore() {
        return totalRoundScore;
    }

    /**
     * Set the total score of the round
     * @param totalRoundScore an int representing the total score of the round
     */
    public void setTotalRoundScore(int totalRoundScore) {
        this.totalRoundScore = totalRoundScore;
    }

    /**
     * Get the user related to the score
     * @return the user related to the score
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user related to the score
     * @param user the user related to the score
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the team related to the score
     * @return the team related to the score
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Set the team related to the score
     * @param team the team related to the score
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Get the game related to the score
     * @return the game related to the score
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the game related to the score
     * @param game the game related to the score
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
