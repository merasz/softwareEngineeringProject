package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

/**
 * Controller for the user view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("session")
public class UserScoresController extends Controller implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    /**
     * Attribute to cache the currently displayed user
     */
    private User user;

    /**
     * for accessing another player's profile
     */
    private String username;
    private boolean viewPrivate;

    /**
     * display the user's profile
     * @return redirection String to profile.xhtml
     */
    public String showProfile() {
        user = sessionInfoBean.getCurrentUser();
        viewPrivate = true;
        return "/secured/profile.xhtml?faces-redirect=true";
    }

    /**
     * display the profile of another user
     * @return redirection String to profile.xhtml
     */
    public String showOtherProfile() {
        user = userService.loadUser(username);
        username = null;

        if (user == null) {
            displayError("User not found", "Make sure the username is correct.");
            return "";
        }
        viewPrivate = false;
        return "/secured/profile.xhtml?faces-redirect=true";
    }

    /**
     * Sets the user to the class attribute
     * @param user User to set
     */
    public void setUser(User user) {
        this.user = user;
        doReloadUser();
    }

    /**
     * Returns the currently displayed user.
     * @return User
     */
    public User getUser() {
        return user;
    }


    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadUser() {
        user = userService.loadUser(user.getUsername());
    }

    /**
     * Action to get the best scores for the current user
     * @return List of Scores
     */
    public List<Score> getBestScoresForUser() {
        return userStatsService.getBestScoresFromUser(this.user);
    }

    /**
     * Action to get the latest scores of the current user
     * @return List of Scores
     */
    public List<Score> getLatestScoresForUser() {
        return userStatsService.getLatestScoresFromUser(this.user);
    }

    /**
     * Action to get the count of played games for the current user
     * @return Number of games
     */
    public int getGameCountForUser() { return userStatsService.getGameCountByUser(this.user); }

    /**
     * Action to get the count of won games for the current user
     * @return Number of won games
     */
    public int getWonGamesByUser() { return userStatsService.getWonCountByUser(this.user); }

    /**
     * Action to get the count of lost games for the current user
     * @return Number of lost games
     */
    public int getLostGamesByUser() { return userStatsService.getLostCountByUser(this.user); }

    /**
     * Action to get the count of won games per topic for the current user
     * @return List of Topics with Counts
     */
    public List<GameTopicCount> getWonGamesByTopics() {
        return userStatsService.getWonGamesByTopics(this.user);
    }

    /**
     * Action to get all teams a players has played ith
     * @return List of teams
     */
    public List<Team> getTeamsByPlayer() {
        Set<Team> tmp = new HashSet<>(teamService.getTeamsByPlayer(this.user));
        return new ArrayList<>(tmp);
    }

    /**
     * Action to get the top team for a game
     * @param game Game
     * @return Team with highest Score
     */
    public Team getTopTeamFromGame(Game game) {
        return userStatsService.getTopTeamForGame(game);
    }

    //region getter & setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setViewPrivate(boolean viewPrivate) {
        this.viewPrivate = viewPrivate;
    }

    public boolean isViewPrivate() {
        return viewPrivate;
    }
    //endregion
}
