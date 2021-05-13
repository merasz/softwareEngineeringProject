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

    /**
     * sets a new team, unless all teams for the game are already set
     * @param game
     */
    public void doSetTeam(Game game) {
        this.game = game;
        try {
            team = game.getTeamList().stream().filter(t -> t.getTeamName() == null).collect(Collectors.toList()).get(0);
            PrimeFaces.current().executeScript("PF('teamCreationDialog').show()");
        } catch (IndexOutOfBoundsException e){
            displayError("No more teams to set", "All teams are already set.");
        }
    }

    /**
     * saves a team to the database
     */
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

    /**
     * saves a user in a team
     */
    public void doSaveUserToTeam() {
        try {
            team = teamService.savePlayerToTeam(team, tmpPlayer);
            playerListController.setTeam(team);
            game = gameService.reloadGame(game);
        } catch (IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }
    }

    /**
     * manages the dialog for adding a player to a team
     */
    public void addPlayerDialog() {
        if (team.getTeamPlayers().size() == game.getTeamSize()) {
            displayError("Team full", "There are no more free places in this team.");
        } else {
            PrimeFaces.current().executeScript("PF('playerAddDialog').show()");
        }
    }

    /**
     * get a list of users that are not already assigned to a team
     * @return
     */
    public List<User> getAssignablePlayers() {
        return playerListController.getAssignablePlayers(game);
    }

    /**
     * delete a user from the database
     */
    public void doDeletePlayer() {
        try {
            team = teamService.deletePlayerFromTeam(team,tmpPlayer);
            playerListController.setTeam(team);
            game = gameService.reloadGame(game);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    /**
     * resets a team by emptying all attributes
     */
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

    /**
     * get a team associated to a given game, containing a given user
     * @param user
     * @param game
     * @return
     */
    public Team getTeamByPlayerAndGame(User user, Game game) {return teamService.getTeamByPlayerAndGame(user,game);}

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
