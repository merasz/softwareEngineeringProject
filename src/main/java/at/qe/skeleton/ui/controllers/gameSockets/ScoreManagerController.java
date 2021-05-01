package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
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
    private Map<String, TeamScoreInfo> scores = new ConcurrentHashMap<>();
    private List<LogEntry> actionLogs = new CopyOnWriteArrayList<>();

    private Game game;

    @PostConstruct
    public void init(){
        game = gameRepository.findAll().get(1);

        System.out.println(game.getGameId());
        setupScores();
    }

    public void setupScores() {
        this.scoreRepository.findGameScoresByGame(this.game)
              .forEach(score -> this.scores.put(score.getTeam().getTeamName(), new TeamScoreInfo(score.getTeam(),score.getTotalRoundScore())));
    }

    public Collection<TeamScoreInfo> getScores() {
        return Collections.unmodifiableCollection(this.scores.values());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addScore1(int roundScore) {
        Team team = teamRepository.findByTeamId(Long.valueOf(3));
        Score tmp = new Score();
        tmp.setGame(this.game);
        tmp.setTotalRoundScore(roundScore);
        tmp.setTeam(team);
        scoreRepository.save(tmp);
        setupScores();
        this.websocketManager.getScoreChannel().send("scoreUpdate");
    }

    public void addScore2(int roundScore) {
        Team team = teamRepository.findByTeamId(Long.valueOf(4));
        Score tmp = new Score();
        tmp.setGame(this.game);
        tmp.setTotalRoundScore(roundScore);
        tmp.setTeam(team);
        scoreRepository.save(tmp);
        setupScores();
        this.websocketManager.getScoreChannel().send("scoreUpdate");
    }
}
