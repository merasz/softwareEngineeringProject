package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("view")
public class GameListController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    /**
     * returns all games associated with a user (via the raspberry)
     * or all games if user is an admin
     * @return collection of games
     */
    public Collection<Game> getGames() {
        User user = sessionInfoBean.getCurrentUser();

        if (user.getRoles().contains(UserRole.ADMIN)) {
            return gameService.getAllGames();
        } else {
            return gameService.getPersonalGames(user.getRaspberry());
        }
    }

    /**
     * returns all active (started) games
     * @return collection of games
     */
    public Collection<Game> getActiveGames() {
        return gameService.getAllActiveGames();
    }

    /**
     * returns description-string if game not started, started, finished
     * @return String
     */
    public String getStatusString(Game game) {
        if (game.getEndTime() != null) {
            return "finished";
        } else {
            return game.isActive() ? "started" : "not started";
        }
    }

}
