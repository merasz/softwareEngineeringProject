package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.TeamService;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class TeamController extends Controller implements Serializable {

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamListController teamListController;

    @Autowired
    PlayerListController playerListController;

    private Team team;

    private Game game;

    private User tmpPlayer;

    private List<User> assignablePlayers;


    public void doSetTeam(Game game) {
        this.game = game;
        try {
            team = game.getTeamList().stream().filter(t -> t.getTeamName() == null).collect(Collectors.toList()).get(0);
            PrimeFaces.current().executeScript("PF('teamCreationDialog').show()");
        } catch (IndexOutOfBoundsException e){
            displayError("No more teams to set", "All teams are already set.");
        }
    }

    public void doSaveTeam() {
        if (team.getTeamName() == null || team.getTeamName().isEmpty()) {
            displayError("No team name", "Give this team a name.");
        } else if (game.getTeamList().stream().filter(t -> t.getTeamName() != null)
                .filter(t -> t.getTeamName().equals(team.getTeamName())).count() > 1) {
            displayError("Team name already taken", "Teams in a game should have distinct names.");
        } else {
            team.setGame(game);
            team = teamService.saveTeam(team);
            game = gameService.saveGame(game);
            teamListController.setGame(game);
            PrimeFaces.current().executeScript("PF('teamCreationDialog').hide()");
        }
    }

    public void doSaveUserToTeam() {
        try {
            team = teamService.savePlayerToTeam(team, tmpPlayer);
            playerListController.setTeam(team);
            game = gameService.reloadGame(game);
        } catch (IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }
    }

    public void addPlayerDialog() {
        assignablePlayers = getAssignablePlayers();
        if (team.getTeamPlayers().size() == game.getTeamSize()) {
            displayError("Team full", "There are no more free places in this team.");
        } else {
            PrimeFaces.current().executeScript("PF('playerAddDialog').show()");
        }
    }

    public List<User> getAssignablePlayers() {
        return assignablePlayers = playerListController.getAssignablePlayers(game);
    }

    public void doDeletePlayer() {
        try {
            team = teamService.deletePlayerFromTeam(team,tmpPlayer);
            playerListController.setTeam(team);
            game = gameService.reloadGame(game);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public void doClearTeam() {
        try {
            for (Team t : game.getTeamList()) {
                if (t != null && t.getTeamName() != null) {
                    if (t.getTeamName().equals(team.getTeamName())) {
                        t.setTeamPlayers(new ArrayList<>());
                        t.setTeamName(null);
                        teamService.saveTeam(t);
                        game = gameService.saveGame(game);
                        teamListController.setGame(game);
                    }
                }
            }
            team = null;
        } catch(NoSuchElementException e) {
            displayError("Error", e.getMessage());
        }
    }

    //region getter & setter
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
    //endregion
}
