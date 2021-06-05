package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * handles the GUI for the player selection phase (join.xhtml)
 * after starting a game and before entering the game room
 * team-leaders get to select their players (if not already set beforehand)
 */
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

    /**
     * starts the game by the game creator
     * @param game
     * @return
     */
    public String startGame(Game game) {
        setUser();
        teamComplete = false;
        if (game.isActive()) {
            displayError("Game already started", "Please use JOIN GAME to join this game.");
        } else if (getUserService().getUserByRaspberry(getUser().getRaspberry()).size() < game.getCountPlayers()) {
            displayError("Not enough players", "You have not enough players in your friend list. " +
                    "Make sure everyone has assigned your Raspberry Pi in their profile.");
        } else if (isTeamFull(game)) {
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

    private boolean isTeamFull(Game game) {
        int countAssignedPlayers = game.getTeamList().stream().map(t -> t.getTeamPlayers().size()).reduce(0, Integer::sum);
        boolean userNotAssigned = game.getTeamList().stream().noneMatch(t -> t.getTeamPlayers().contains(getUser()));
        return countAssignedPlayers == game.getCountPlayers() && userNotAssigned;
    }

    /**
     * let all other team representatives join an active game
     * @return
     */
    public String joinGame() {
        setUser();
        try {
            setGame(gameStartService.getActiveGame(getUser()));
        } catch (NullPointerException e) {
            displayError("No active Games", "Start a game in Game Creation first.");
            return "";
        }

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
            } catch (NoSuchElementException | NullPointerException e) {
                displayError("No games", "No active game found. Ask a game manager to create a new game.");
            } catch (IllegalArgumentException e) {
                displayError("All teams already full", e.getMessage());
            }
            return "";
        }
    }

    /**
     * get list of player availabilities (player free to select or already assigned to a team)
     * @return List<PlayerAvailability>
     */
    public List<PlayerAvailability> getPlayerAvailability() {
        return gameStartService.getGameJoinController().getPlayerAvailability(getGame());
    }

    /**
     * socket-channel update, used to query if all teams ready to join
     */
    public void setAllTeamsReady() {
        gameStartService.getGameJoinController().updateJoinChannel(getGame());
    }

    /**
     * select a player in the GUI to add to current team
     * @param event
     */
    public void selectPlayer(SelectEvent<PlayerAvailability> event) {
        this.player = event.getObject();
        setGame(gameStartService.selectPlayer(player.getUser()));
    }

    /**
     * announce team ready to play, try to join if other teams ready too
     * triggered by "join game" button
     */
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

    /**
     * enter game room if all teams ready to play
     * triggered by socket update
     */
    public void enterGame() {
        if (teamComplete) {
            try {
                setGame(gameStartService.enterGame());
            } catch (IOException e) {
                displayError("Redirect error", e.getMessage());
            }
        }
    }

    /**
     * reset all the player assignments
     */
    public void resetAssignments() {
        teamComplete = false;

        // reset teams
        for (Team t : getGame().getTeamList()) {
            t.setTeamPlayers(new ArrayList<>());
            gameStartService.getTeamService().saveTeam(t);
        }

        gameStartService.getGameJoinController().resetAssignments(getGame());
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
