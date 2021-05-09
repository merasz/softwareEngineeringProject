package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.*;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;

import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This controller holds and manages all user's status-information (i.e. their
 * online-status)
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Controller
@Scope("application")
@CDIContextRelated
public class ScoreManagerController {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    TeamRepository teamRepository;


    @CDIAutowired
    private WebSocketManager websocketManager;
    private Map<Integer,Map<String, TeamScoreInfo>> scores = new ConcurrentHashMap<>();


    public void setupScores(Game game) {
        Map<String, TeamScoreInfo> tmp = new ConcurrentHashMap<>();
        System.out.println(game);
        this.scoreRepository.findGameScoresByGame(game)
              .forEach(score -> tmp.put(score.getTeam().getTeamName(), new TeamScoreInfo(score.getTeam(),score.getTotalRoundScore())));
        scores.put(game.getGameId(), tmp);
    }

    public Collection<TeamScoreInfo> getScores(Game game) {
        if(this.scores.get(game.getGameId()) == null) {return null;}
        return Collections.unmodifiableCollection(this.scores.get(game.getGameId()).values());
    }

    public void addScoreToTeam(Game game, User user, int roundScore) {
        Team team = teamRepository.findByTeamPlayersAndGame(user,game);
        Score tmp = new Score();
        tmp.setGame(game);
        tmp.setTotalRoundScore(roundScore);
        tmp.setTeam(team);
        tmp.setUser(user);
        scoreRepository.save(tmp);
        setupScores(game);
    }
}
