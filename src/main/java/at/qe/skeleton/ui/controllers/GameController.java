package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.GameStatsService;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.*;
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
    private SessionInfoBean sessionInfoBean;

    private List<Game> games;
    private Game game;
    private User user;
    private DonutChartModel model;

    public GameController(){
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

    public DonutChartModel getModel() {
        return model;
    }

    public void setModel(DonutChartModel model) {
        this.model = model;
    }
    //endregion
}
