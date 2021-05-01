package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.demo.LogEntry;
import at.qe.skeleton.model.demo.TeamScoreInfo;

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
public class GamePlaySocketController {


    @CDIAutowired
    private WebSocketManager websocketManager;
    private Integer time;
    private List<LogEntry> actionLogs = new CopyOnWriteArrayList<>();


    @PostConstruct
    public void init(){
        time = 180;
    }

    public void startTimer(int time){
        this.time = time;
        this.websocketManager.getTimeChannel().send("timeUpdate");
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

    public List<LogEntry> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<LogEntry> actionLogs) {
        this.actionLogs = actionLogs;
    }
}
