package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.services.ScoreService;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class TeamListController {

    private static final long serialVersionUID = 1L;

    private Game game;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;

    private Map<Team, Integer> teamsWithItsTotalScoreAGame;

//    @PostConstruct
//    public void init(){
//        teamsWithItsTotalScoreAGame = scoreService.getScoresForTeamByGame();
//    }

    public Collection<Team> getTeams() {
        return teamService.getAllTeams();
    }

    public Collection<Score> getTeamScores() {
        return scoreService.getScoresForTeams(game);
    }

    public Collection<Team> getTeamsByGame() {
        if(game == null) {
            return new ArrayList<Team>();
        }
        return teamService.getTeamsByGame(this.game).stream().filter(t -> t.getTeamName() != null).collect(Collectors.toList());
    }

    public void doSetGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Map<Team, Integer> getTeamsWithItsTotalScoreAGame() {
        return teamsWithItsTotalScoreAGame;
    }

    public List<Map.Entry<Team, Integer>> getTeamScoresForManager() {
        Set<Map.Entry<Team, Integer>> productSet = teamsWithItsTotalScoreAGame.entrySet();
        return new ArrayList<Map.Entry<Team, Integer>>(productSet);
    }
}
