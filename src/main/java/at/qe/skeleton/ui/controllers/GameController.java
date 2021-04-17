package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.GameStatsService;
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
    private GameStatsService gameStatsService;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    //endregion
}
