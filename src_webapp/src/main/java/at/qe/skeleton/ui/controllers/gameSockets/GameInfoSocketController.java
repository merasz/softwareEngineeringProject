package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Scope("application")
@CDIContextRelated
public class GameInfoSocketController {

    @CDIAutowired
    private WebSocketManager websocketManager;
    private Map<Integer,String> messageMap = new ConcurrentHashMap<>();

    public void setGameMessageToGame(Game game, String message) {
        messageMap.put(game.getGameId(),message);
    }

    public Map<Integer, String> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<Integer, String> messageMap) {
        this.messageMap = messageMap;
    }
}
