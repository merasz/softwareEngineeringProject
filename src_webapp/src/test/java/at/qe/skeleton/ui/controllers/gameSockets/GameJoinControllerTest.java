package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
class GameJoinControllerTest {
    @Mock
    GameJoinController gameJoinController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void onJoin() {
    }

    @Test
    void onSelect() {
    }

    @Test
    void updateJoinChannel() {
    }

    @Test
    void takeTeam() {
    }

    @Test
    void getPlayerAvailability() {
    }

    @Test
    void updateReadyToStart() {
    }

    @Test
    void allReadyToStart() {
    }

    @Test
    void updateTeamsReady() {
    }

    @Test
    void resetAssignments() {
    }

    @Test
    void teamAvailable() {
    }

    @Test
    void isInitialized() {
    }

    @Test
    void testSetInitialized() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Game game = new Game();
            Map<Game, Boolean> gameInitialized = new ConcurrentHashMap<>();
            gameInitialized.put(game, true);
            Map<Game, Boolean> gameInitialized2 = new ConcurrentHashMap<>();
            gameInitialized.put(game, true);
            gameInitialized2.put(game, false);
            assertFalse(gameInitialized == gameInitialized2);
            gameJoinController.setInitialized(game);
        });



    }
}