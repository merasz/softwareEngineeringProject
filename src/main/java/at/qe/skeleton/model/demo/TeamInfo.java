package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;

import java.io.Serializable;

public class TeamInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    Team team;
    int maxTeamSize;

    public TeamInfo(Game game, Team team) {
        this.team = team;
        this.maxTeamSize = game.getTeamSize();
    }

    public Team getTeam() {
        return team;
    }

    public String getTeamName() {
        return team.getTeamName();
    }

    public int getCurrentTeamSize() {
        return team.getTeamPlayers().size();
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }
}
