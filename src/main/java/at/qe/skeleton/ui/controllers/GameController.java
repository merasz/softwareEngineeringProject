package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("view")
public class GameController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    private List<Game> games;
    private Game game;

    public List<Game> getGames() {
        games = gameService.getGameRepository().findAll();
        return games;
    }

    public void stopOrDeleteGame() {
        if (game.isFinished) {
            deleteGame();
        } else {
            stopGame();
        }
    }

    private void deleteGame() {
        gameService.deleteGame(game);
        displayInfo("Game deleted", "Game deleted successfully.");
    }

    public Game startGame() {
        displayInfo("Game started", "Game started successfully.");
        game = gameService.createGame();
        return game;
    }

    public Game stopGame() {
        displayInfo("Game stopped", "Game stopped successfully.");
        game = gameService.haltGame(game);
        return game;
    }


}
