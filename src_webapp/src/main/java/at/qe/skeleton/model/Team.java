package at.qe.skeleton.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToMany(cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<User> teamPlayers;

    private String teamName;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "team")
    private List<Score> scores;

    /**
     * Constructor for Spring
     */
    public Team() { }

    /**
     * Constructor to use the Team
     * @param game the game related to the team
     */
    public Team(Game game) {
        this.game = game;
        this.teamPlayers = new ArrayList<>();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Get the team's id
     * @return a long representing the team's id
     */
    public Long getTeamId() {
        return teamId;
    }

    /**
     * Set the team's id
     * @param teamId a long representing the team's id
     */
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    /**
     * Get the team players
     * @return a list of users representing the team players
     */
    public List<User> getTeamPlayers() {
        Set<User> myUsers = new HashSet<>(teamPlayers);
        return new ArrayList<>(myUsers);
    }

    /**
     * Set the team players
     * @param teamPlayers a list of users representing the team players
     */
    public void setTeamPlayers(List<User> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    /**
     * Get the name of the team
     * @return a String representing the team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Set the name of the team
     * @param teamName a String representing the team name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Get the game related to the team
     * @return the game related to the team
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the game related to the team
     * @param game the game related to the team
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Get the team's scores
     * @return the list of scores related to the team
     */
    public List<Score> getScores() {
        return scores;
    }

    /**
     * Set the team's scores
     * @param scores the list of scores related to the team
     */
    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId.equals(team.teamId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.teamId);
        return hash;
    }
}
