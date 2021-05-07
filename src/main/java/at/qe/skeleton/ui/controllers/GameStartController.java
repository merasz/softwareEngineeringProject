package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        System.out.println(game.getGameName());
        if (game.isActive()) {
            displayError("Game already started", "Please use JOIN GAME to join this game.");
        } else if (game.getTeamList().stream().map(t -> t.getTeamPlayers().size()).reduce(0, Integer::sum) == game.getCountPlayers()
                && game.getTeamList().stream().noneMatch(t -> t.getTeamPlayers().contains(getUser()))) {
            System.out.println(game.getTeamList().stream().map(t -> t.getTeamPlayers().size()).reduce(0, Integer::sum));
            System.out.println(game.getCountPlayers());
            System.out.println(getUser().getUsername());
            System.out.println(game.getTeamList().stream().flatMap(t -> t.getTeamPlayers().stream())
                    .map(User::getUsername).collect(Collectors.toList()));
            displayError("All teams full", "You cannot join this game because all teams are full and you are not assigned to any of them.");
        } else {
            try {
                setGame(gameStartService.startGame(game, getUser()));
                return "/secured/game_room/join.xhtml?faces-redirect=true";
            } catch (IllegalArgumentException e) {
                displayError("All teams already have a team leader", e.getMessage());
            }
        }
        return "";
    }

    public String joinGame() {
        // check for rejoin: if game was already entered before
        boolean allTeamsEntered;
        try {
            allTeamsEntered = gameStartService.getGameJoinController().getAllTeamsReady(getGame(), getUser());
        } catch (NullPointerException e) {
            allTeamsEntered = false;
        }

        // redirect to appropriate page
        if (allTeamsEntered) {
            return "/secured/game_room/gameRoom.xhtml?faces-redirect=true";
        } else {
            setUser();
            teamComplete = false;
            try {
                setGame(gameStartService.joinGame(getUser()));
                return "/secured/game_room/join.xhtml?faces-redirect=true";
            } catch (NoSuchElementException e) {
                displayError("No games", e.getMessage());
            } catch (IllegalArgumentException e) {
                displayError("All teams already full", e.getMessage());
            }
            return "";
        }
    }

    public List<PlayerAvailability> getPlayerAvailability() {
        return gameStartService.getGameJoinController().getPlayerAvailability(getGame());
    }

    public void setAllTeamsReady() {
        gameStartService.getGameJoinController().setAllTeamsReady();
    }

    public void selectPlayer(SelectEvent<PlayerAvailability> event) {
        this.player = event.getObject();
        setGame(gameStartService.selectPlayer(player.getUser()));
    }

    public void finishTeamAssign() {
        try {
            setGame(gameStartService.finishTeamAssign(teamName));
            teamComplete = true;
        } catch (IOException e) {
            displayError("Redirect error", e.getMessage());
        } catch (IllegalArgumentException e) {
            displayError("Not so fast", e.getMessage());
        }
    }

    public void enterGame() {
        if (teamComplete) {
            try {
                setGame(gameStartService.enterGame());
            } catch (IOException e) {
                displayError("Redirect error", e.getMessage());
            }
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
