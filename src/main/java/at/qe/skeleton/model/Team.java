package at.qe.skeleton.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @OneToMany(mappedBy = "team")
    private List<User> teamPlayers;

    private String teamName;

    @ManyToMany
    private List<Game> games;

    //@Type(type = "json")
    //private Map<User,String> deviceIdsFromTeam;

    @OneToMany(mappedBy = "team")
    private List<Score> scores;

    public Team() {
    }

    public Team(Long teamId, List<User> teamPlayers, String teamName) {
        this.teamId = teamId;
        this.teamPlayers = teamPlayers;
        this.teamName = teamName;
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
}
