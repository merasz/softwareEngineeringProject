package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.User;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.GameStatsService;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.services.UserStatsService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * Controller for the user view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class UserScoresController extends Controller implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    /**
     * Attribute to cache the currently displayed user
     */
    private User user;

    @PostConstruct
    public void init(){
        if(this.user == null){
            setLoggedInUser();
        }
    }

    private void setLoggedInUser() {
        user = sessionInfoBean.getCurrentUser();
        System.out.println(user);
    }
    
    public void setUser(User user) {
        this.user = user;
        doReloadUser();
    }

    /**
     * Returns the currently displayed user.
     *
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

    public List<Score> getBestScoresForUser() {
        return userStatsService.getBestScoresFromUser(this.user);
    }

    public List<Score> getLatestScoresForUser() {
        return userStatsService.getBestScoresFromUser(this.user);
    }


}
