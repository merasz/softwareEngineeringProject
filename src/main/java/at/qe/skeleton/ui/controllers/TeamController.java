package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
public class TeamController extends Controller implements Serializable {

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerListController playerListController;

    private Team team;

    private Game game;

    private User tmpPlayer;

    /*
    @PostConstruct
    public void init() {
        doCreateTeam();
    }
    */

    public void doCreateTeam() {
        this.team = new Team();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void doSaveTeam(Game game) {
        this.team.setGame(game);
        try {
            team = teamService.saveTeam(team);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public void doSaveUserToTeam() {
        try {
            team = teamService.savePlayerToTeam(team,tmpPlayer);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }

    }

    public User getTmpPlayer() {
        return tmpPlayer;
    }

    public void setTmpPlayer(User tmpPlayer) {
        this.tmpPlayer = tmpPlayer;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void doDeletePlayer() {
        try {
            team = teamService.deletePlayerFromTeam(team,tmpPlayer);
            playerListController.setTeam(team);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public void doDeleteTeam() {
        try {
            teamService.deleteTeam(team);
            team = null;
        } catch(IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }

    }

}
