package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;

@Component
@Scope("session")
public class GameStartController extends GameController implements Serializable {

    @Autowired
    private GameStartService gameStartService;

    private PlayerAvailability player;
    private String teamName;
    private boolean teamComplete = false;

    public String startGame(Game game) {
        setUser();
        setGame(gameStartService.startGame(game, getUser()));
        return "/secured/game_room/join.xhtml?faces-redirect=true";
    }

    public String joinGame() {
        setUser();
        try {
            setGame(gameStartService.joinGame(getUser()));
            return "/secured/game_room/join.xhtml";
        } catch (NoSuchElementException e) {
            displayError("No games", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            displayError("All teams already logged in", "You've been probably already assigned to a team.");
        }
        return "";
    }

    public void selectPlayer(SelectEvent<PlayerAvailability> event) {
        this.player = event.getObject();
        gameStartService.selectPlayer(player.getUser());
    }

    public void enterGame(boolean allTeamsReady) {
        try {
            teamComplete = true;
            System.out.println("-------  " + allTeamsReady + "  -------");
            if (allTeamsReady) {
                gameStartService.enterGame(teamName);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/game_room/gameRoom.xhtml");
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (IOException e) {
            displayError("Redirect error", e.getMessage());
        } catch (IllegalArgumentException e) {
            displayError("Not so fast", e.getMessage());
        }
    }

    //region getter & setter
    public String getTeamName() {
        this.teamName = gameStartService.getTeam().getTeamName();
        return teamName == null ? "" : teamName;
    }

    public void setTeamName(String name) {
        this.teamName = name;
    }

    public String getTeamSizeString() {
        return gameStartService.getTeamSizeString();
    }

    public PlayerAvailability getPlayer() {
        return player;
    }

    public void setPlayer(PlayerAvailability player) {
        this.player = player;
    }

    public boolean getTeamReady() {
        return gameStartService.teamReady();
    }

    public boolean isTeamComplete() {
        return teamComplete;
    }

    //endregion
}
