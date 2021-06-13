package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.TimeFlipConfRepository;
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
    }

    @Test
    void startTimer() {
    }

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