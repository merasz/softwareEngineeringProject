package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.LogEntry;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.model.demo.TeamScoreInfo;

import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.TimeFlipConfRepository;
import at.qe.skeleton.services.GameStartService;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@Scope("application")
@CDIContextRelated
public class GamePlaySocketController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TimeFlipConfRepository timeFlipConfRepository;

    @Autowired
    ScoreManagerController scoreManagerController;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @CDIAutowired
    private WebSocketManager websocketManager;
    private List<LogEntry> actionLogs = new CopyOnWriteArrayList<>();
    private Map<Integer,Topic> topicMap = new ConcurrentHashMap<>();
    private Map<Integer,Queue<Term>> termQueueMap = new ConcurrentHashMap<>();
    private Map<Integer,Term> currentTermMap = new ConcurrentHashMap<>();
    private Map<Integer,Queue<TeamPlayer>> teamPlayerMap = new ConcurrentHashMap<>();
    private Map<Integer,String> typeMap = new ConcurrentHashMap<>();
    private Map<Integer,Integer> timeMap = new ConcurrentHashMap<>();
    private Map<Integer,Integer> pointsMap = new ConcurrentHashMap<>();
    private Map<Integer,Team> teamMap = new ConcurrentHashMap<>();
    private Map<Integer,User> currentPlayerMap = new ConcurrentHashMap<>();
    //private Map<Integer,Queue<User>> playerQueueMap = new ConcurrentHashMap<>();

    private Map<Integer,Integer> runningMap = new ConcurrentHashMap<>();
    private Map<Integer, Integer> currentRoundRunning = new ConcurrentHashMap<>();

    public void initGame(Game game) {
        //teamPlayerMap.put(game.getGameId(),createPlayerOrdering(game));

        Topic topic = game.getTopic();
        List<Term> terms = topic.getTerms();
        Collections.shuffle(terms);
        termQueueMap.put(game.getGameId(),new LinkedList<>(terms));
        System.out.println("init game: " + game);
        scoreManagerController.setupScores(game);

        runningMap.put(game.getGameId(),0);
        typeMap.put(game.getGameId(),"");
        timeMap.put(game.getGameId(),0);
    }

    public void startTimer(Game game,int time){
        timeMap.put(game.getGameId(),time);
        this.websocketManager.getTimeChannel().send("timeUpdate",getAllRecipients(game));
    }

    public void timeFlipUpdate(Game activeGame, int facetID) {
        if(runningMap.get(activeGame.getGameId()) == 0 || (runningMap.get(activeGame.getGameId()) == 1 && currentRoundRunning.get(activeGame.getGameId()) == 0)) {
            nextTerm(activeGame, facetID);
        } else if(currentRoundRunning.get(activeGame.getGameId()) == 1) {
            stopRound(activeGame);
        }
    }

    public void nextTerm(Game activeGame, int facetId) {
        runningMap.put(activeGame.getGameId(),1);
        currentRoundRunning.put(activeGame.getGameId(),1);

        TimeFlipConf timeFlipConf = timeFlipConfRepository.findByFacetId(facetId);


        typeMap.put(activeGame.getGameId(),timeFlipConf.getRequestType().toString());
        pointsMap.put(activeGame.getGameId(),timeFlipConf.getFacetPoint());

        //User user = playerQueueMap.get(activeGame.getGameId()).poll();
        TeamPlayer teamPlayer = teamPlayerMap.get(activeGame.getGameId()).poll();
        teamPlayerMap.get(activeGame.getGameId()).add(teamPlayer);
        User user = teamPlayer.getPlayer();
        currentPlayerMap.put(activeGame.getGameId(),user);

        Term term = termQueueMap.get(activeGame.getGameId()).poll();
        currentTermMap.put(activeGame.getGameId(),term);

        int time = timeFlipConf.getTime();
        startTimer(activeGame, time);

        List<List<String>> recipients = getSeperatedRecipients(activeGame, user);
        List<String> currentUser = recipients.get(0);
        List<String> otherUser = recipients.get(1);

        this.websocketManager.getTermChannel().send("termUpdateCurrent",currentUser);
        this.websocketManager.getTermChannel().send("termUpdate",otherUser);

    }

    public String getNextTerm(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(currentTermMap.get(game.getGameId()) == null ){}
        return currentTermMap.get(game.getGameId()).getTermName();

    }

    public void termGuessed(Game game) {
        if(currentRoundRunning.get(game.getGameId()) == 1) {
            scoreManagerController.addScoreToTeam(game, currentPlayerMap.get(game.getGameId()),pointsMap.get(game.getGameId()));
            currentRoundRunning.put(game.getGameId(),0);
            setTimeInternal(game,0);
            websocketManager.getScoreChannel().send("scoreUpdate",getAllRecipients(game));
        }
    }

    public void termGuessedWithRulebreak(Game game) {
        if(currentRoundRunning.get(game.getGameId()) == 1) {
            scoreManagerController.addScoreToTeam(game, currentPlayerMap.get(game.getGameId()),pointsMap.get(game.getGameId())-1);
            currentRoundRunning.put(game.getGameId(),0);

            websocketManager.getScoreChannel().send("scoreUpdate",getAllRecipients(game));
        }
    }

    public void termNotGuessed(Game game) {
        if(currentRoundRunning.get(game.getGameId()) == 1) {
            currentRoundRunning.put(game.getGameId(),0);
            setTimeInternal(game,0);
        }
    }

    public String getNextPlayer(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(currentPlayerMap.get(game.getGameId()) == null ){}
        return currentPlayerMap.get(game.getGameId()).getUsername();

    }

    public void onTimerOver(Game game) {
        if(game!=null && currentRoundRunning.get(game.getGameId()) != null && currentPlayerMap.get(game.getGameId()) != null && pointsMap.get(game.getGameId()) != null && currentRoundRunning.get(game.getGameId()) == 1) {
            currentRoundRunning.put(game.getGameId(),0);
        }
    }

    public void stopRound(Game game) {
        websocketManager.getTimeChannel().send("stopTimer",getAllRecipients(game));
    }

    public List<List<String>> getSeperatedRecipients(Game game, User user) {
        List<Team> allTeams = teamRepository.findByGame(game.getGameId());
        Team userTeam = teamRepository.findByTeamPlayersAndGame(user,game);
        List<String> otherTeams = new ArrayList<>();
        List<String> currentTeam = new ArrayList<>();
        for(Team t : allTeams) {
            if(t.getTeamId().equals(userTeam.getTeamId())) {
                Set <User> s = new HashSet<>(t.getTeamPlayers());
                s.forEach(r -> currentTeam.add(r.getUsername()));
            } else {
                Set <User> s = new HashSet<>(t.getTeamPlayers());
                s.forEach(r -> otherTeams.add(r.getUsername()));
            }
        }

        List<List<String>> concatRecipients = new ArrayList<>();
        concatRecipients.add(currentTeam);
        concatRecipients.add(otherTeams);

        return concatRecipients;
    }

    public List<String> getAllRecipients(Game game) {
        List<Team> allTeams = teamRepository.findByGame(game.getGameId());

        List<String> recipients = new ArrayList<>();
        for(Team t : allTeams) {
            Set<User> removeDupl = new HashSet<>(t.getTeamPlayers());
            removeDupl.forEach(r -> recipients.add(r.getUsername()));
        }
        System.out.println(recipients);
        return recipients;
    }

    public String getType(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(typeMap.get(game.getGameId()) == null ){}
        return typeMap.get(game.getGameId());
    }
    /*
    public Integer getTime(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(timeMap.get(game.getGameId()) == null ){}
        return timeMap.get(game.getGameId());
    }

    public void setGetTime(Game game) {}

     */

    public Map<Integer, Integer> getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(Map<Integer, Integer> timeMap) {
        this.timeMap = timeMap;
    }

    public void setTimeInternal(Game game, Integer time) {
        timeMap.put(game.getGameId(), time);
    }



    public Integer getPoints(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(pointsMap.get(game.getGameId()) == null ){}
        return pointsMap.get(game.getGameId());
    }

    public void setPoints(Game game, Integer points) {
        pointsMap.put(game.getGameId(),points);
    }


    public Topic getTopic(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(topicMap.get(game.getGameId()) == null ){}
        return topicMap.get(game.getGameId());
    }

    public void setTopic(Game game, Topic topic) {
        topicMap.put(game.getGameId(),topic);
    }

    public String getTopicName(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(topicMap.get(game.getGameId()) == null ){}
        return topicMap.get(game.getGameId()).getTopicName();
    }

    public void setTeam(Game game, Team Team) {
        teamMap.put(game.getGameId(),Team);
    }

    public Team getTeam(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(teamMap.get(game.getGameId()) == null ){}
        return teamMap.get(game.getGameId());
    }

    public String getTeamName(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(teamMap.get(game.getGameId()) == null ){}
        return teamMap.get(game.getGameId()).getTeamName();
    }

    public void setActionLogs(List<LogEntry> actionLogs) {
        this.actionLogs = actionLogs;
    }

    public Map<Integer, Queue<TeamPlayer>> getTeamPlayerMap() {
        return teamPlayerMap;
    }

    public void putTeamPlayerMap(Game game, Queue<TeamPlayer> orderedPlayerList) {
        this.teamPlayerMap.put(game.getGameId(), orderedPlayerList);
    }
}
