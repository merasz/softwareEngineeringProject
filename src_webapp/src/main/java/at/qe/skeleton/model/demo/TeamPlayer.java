package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;

/**
 * A wrapper class used in gamePlay for in-game player-list
 */
public class TeamPlayer {

    User player;
    Team team;

    public TeamPlayer(User player, Team team) {
        this.player = player;
        this.team = team;
    }

    public TeamPlayer() {
    }

    public Team getTeam() {
        return team;
    }

    public User getPlayer() {
        return player;
    }
}
