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
        System.out.println("test");
        this.team = new Team();
        System.out.println(team);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        System.out.println("setTeam: " + team);
        this.team = team;
    }

    public void doSaveTeam(Game game) {
        System.out.println("Teamcontroller dosaveTeam:"  + team);
        this.team.setGames(game);
        try {
            team = teamService.saveTeam(team);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public void doSaveUserToTeam() {
        try {
            System.out.println(tmpPlayer);
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
            System.out.println(team.getTeamPlayers());
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }
}
