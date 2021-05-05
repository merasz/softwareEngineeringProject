package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.*;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
@Scope("session")
public class GameController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private GameStatsService gameStatsService;

    @Autowired
    private TermsService termsService;

    @Autowired
    private GameListController gameListController;

    @Autowired
    UserService userService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private List<Game> games;
    private Game game;
    private User user;


    public void doSaveGame() {
        try {
            game = gameService.saveGame(game);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public List<Game> getGames() {
        games = gameService.getGameRepository().findAll();
        return games;
    }

    //region getter & setter
    public GameStatsService getGameStatsService() {
        return gameStatsService;
    }

    public TermsService getTermsService() {
        return termsService;
    }

    public TopicService getTopicService() {
        return topicService;
    }

    public User getUser() {
        return user;
    }

    public void setUser() {
        this.user = sessionInfoBean.getCurrentUser();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Topic getGameTopic() {
        return this.topicService.getTopicByName(game.getTopic());
    }


    public GameListController getGameListController() {
        return gameListController;
    }

    public void setGameListController(GameListController gameListController) {
        this.gameListController = gameListController;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    //endregion
}
