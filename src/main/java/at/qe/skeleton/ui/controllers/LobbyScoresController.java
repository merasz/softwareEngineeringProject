package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.DonutChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.*;
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

    @Autowired
    private ScoreService scoreService;

    private Collection<Topic> mostPopularTopics;
    private Collection<User> mostValuedUsers;
    private Collection<Integer> mostValuedUsersScores;
    private Map<User, Integer> mostValuedUsersWithScore;
    private Map<User, Integer> mostValuedUsersWithScoreAGame;

    @PostConstruct
    public void init(){
        mostPopularTopics = gameService.getMostPopularTopics();
        mostValuedUsers = scoreService.getMostValuedUsers();
        mostValuedUsersScores = scoreService.getMostValuedUserScores();
        mostValuedUsersWithScore = scoreService.getUsersWithScores();
        mostValuedUsersWithScoreAGame = scoreService.getUsersWithScoreAGame();
    }

    public Collection<Topic> getMostPopularTopics() {
        return mostPopularTopics.stream().limit(3).collect(Collectors.toList());
    }

    public Collection<User> getMostValuedUsers() {
        return mostValuedUsers.stream().limit(10).collect(Collectors.toList());
    }

    public Collection<Integer> getMostValuedUsersScores() {
        return mostValuedUsersScores.stream().limit(10).collect(Collectors.toList());
    }

    public Map<User, Integer> getMostValuedUsersWithScore() {
        return mostValuedUsersWithScore;
    }

    public Map<User, Integer> getMostValuedUsersWithScoreAGame() {
        return mostValuedUsersWithScoreAGame;
    }

    public List<Map.Entry<User, Integer>> getHighscores() {
        Set<Map.Entry<User, Integer>> productSet = mostValuedUsersWithScore.entrySet();
        return new ArrayList<Map.Entry<User, Integer>>(productSet);
    }

    public List<Map.Entry<User, Integer>> getHighscoresAGame() {
        Set<Map.Entry<User, Integer>> productSet = mostValuedUsersWithScore.entrySet();
        return new ArrayList<Map.Entry<User, Integer>>(productSet);
    }

}
