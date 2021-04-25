package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
public class PlayerListController {

    private static final long serialVersionUID = 1L;

    @Autowired
    UserService userService;

    private Team team;

    public List<User> getPlayerByTeam() {
        if(team == null) {
            return new ArrayList<User>();
        }
        return userService.getUserByTeam(this.team);
    }

    public void doSetTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
