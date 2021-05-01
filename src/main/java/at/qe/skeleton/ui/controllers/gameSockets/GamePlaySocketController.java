package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.model.demo.LogEntry;
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
public class GamePlaySocketController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TimeFlipConfRepository timeFlipConfRepository;


    @CDIAutowired
    private WebSocketManager websocketManager;
    private Integer time;
    private List<LogEntry> actionLogs = new CopyOnWriteArrayList<>();
    private Game game;
    private Topic topic;
    private List<Term> terms;
    private Queue<Term> nextTerms;
    private Map<Game,Queue<Term>> termMap = new ConcurrentHashMap<>();
    private Map<Game,String> typeMap = new ConcurrentHashMap<>();
    private String type;

    private int running = 0;


    @PostConstruct
    public void init(){
        game = gameRepository.findAll().get(1);
        topic = game.getTopic();
        terms = topic.getTerms();
        Collections.shuffle(terms);
        nextTerms = new LinkedList<>(terms);
        if(!termMap.containsKey(game)) {
            termMap.put(game,nextTerms);
        }
        time = 0;
        if(!typeMap.containsKey(game)) {
            typeMap.put(game,"");
        }
    }

    public void startTimer(int time){
        this.time = time;
        this.websocketManager.getTimeChannel().send("timeUpdate");
    }

    public void nextTerm(){
        running = 1;
        Random rn = new Random();
        int answer = rn.nextInt(12) + 1;
        int time = timeFlipConfRepository.findByFacetId(answer).getTime();

        typeMap.put(game,timeFlipConfRepository.findByFacetId(answer).getRequestType().toString());
        startTimer(time);
        this.websocketManager.getTermChannel().send("termUpdate");
    }

    public String getNextTerm() {
        if (running == 0) {return null;}
        while(termMap.get(game) == null ){}
        if(termMap.get(game).size() > 0) {
            return termMap.get(game).poll().getTermName();
        } else {
            return null;
        }

    }

    public String getType() {
        if (running == 0) {return null;}
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
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTopicName() {
        return topic.getTopicName();
    }

    public List<LogEntry> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<LogEntry> actionLogs) {
        this.actionLogs = actionLogs;
    }
}
