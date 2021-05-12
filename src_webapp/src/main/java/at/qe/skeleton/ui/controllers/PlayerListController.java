package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class PlayerListController {

    private static final long serialVersionUID = 1L;

    @Autowired
    UserService userService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private Team team;

    public List<User> getPlayerByTeam() {
        if(team == null) {
            return new ArrayList<User>();
        }
        return team.getTeamPlayers();
    }

    // get players assignable to a team:
    // having the same raspberry id and not already assigned to another team
    public List<User> getAssignablePlayers(Game game) {
        Raspberry raspberry = sessionInfoBean.getCurrentUser().getRaspberry();

        if (game == null) {
            return userService.getUserByRaspberry(raspberry);
        }

        List<User> playersInGame = game.getTeamList().stream().flatMap(t -> t.getTeamPlayers().stream()).collect(Collectors.toList());
        return userService.getUserByRaspberry(raspberry).stream().filter(u -> !playersInGame.contains(u)).collect(Collectors.toList());
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
