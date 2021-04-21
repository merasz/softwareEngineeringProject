package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.GameStatsService;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class GameController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private GameStatsService gameStatsService;

    @Autowired
    private TermsService termsService;

    private List<Game> games;
    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void doSaveGame() {
        try {
            game = gameService.saveGame(game);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public Topic getGameTopic() {
        return this.topicService.getTopicByName(game.getTopic());
    }


    //endregion
}
