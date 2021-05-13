package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private GameService gameService;

    @Autowired
    private ScoreService scoreService;

    private Collection<Topic> mostPopularTopics;
    private Map<User, Integer> mostValuedUsersWithScore;

    @PostConstruct
    public void init(){
        mostPopularTopics = gameService.getMostPopularTopics();
        mostValuedUsersWithScore = scoreService.getUsersWithScores();
    }

    public Collection<Topic> getMostPopularTopics() {
        return mostPopularTopics.stream().limit(3).collect(Collectors.toList());
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
