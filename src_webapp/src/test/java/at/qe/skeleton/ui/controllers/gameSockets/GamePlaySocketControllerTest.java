package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.TimeFlipConfRepository;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Assertions;
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

import at.qe.skeleton.ui.websockets.WebSocketManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class GamePlaySocketControllerTest {


    @Mock
    GameRepository gameRepository;

    @Mock
    TimeFlipConfRepository timeFlipConfRepository;

    @Mock
    ScoreManagerController scoreManagerController;

    @Mock
    GameInfoSocketController gameInfoSocketController;

    @Mock
    GamePlaySocketController  gamePlaySocketController;

    @Mock
    TeamRepository teamRepository;

    @Test
    void initGame() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Map<Integer, Queue<Term>> termQueueMap = new ConcurrentHashMap<>();
            Map<Integer, User> nextPlayerMap = new ConcurrentHashMap<>();
            Map<Integer, Queue<TeamPlayer>> teamPlayerMap = new ConcurrentHashMap<>();
            Map<Integer, Integer> runningMap = new ConcurrentHashMap<>();
            Map<Integer, String> typeMap = new ConcurrentHashMap<>();
            Map<Integer, Integer> timeMap = new ConcurrentHashMap<>();
            Map<Integer, Integer> gameFinishedMap = new ConcurrentHashMap<>();
            Game game = new Game();
            Topic topic = game.getTopic();
            List<Term> terms = topic.getTerms();
            Collections.shuffle(terms);
            termQueueMap.put(game.getGameId(), new LinkedList<>(terms));

            System.out.println("init game: " + game);
            scoreManagerController.setupScores(game);

            nextPlayerMap.put(game.getGameId(), teamPlayerMap.get(game.getGameId()).peek().getPlayer());

            runningMap.put(game.getGameId(), 0);
            typeMap.put(game.getGameId(), "");
            timeMap.put(game.getGameId(), 0);
            gameFinishedMap.put(game.getGameId(), 0);

            gameInfoSocketController.setGameMessageToGame(game, "Roll the dice to start the game!");
            assertThat(gamePlaySocketController).isEqualTo(gameInfoSocketController).toString();
        });
        }


    /*@Test
    void teststartTimer() {
        Game game = new Game();
        int time = 35;
        Map<Integer,Integer> timeMap = new ConcurrentHashMap<>();
        timeMap.put(game.getGameId(),time);
        timeMap.get(35);
        assertTrue(gamePlaySocketController.startTimer(game, 35) == timeMap);

    }*/

    @Test
    void timeFlipUpdate() {
    }

    @Test
    void nextTerm() {
    }

    @Test
    void getNextTerm() {
    }

    @Test
    void termGuessed() {
    }

    @Test
    void termGuessedWithRulebreak() {
    }

    @Test
    void termNotGuessed() {
    }

    @Test
    void getNextPlayer() {
    }

    @Test
    void onTimerOver() {
    }

    @Test
    void stopRound() {
    }

    @Test
    void getSeperatedRecipients() {
    }

    @Test
    void getAllRecipients() {
    }

    @Test
    void getType() {
    }

    @Test
    void getTimeMap() {
        GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
            Map<Integer, Integer> msg  = new HashMap<>();
            gamePlaySocketController.setTimeMap(msg);
            assertTrue(gamePlaySocketController.getTimeMap() == msg);
        }

    @Test
    void setTimeMap() {
        GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
        Map<Integer, Integer> msg  = new HashMap<>();
        gamePlaySocketController.setTimeMap(msg);
        assertTrue(gamePlaySocketController.getTimeMap() == msg);
    }


    @Test
    void setTimeInternal() {
    }

    @Test
    void getPoints() {
    }

    @Test
    void setPoints() {
    }

    @Test
    void putTeamPlayerMap() {
    }

    @Test
    void getNextRoundPlayer() {
        User user = new User();
        Game game = new Game();
        Map<Integer, User> players = new HashMap<>();
        gamePlaySocketController.getNextRoundPlayer(game);

    }
}