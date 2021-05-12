package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

// handles the GUI for the player selection phase (join.xhtml)
// after starting a game and before entering the game room
// team-leaders get to select their players (if not already set beforehand)
@Component
@Scope("session")
public class GameStartController extends GameController implements Serializable {

    @Autowired
    private GameStartService gameStartService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private PlayerAvailability player;
    private String teamName;
    private boolean teamComplete = false;

    // starts the game by the game creator
    public String startGame(Game game) {
        setUser();
        teamComplete = false;
        if (game.isActive()) {
            displayError("Game already started", "Please use JOIN GAME to join this game.");
        } else if (game.getTeamList().stream().map(t -> t.getTeamPlayers().size()).reduce(0, Integer::sum) == game.getCountPlayers()
                && game.getTeamList().stream().noneMatch(t -> t.getTeamPlayers().contains(getUser()))) {
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

    // let all other team representatives join an active game
    public String joinGame() {
        setUser();
        setGame(gameStartService.getActiveGame(getUser()));

        // check for rejoin: if game was already entered before
        boolean allTeamsEntered;
        try {
            allTeamsEntered = gameStartService.getGameJoinController().allReadyToStart(getGame());
        } catch (NullPointerException | NoSuchElementException e) {
            allTeamsEntered = false;
        }

        // redirect to appropriate page
        if (allTeamsEntered && teamComplete) {
            return "/secured/game_room/gameRoom.xhtml?faces-redirect=true";
        } else {
            teamComplete = false;
            try {
                setGame(gameStartService.joinGame(getGame(), getUser()));
                return "/secured/game_room/join.xhtml?faces-redirect=true";
            } catch (NoSuchElementException e) {
                displayError("No games", e.getMessage());
            } catch (IllegalArgumentException e) {
                displayError("All teams already full", e.getMessage());
            }
            return "";
        }
    }

    // get list of player availabilities (player free to select or already assigned to a team)
    public List<PlayerAvailability> getPlayerAvailability() {
        return gameStartService.getGameJoinController().getPlayerAvailability(getGame());
    }

    // socket-channel update, used to query if all teams ready to join
    public void setAllTeamsReady() {
        gameStartService.getGameJoinController().updateJoinChannel();
    }

    // select a player in the GUI to add to current team
    public void selectPlayer(SelectEvent<PlayerAvailability> event) {
        this.player = event.getObject();
        setGame(gameStartService.selectPlayer(player.getUser()));
    }

    // announce team ready to play, try to join if other teams ready too
    // triggered by "join game" button
    public void finishTeamAssign() {
        sessionInfoBean.setCurrentGame(getGame());
        try {
            setGame(gameStartService.finishTeamAssign(teamName));
            teamComplete = true;
        } catch (IOException e) {
            displayError("Redirect error", e.getMessage());
        } catch (IllegalArgumentException e) {
            displayError("Not so fast", e.getMessage());
        }
    }

    // enter game room if all teams ready to play
    // triggered by socket update
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