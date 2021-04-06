package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @OneToMany(mappedBy = "team")
    private List<User> teamPlayers;

    @OneToMany(mappedBy = "team")
    private List<GameConfig> gameConfigs;

    private String teamName;

    //deviceIdsFromTeam

    //@OneToMany(mappedBy = "team")
    //private List<Score> scores;

    @ManyToOne
    private VirtualRoom virtualRoom;

    @OneToMany(mappedBy = "team")
    private List<Score> score;

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
