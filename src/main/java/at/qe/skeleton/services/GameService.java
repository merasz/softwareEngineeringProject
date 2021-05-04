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

    protected final int PENALTY_POINTS = -1;
    protected final int SUCCESS_POINTS = 3;
    private int numPlayers = 0;
    private Team currentTeam;
    private User currentPlayer;
    private List<TeamPlayer> players;

    public class TeamPlayer {
        User player;
        Team team;

        TeamPlayer(User player, Team team) {
            this.player = player;
            this.team = team;
        }
    }

    public Game saveGame(Game game) {
        game.setNrRound(0);
        game.setTotalScore(0);
        return gameRepository.save(game);
    }

    public Game reloadGame(Game game) {
        return gameRepository.findByGameId(game.getGameId());
    }

    //region getter & setter
    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public ScoreRepository getScoreRepository() {
        return scoreRepository;
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void incrementNumPlayers(int numPlayers) {
        this.numPlayers += numPlayers;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public GameJoinController getGameJoinController() {
        return gameJoinController;
    }

    public Collection<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Collection<Topic> getMostPopularTopics() {
        return gameRepository.getMostPopularTopics();
    }
    //endregion
}
