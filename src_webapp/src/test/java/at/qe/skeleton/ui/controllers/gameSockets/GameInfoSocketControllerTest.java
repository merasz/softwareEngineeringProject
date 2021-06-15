package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import at.qe.skeleton.model.Game;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameInfoSocketControllerTest {



    @Test
    void setGameMessageToGame(){
        Game game = new Game();
        String message = "hola";
        GameInfoSocketController gameInfoSocketController = new GameInfoSocketController();
        Map<Integer, String> msg  = new HashMap<>();
        gameInfoSocketController.setGameMessageToGame(game, "hola");
        msg.put(game.getGameId(), message);
        assertTrue(gameInfoSocketController.getMessageMap() == msg);
        gameInfoSocketController.getMessageMap();
        gameInfoSocketController.setMessageMap(msg);
    }




    @Test
    void testGetMessageMap() {
        Game game = new Game();
        GameInfoSocketController gameInfoSocketController = new GameInfoSocketController();
        Map<Integer, String> msg  = new HashMap<>();
        gameInfoSocketController.setMessageMap(msg);
        assertTrue(gameInfoSocketController.getMessageMap() == msg);
        }

    @Test
    void testSetGameMessage() {
        Game game = new Game();
        GameInfoSocketController gameInfoSocketController = new GameInfoSocketController();
        Map<Integer, String> msg  = new HashMap<>();
        gameInfoSocketController.setMessageMap(msg);
        assertTrue(gameInfoSocketController.getMessageMap() == msg);
    }

}