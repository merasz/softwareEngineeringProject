package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.GameStatsService;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

@Component
@Scope("view")
public class GameCreationController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    @Autowired
    private TopicService topicService;

    private Topic currentTopic;


    private Game game;
    private List<Topic> topicsList;

    @PostConstruct
    public void init() {
        setTopicsList();
        doCreateNewGame();
    }

    public void doCreateNewGame() {
        game = new Game();
    }

    public void doSaveGame() {
        System.out.println(currentTopic);
        try {
            game = gameService.saveGame(game);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Topic> getTopicsList() {
        return topicsList;
    }

    public void setCurrentTopic(Topic currentTopic) {
        this.currentTopic = currentTopic;
    }

    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public void setTopicsList() {
        List<Topic> arr = new ArrayList<>();
        arr.addAll(topicService.getAllTopics());
        topicsList =  arr;
    }
}
