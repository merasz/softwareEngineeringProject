package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component
@Scope("view")
public class TeamController extends Controller implements Serializable {

    @Autowired
    TeamService teamService;

    private Team team;

    @PostConstruct
    public void init() {
        doCreateTeam();
    }

    public void doCreateTeam() {
        team = new Team();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void doSaveTeam(Game game) {
        team.setGame(game);
        try {
            team = teamService.saveTeam(team);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public void doSaveUserToTeam() {
        System.out.println(team);
        try {
            team = teamService.saveTeam(team);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }

    }

}
