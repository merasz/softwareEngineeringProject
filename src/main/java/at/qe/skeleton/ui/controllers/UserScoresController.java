package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
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
@Scope("view")
public class UserScoresController extends Controller implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SessionInfoBean sessionInfoBean;


    private DonutChartModel model;

    public UserScoresController(){
        model = new DonutChartModel();
        updateChart();
    }

    public void updateChart() {

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Game 1", 24);
        circle1.put("Game 2", 12);
        circle1.put("Game 3", 6);
        circle1.put("Game 4", 4);

        model.addCircle(circle1);
        model.setTitle("Current Players in each Game");
        model.setLegendPosition("w");
    }

    public DonutChartModel getModel() {
        return model;
    }

    public void setModel(DonutChartModel model) {
        this.model = model;
    }

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
        return userStatsService.getLatestScoresFromUser(this.user);
    }

    public int getGameCountForUser() { return userStatsService.getGameCountByUser(this.user); }

    public int getWonGamesByUser() { return userStatsService.getWonCountByUser(this.user); }

    public int getLostGamesByUser() { return userStatsService.getLostCountByUser(this.user); }

    public List<GameTopicCount> getWonGamesByTopics() {
        return userStatsService.getWonGamesByTopics(this.user);
    }

    public List<Team> getTeamsByPlayer() {
        Set<Team> tmp = new HashSet<>();
        tmp.addAll(teamService.getTeamsByPlayer(this.user));
        List<Team> myTeams = new ArrayList<>();
        for (Team t: tmp) {
            myTeams.add(t);
        }
        return myTeams;
    }

}
