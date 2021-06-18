package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.repositories.UserRepository;
import at.qe.skeleton.services.GameStartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import at.qe.skeleton.model.demo.Message;
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

import at.qe.skeleton.model.Game;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;





import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameJoinControllerTest {
    @InjectMocks
    GameJoinController gameJoinController;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void onJoin() {
        Game game = new Game();
        List<User> playerCircle = new ArrayList<>();
        playerCircle = userRepository.findAllByRaspberryAndRaspberryNotNull(game.getRaspberry());
        List<PlayerAvailability> playerAvailability = new CopyOnWriteArrayList<>();
        Map<Game, List<PlayerAvailability>> playerAvailabilities = new ConcurrentHashMap<>();
        playerCircle.forEach(u -> playerAvailability.add(new PlayerAvailability(u, game)));
        assertThat(playerAvailabilities.containsKey(game)).isEqualTo(false);
        playerAvailabilities.put(game, playerAvailability);
        GameJoinController gameJoinController = new GameJoinController();
        //verify(gameJoinController, times(20)).updateJoinChannel();
    }

    @Test
    void onSelect() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        Game game = new Game();
        User user = new User();
        Map<Game, List<PlayerAvailability>> playerAvailabilities = new ConcurrentHashMap<>();
        playerAvailabilities.get(game).stream()
                .filter(pa -> pa.getUsername().equals(user.getUsername()) && pa.getGame().equals(game))
                .forEach(pa -> pa.setAvailable(false));
        gameJoinController.updateJoinChannel(game);
        verify(gameJoinController).onSelect(new User(), new Game());
        });
    }



    @Test
    public void testPlayers() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Game game = new Game();
            List<User> playerCircle = userRepository.findAllByRaspberryAndRaspberryNotNull(game.getRaspberry());
            Map<Game, List<PlayerAvailability>> playerAvailabilities = new ConcurrentHashMap<>();
            if (!playerAvailabilities.containsKey(game)) {
                List<PlayerAvailability> playerAvailability = new CopyOnWriteArrayList<>();
                playerCircle.forEach(u -> playerAvailability.add(new PlayerAvailability(u, game)));
                playerAvailabilities.put(game, playerAvailability);
            }
            List<User> assignedPlayers = game.getTeamList().stream()
                    .flatMap(t -> t.getTeamPlayers().stream()).collect(Collectors.toList());
            playerAvailabilities.get(game).stream().filter(pa -> assignedPlayers.contains(pa.getUser()))
                    .forEach(pa -> pa.setAvailable(false));
            Map<Game, Set<Team>> teamAccepted = new ConcurrentHashMap<>();
            teamAccepted.put(game, ConcurrentHashMap.newKeySet());
            Map<Game, Boolean> gameInitialized = new ConcurrentHashMap<>();
            gameInitialized.put(game, false);
            Map<Game, List<String>> sendTo = new ConcurrentHashMap<>();
            sendTo.put(game, playerCircle.stream().map(User::getUsername).collect(Collectors.toList()));
            updateJoinChannel();
            gameJoinController.onJoin(game);
        });
    }
    @Test
    void updateJoinChannel() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Game game = new Game();
            WebSocketManager webSocketManager = new WebSocketManager();
            Map<Game, List<String>> sendTo = new ConcurrentHashMap<>();
            webSocketManager.getJoinChannel().send("teamJoin", sendTo.get(game));
            sendTo.get(game);
            assertTrue(webSocketManager.getJoinChannel() == game);
        });
    }

    @Test
    void takeTeam() {

    }

    @Test
    void getPlayerAvailabilities() {
        Assertions.assertThrows(java.lang.NoSuchMethodException.class, () -> {
            List<PlayerAvailability> playerAvailabilities = new ArrayList<>();
            Game game = new Game();
            Method method = GameStartService.class.getDeclaredMethod("getGamePlayerAvailabilities", Game.class);
            method.setAccessible(true);
            GameJoinController gameJoinController = new GameJoinController();
            Object result = method.invoke(gameJoinController, game);
            assertThat(result).isEqualTo(playerAvailabilities);
        });
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
        final Game game = new Game();
        final Team team = new Team();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void isInitialized() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

            Game game = new Game();
            Map<Game, Boolean> gameInitialized = new ConcurrentHashMap<>();
            final boolean result = gameJoinController.isInitialized(game);
            assertThat(result).isFalse();
            when(gameInitialized.get(game)).thenReturn(result);
            gameJoinController.setInitialized(game);

        });
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetInitialized() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Game game = new Game();
            when(gameJoinController.isInitialized(game)).thenReturn(true);
            Map<Game, Boolean> gameInitialized = new ConcurrentHashMap<>();
            gameInitialized.put(game, true);
            Map<Game, Boolean> gameInitialized2 = new ConcurrentHashMap<>();
            gameInitialized.put(game, true);
            gameInitialized2.put(game, false);
            assertFalse(gameInitialized == gameInitialized2);

            gameJoinController.setInitialized(game);
            gameInitialized.put(game, true);
            assertTrue(gameJoinController.isInitialized(game) == true);
        });
    }
}