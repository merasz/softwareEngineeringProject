package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.services.UserStatsService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.DonutChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for the user view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class LobbyScoresController extends Controller implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private Collection<Topic> mostPopularTopics;

    @PostConstruct
    public void init(){
        mostPopularTopics = gameService.getMostPopularTopics();
    }

    public Collection<Topic> getMostPopularTopics() {
        return mostPopularTopics.stream().limit(3).collect(Collectors.toList());
    }

}
