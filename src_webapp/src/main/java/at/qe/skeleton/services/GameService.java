package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("application")
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameJoinController gameJoinController;

    /**
     * Function returns all active games.
     * @return collection of games
     */
    public Collection<Game> getAllActiveGames() {
        return gameRepository.findAllActive();
    }

    /**
     * creates a new game and returns it
     * @return Game object
     */
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    /**
     * Reloads a game from the database.
     * @param game
     * @return game object
     */
    public Game reloadGame(Game game) {
        return gameRepository.findByGameId(game.getGameId());
    }

    /**
     * Returns the current played game by the given Raspberry Id
     * @param raspiId
     * @return game object
     */
    public Game getRunningGameByRaspberry(Integer raspiId) {
        return gameRepository.findActiveGameByRaspberry(raspiId);
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public ScoreRepository getScoreRepository() {
        return scoreRepository;
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public GameJoinController getGameJoinController() {
        return gameJoinController;
    }

    /**
     * returns all games
     * @return collection of games
     */
    public Collection<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * returns the most popular topics
     * @return collection of topics
     */
    public Collection<Topic> getMostPopularTopics() {
        return gameRepository.getMostPopularTopics();
    }
}
