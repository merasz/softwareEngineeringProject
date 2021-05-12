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


    @CDIAutowired
    private WebSocketManager websocketManager;
    private Map<Integer,Topic> topicMap = new ConcurrentHashMap<>();
    private Map<Integer,Queue<Term>> termQueueMap = new ConcurrentHashMap<>();
    private Map<Integer,Term> currentTermMap = new ConcurrentHashMap<>();
    private Map<Integer,Queue<TeamPlayer>> teamPlayerMap = new ConcurrentHashMap<>();
    private Map<Integer,String> typeMap = new ConcurrentHashMap<>();
    private Map<Integer,Integer> timeMap = new ConcurrentHashMap<>();
    private Map<Integer,Integer> pointsMap = new ConcurrentHashMap<>();
    private Map<Integer,Team> teamMap = new ConcurrentHashMap<>();
    private Map<Integer,User> currentPlayerMap = new ConcurrentHashMap<>();
    private Map<Integer,Integer> gameFinishedMap = new ConcurrentHashMap<>();
    private Map<Integer,Team> gameWinnerMap = new ConcurrentHashMap<>();

    private Map<Integer,Integer> runningMap = new ConcurrentHashMap<>();
    private Map<Integer, Integer> currentRoundRunning = new ConcurrentHashMap<>();

    /**
     * Method to initialize a game and set the init values in the corresponding Maps needed for Gameplay
     * @param game Game to be started
     */
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
        gameFinishedMap.put(game.getGameId(),0);
    }

    /**
     * Action to start the Timer for a game and send update to frontend
     * @param game Game playing
     * @param time time to which the timer is set in seconds
     */
    public void startTimer(Game game,int time){
        timeMap.put(game.getGameId(),time);
        this.websocketManager.getTimeChannel().send("timeUpdate",getAllRecipients(game));
    }

    /**
     * Action to process an TimeFlip update from the API.
     * Either continues to the next round in the game or stops the current one
     * @param activeGame Game the TimeFlip corresponds to
     * @param facetID facet id sent from the TimeFlip
     */
    public void timeFlipUpdate(Game activeGame, int facetID) {
        if( ( runningMap.get(activeGame.getGameId()) == 0 && gameFinishedMap.get(activeGame.getGameId()) != 1 ) || (runningMap.get(activeGame.getGameId()) == 1 && currentRoundRunning.get(activeGame.getGameId()) == 0) && gameFinishedMap.get(activeGame.getGameId()) != 1) {
            nextTerm(activeGame, facetID);
        } else if(currentRoundRunning.get(activeGame.getGameId()) == 1 && gameFinishedMap.get(activeGame.getGameId()) != 1) {
            stopRound(activeGame);
        }
    }

    /**
     * Action to start the next round,get the next player and next term, and update the frontend
     *
     * @param activeGame game the TimeFlip corresponds to
     * @param facetId facet id sent from the TimeFlip
     */
    public void nextTerm(Game activeGame, int facetId) {
        runningMap.put(activeGame.getGameId(),1);
        currentRoundRunning.put(activeGame.getGameId(),1);

        TimeFlipConf timeFlipConf = timeFlipConfRepository.findByFacetId(facetId);

        System.out.println(new Date());

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

    /**
     * Action to get the term for a running game
     * @param game current game
     * @return Term
     */
    public String getNextTerm(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(currentTermMap.get(game.getGameId()) == null ){}
        return currentTermMap.get(game.getGameId()).getTermName();

    }

    /**
     * Action to handle a correct guessed term. Updates scores, notifies frontend and handles end of game
     * @param game current game
     */
    public void termGuessed(Game game) {
        if(currentRoundRunning.get(game.getGameId()) == 1) {
            boolean finished = scoreManagerController.addScoreToTeam(game, currentPlayerMap.get(game.getGameId()),pointsMap.get(game.getGameId()));
            currentRoundRunning.put(game.getGameId(),0);
            setTimeInternal(game,0);
            websocketManager.getScoreChannel().send("scoreUpdate",getAllRecipients(game));

            if(finished) {
                List<String> recipients = getAllRecipients(game);
                gameFinishedMap.put(game.getGameId(),1);
                scoreManagerController.setGameEnd(game);
                websocketManager.getGameChannel().send("gameFinished", recipients);
            }

        }
    }

    /**
     * Action to handle a correct guessed term with a rulebreak. Updates scores, notifies frontend and handles end of game
     * @param game current game
     */
    public void termGuessedWithRulebreak(Game game) {
        if(currentRoundRunning.get(game.getGameId()) == 1) {
            boolean finished = scoreManagerController.addScoreToTeam(game, currentPlayerMap.get(game.getGameId()),pointsMap.get(game.getGameId())-1);
            currentRoundRunning.put(game.getGameId(),0);
            websocketManager.getScoreChannel().send("scoreUpdate",getAllRecipients(game));
            if(finished) {
                List<String> recipients = getAllRecipients(game);
                gameFinishedMap.put(game.getGameId(),1);
                scoreManagerController.setGameEnd(game);
                websocketManager.getGameChannel().send("gameFinished", recipients);
            }
        }
    }

    /**
     * Action to handle a not guessed term.
     * @param game current game
     */
    public void termNotGuessed(Game game) {
        if(currentRoundRunning.get(game.getGameId()) == 1) {
            currentRoundRunning.put(game.getGameId(),0);
            setTimeInternal(game,0);
        }
    }

    /**
     * Action to get the player for this round
     * @param game current game
     * @return
     */
    public String getNextPlayer(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(currentPlayerMap.get(game.getGameId()) == null ){}
        return currentPlayerMap.get(game.getGameId()).getUsername();

    }

    /**
     * Action which sets the current game round as over, when the timer is done
     * @param game current game
     */
    public void onTimerOver(Game game) {
        if(game!=null && currentRoundRunning.get(game.getGameId()) != null && currentPlayerMap.get(game.getGameId()) != null && pointsMap.get(game.getGameId()) != null && currentRoundRunning.get(game.getGameId()) == 1) {
            currentRoundRunning.put(game.getGameId(),0);
        }
    }

    /**
     * Action to notify frontend to stop timer for the given game
     * @param game current game
     */
    public void stopRound(Game game) {
        websocketManager.getTimeChannel().send("stopTimer",getAllRecipients(game));
    }

    /**
     * Action to get separated lists of recipient users from the given game.
     * List 0 : Player in the team of the given user
     * List 1 : Player from all other teams
     * @param game current game
     * @param user current user
     * @return List consisting of the two Lists of different recipients
     */
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

    /**
     * Action to get List of all recipient usernames in the game
     * @param game current game
     * @return List of recipients
     */
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

    /**
     * Action to get the current type of action the user has to do in a game
     * @param game current game
     * @return Type of action
     */
    public String getType(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(typeMap.get(game.getGameId()) == null ){}
        return typeMap.get(game.getGameId());
    }

    /**
     * Returns Map of times for all games
     *
     * @return Map
     */
    public Map<Integer, Integer> getTimeMap() {
        return timeMap;
    }

    /**
     * Setter for the timeMap
     * @param timeMap
     */
    public void setTimeMap(Map<Integer, Integer> timeMap) {
        this.timeMap = timeMap;
    }

    /**
     * Internal setter to set the time for a specific game in the timeMap
     * @param game current game
     * @param time Time
     */
    public void setTimeInternal(Game game, Integer time) {
        timeMap.put(game.getGameId(), time);
    }

    /**
     * Returns points for the current round of the game
     * @param game current Game
     * @return Points for the current round
     */
    public Integer getPoints(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(pointsMap.get(game.getGameId()) == null ){}
        return pointsMap.get(game.getGameId());
    }

    /**
     * Set points for a specific game
     * @param game current Game
     * @param points points to set
     */
    public void setPoints(Game game, Integer points) {
        pointsMap.put(game.getGameId(),points);
    }

    /**
     * returns guessing topic for the current game
     * @param game current game
     * @return guessing topic
     */
    public Topic getTopic(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(topicMap.get(game.getGameId()) == null ){}
        return topicMap.get(game.getGameId());
    }

    /**
     * set topic for a specific game
     * @param game current game
     * @param topic topic to set
     */
    public void setTopic(Game game, Topic topic) {
        topicMap.put(game.getGameId(),topic);
    }

    /**
     * returns topicname for a game
     * @param game Game
     * @return topic name
     */
    public String getTopicName(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(topicMap.get(game.getGameId()) == null ){}
        return topicMap.get(game.getGameId()).getTopicName();
    }

    /**
     * Set current team for a specified game
     * @param game current game
     * @param Team current team
     */
    public void setTeam(Game game, Team Team) {
        teamMap.put(game.getGameId(),Team);
    }

    /**
     * returns current team for a specified game
     * @param game current Game
     * @return Team
     */
    public Team getTeam(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(teamMap.get(game.getGameId()) == null ){}
        return teamMap.get(game.getGameId());
    }

    /**
     * returns currently playing teams name for a game
     * @param game Game
     * @return Name of the current team
     */
    public String getTeamName(Game game) {
        if (runningMap.get(game.getGameId()) == 0) {return null;}
        while(teamMap.get(game.getGameId()) == null ){}
        return teamMap.get(game.getGameId()).getTeamName();
    }

    /**
     * Action to put a Queue of TeamPlayers for a specified game
     * @param game Game
     * @param orderedPlayerList Queue of TeamPlayer
     */
    public void putTeamPlayerMap(Game game, Queue<TeamPlayer> orderedPlayerList) {
        this.teamPlayerMap.put(game.getGameId(), orderedPlayerList);
    }
}
