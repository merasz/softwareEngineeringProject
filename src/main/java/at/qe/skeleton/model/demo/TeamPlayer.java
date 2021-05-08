package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;

public class TeamPlayer {

    User player;
    Team team;

    public TeamPlayer(User player, Team team) {
        this.player = player;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public User getPlayer() {
        return player;
    }
}
