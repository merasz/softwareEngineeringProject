package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import java.io.Serializable;
import java.util.*;

@Component
@Scope("view")
public class GameCreationController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    private String currentTopic;


    private Game game;
    private List<Topic> topicsList;

    private List<User> userList;
    private List<User> tmp;

    @PostConstruct
    public void init() {
        setTopicsList();
        doCreateNewGame();
        setUserList();
    }

    public void doCreateNewGame() {
        game = new Game();
    }

    public void doSaveGame() {
        game.setTopic(topicService.loadTopic(currentTopic));
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

    public void setCurrentTopic(String currentTopic) {
        this.currentTopic = currentTopic;
    }

    public String getCurrentTopic() {
        return currentTopic;
    }

    public void setTopicsList() {
        List<Topic> arr = new ArrayList<>();
        arr.addAll(topicService.getAllTopics());
        topicsList =  arr;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList() {
        List<User> arr = new ArrayList<>();
        arr.addAll(userService.getAllUsers());
        userList = arr;
    }

    public List<User> getTmp() {
        return tmp;
    }

    public void setTmp(List<User> tmp) {
        this.tmp = tmp;
    }

}
