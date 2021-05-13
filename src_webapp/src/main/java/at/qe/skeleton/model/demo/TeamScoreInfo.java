package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Team;

/**
 * Just combines a user and its status.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
public class TeamScoreInfo {

    private Team team;
    private Integer score;

    public TeamScoreInfo(Team team, Integer score) {
            super();
            this.team = team;
            this.score = score;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
