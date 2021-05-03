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

    @ElementCollection
    @MapKeyColumn(name="key") // column name for map "key"
    @Column(name="value") // column name for map "value"
    private Map<User,String> deviceIdsFromTeam = new HashMap<User, String>();

    @OneToMany(mappedBy = "team")
    private List<Score> scores;

    public Team() { }

    public Team(Game game) {
        this.game = game;
        this.teamPlayers = new ArrayList<>();
    }

    public Team(Long teamId, List<User> teamPlayers, String teamName, Game game, List<Score> scores,
                Map<User,String> deviceIdsFromTeam) {
        this.teamId = teamId;
        this.teamPlayers = teamPlayers;
        this.teamName = teamName;
        this.game = game;
        this.scores = scores;
        this.deviceIdsFromTeam = deviceIdsFromTeam;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<User> getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(List<User> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public Map<User, String> getDeviceIdsFromTeam() {
        return deviceIdsFromTeam;
    }

    public void setDeviceIdsFromTeam(Map<User, String> deviceIdsFromTeam) {
        this.deviceIdsFromTeam = deviceIdsFromTeam;
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
