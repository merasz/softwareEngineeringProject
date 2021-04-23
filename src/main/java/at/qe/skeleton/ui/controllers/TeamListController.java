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
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
public class TeamListController {

    private static final long serialVersionUID = 1L;

    private Game game;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    public List<Team> getTeams() {
        return teamService.getAllTeams();
    }

    public List<Team> getTeamsByGame() {
        if(game == null) {
            return new ArrayList<Team>();
        }
        return teamService.getTeamsByGame(this.game);
    }

    public void doSetGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
