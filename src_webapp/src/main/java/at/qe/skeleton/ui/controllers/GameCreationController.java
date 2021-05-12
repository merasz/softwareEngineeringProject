package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

// handles the GUI for game creation (availableGames.xhtml)
@Component
@Scope("view")
public class GameCreationController extends Controller implements Serializable {
    @Autowired
    private GameService gameService;

    /*
    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;
    */

    @Autowired
    private TermsService termsService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    //minimum number of points for winning the game allowed to set at game creation
    private int MIN_WIN_SCORE = 12;

    private Game game;
    private Topic currentTopic;

    private User user;
    //private List<User> userList;
    //private List<User> tmp;
    private int numberTeams;

    @PostConstruct
    public void init() {
        doCreateNewGame();
        //setUserList();
    }

    public void doCreateNewGame() {
        user = sessionInfoBean.getCurrentUser();
        game = new Game();
        game.setRaspberry(user.getRaspberry());
    }

    public void doSaveGame() {
        if (game.getGameName() == null || currentTopic == null) {
            displayError("Missing input", "Please enter required game settings.");
        } else if (game.getScoreToWin() >= MIN_WIN_SCORE) {
            try {
                game.setTopic(termsService.setTopic(currentTopic));
                game = gameService.saveGame(game);
                IntStream.range(0,numberTeams).forEach(i -> game.getTeamList().add(teamService.saveTeam(new Team(game))));
                game.setCountPlayers(numberTeams * game.getTeamSize());
                game = gameService.saveGame(game);
                PrimeFaces.current().executeScript("PF('gameCreationDialog').hide()");
            } catch (IllegalArgumentException e) {
                displayError("Too few terms", e.getMessage());
            }
        } else {
            displayError("Score to win too small", "Score should be at least " + MIN_WIN_SCORE + " points.");
        }
    }

    /*
    public void setUserList() {
        userList = new ArrayList<>(userService.getAllUsers());
    }
    */

    //region getter & setter
    public int getNumberTeams() {
        return numberTeams;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setNumberTeams(int numberTeams) {
        this.numberTeams = numberTeams;
    }

    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(Topic topic) {
        this.currentTopic = topic;
    }

    /*
    public List<User> getUserList() {
        return userList;
    }

    public List<User> getTmp() {
        return tmp;
    }

    public void setTmp(List<User> tmp) {
        this.tmp = tmp;
    }
    */
    //endregion
}
