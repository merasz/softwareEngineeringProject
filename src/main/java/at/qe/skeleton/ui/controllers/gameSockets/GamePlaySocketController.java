package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.LogEntry;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.model.demo.TeamScoreInfo;

import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.TimeFlipConfRepository;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


@Controller
@Scope("application")
@CDIContextRelated
public class GamePlaySocketController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TimeFlipConfRepository timeFlipConfRepository;


    @CDIAutowired
    private WebSocketManager websocketManager;
    private List<LogEntry> actionLogs = new CopyOnWriteArrayList<>();
    private Game game;
    private Map<Game,Topic> topicMap = new ConcurrentHashMap<>();
    private Map<Game,Queue<Term>> termMap = new ConcurrentHashMap<>();
    private Map<Game,String> typeMap = new ConcurrentHashMap<>();
    private Map<Game,Integer> timeMap = new ConcurrentHashMap<>();
    private Map<Game,Integer> pointsMap = new ConcurrentHashMap<>();
    private Map<Game,Team> teamMap = new ConcurrentHashMap<>();
    private Map<Game,Queue<User>> playerMap = new ConcurrentHashMap<>();
    private Map<Game,Queue<TeamPlayer>> teamPlayerMap = new ConcurrentHashMap<>();
    private Map<Game,Integer> runningMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void init(){
        game = gameRepository.findAll().get(1);
        Topic topic = game.getTopic();
        List<Term> terms = topic.getTerms();
        Collections.shuffle(terms);
        termMap.put(game,new LinkedList<>(terms));

        List<Team> teams = game.getTeamList();
        List<User> player = new ArrayList<User>();
        for(Team team : teams) {
            player.addAll(team.getTeamPlayers());
        }
        playerMap.put(game,new LinkedList<>(player));
        runningMap.put(game,0);
        typeMap.put(game,"");
        timeMap.put(game,0);

    }

    public void startTimer(int time){
        timeMap.put(game,time);
        this.websocketManager.getTimeChannel().send("timeUpdate");
    }

    public void nextTerm(Game activeGame, int facetId) {
        runningMap.put(game,1);

        TimeFlipConf timeFlipConf = timeFlipConfRepository.findByFacetId(facetId);

        int time = timeFlipConf.getTime();
        typeMap.put(game,timeFlipConf.getRequestType().toString());
        pointsMap.put(game,timeFlipConf.getFacetPoint());
        startTimer(time);
        this.websocketManager.getTermChannel().send("termUpdate");
    }

    public String getNextTerm() {
        if (runningMap.get(game) == 0) {return null;}
        while(termMap.get(game) == null ){}
        if(termMap.get(game).size() > 0) {
            return termMap.get(game).poll().getTermName();
        } else {
            return null;
        }

    }

    public String getNextPlayer() {
        if (runningMap.get(game) == 0) {return null;}
        while(playerMap.get(game) == null ){}
        if(playerMap.get(game).size() > 0) {
            return playerMap.get(game).poll().getUsername();
        } else {
            return null;
        }

    }

    public String getType() {
        if (runningMap.get(game) == 0) {return null;}
        while(typeMap.get(game) == null ){}
        return typeMap.get(game);
    }


    public WebSocketManager getWebsocketManager() {
        return websocketManager;
    }

    public void setWebsocketManager(WebSocketManager websocketManager) {
        this.websocketManager = websocketManager;
    }

    public Integer getTime() {
        if (runningMap.get(game) == 0) {return null;}
        while(timeMap.get(game) == null ){}
        return timeMap.get(game);
    }

    public void setTime(Integer time) {
        timeMap.put(this.game, time);
        System.out.println(time);
    }

    public Integer getPoints() {
        if (runningMap.get(game) == 0) {return null;}
        while(pointsMap.get(game) == null ){}
        return pointsMap.get(game);
    }

    public void setPoints(Integer points) {
        pointsMap.put(this.game,points);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Topic getTopic() {
        if (runningMap.get(game) == 0) {return null;}
        while(topicMap.get(game) == null ){}
        return topicMap.get(game);
    }

    public void setTopic(Topic topic) {
        topicMap.put(game,topic);
    }

    public String getTopicName() {
        if (runningMap.get(game) == 0) {return null;}
        while(topicMap.get(game) == null ){}
        return topicMap.get(game).getTopicName();
    }

    public void setTeam(Team Team) {
        teamMap.put(game,Team);
    }

    public Team getTeam() {
        if (runningMap.get(game) == 0) {return null;}
        while(teamMap.get(game) == null ){}
        return teamMap.get(game);
    }

    public String getTeamName() {
        if (runningMap.get(game) == 0) {return null;}
        while(teamMap.get(game) == null ){}
        return teamMap.get(game).getTeamName();
    }

    public List<LogEntry> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<LogEntry> actionLogs) {
        this.actionLogs = actionLogs;
    }

    public Map<Game, Queue<TeamPlayer>> getTeamPlayerMap() {
        return teamPlayerMap;
    }

    public void putTeamPlayerMap(Game game, Queue<TeamPlayer> orderedPlayerList) {
        this.teamPlayerMap.put(game, orderedPlayerList);
    }
}
