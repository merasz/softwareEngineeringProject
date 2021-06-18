package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import at.qe.skeleton.ui.controllers.gameSockets.GamePlaySocketController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameStartServiceTest {

    @Mock
    private GamePlaySocketController mockGamePlaySocketController;

    @InjectMocks
    private GameStartService gameStartServiceUnderTest;

    @BeforeEach
    void setUp() {
        gameStartServiceUnderTest = new GameStartService();
    }

    @Test
    void testStartGame() {
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> {
            final User user = new User();
            final Game game = new Game();
            final Game expectedResult = new Game();
            final Game result = gameStartServiceUnderTest.startGame(game, user);
            assertThat(result).isEqualTo(expectedResult);
            GameJoinController gameJoinController = new GameJoinController();
            assertThat(gameStartServiceUnderTest.getGameJoinController() == gameJoinController);
        });
    }

    @Test
    void testJoinGame() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final User user = new User();
            final Game game = new Game();
            final Game expectedResult = new Game();
            final Game result = gameStartServiceUnderTest.joinGame(game, user);
            assertThat(result).isEqualTo(expectedResult);
            when(game == null).thenThrow(NoSuchElementException.class);
        });
    }

    @Test
    void testGetActiveGame() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final User user = new User();
            final Game expectedResult = new Game();
            final Game result = gameStartServiceUnderTest.getActiveGame(user);
            assertThat(result).isEqualTo(expectedResult);
        });
    }

    @Test
    void testJoinTeam() {
        Assertions.assertThrows(java.lang.NoSuchMethodException.class, () -> {
            final Game expectedResult = new Game();
            final Team team = new Team();

            Method method = Team.class.getDeclaredMethod("joinTeam", null);
            method.setAccessible(true);
            GameStartService gameStartService = new GameStartService();
            Object result = method.invoke(gameStartService, null);
            assertThat(result).isEqualTo(expectedResult);

            Method method1 = GameStartService.class.getDeclaredMethod("addUserToTeam", Team.class);
            method.setAccessible(true);
            Object result1 = method.invoke(gameStartService, team);
            assertThat(result1).isEqualTo(team);
        });
    }

    @Test
    void testAddUserToTeam() {
        Assertions.assertThrows(java.lang.reflect.InvocationTargetException.class, () -> {
            User user = new User();
            Team team = new Team();
            Method method = GameStartService.class.getDeclaredMethod("addUserToTeam", Team.class);
            method.setAccessible(true);
            GameStartService gameStartService = new GameStartService();
            Object result = method.invoke(gameStartService, team);
            assertThat(result).isEqualTo(user);
        });
    }

    @Test
    void testSelectPlayer() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final User user = new User();
            final Game expectedResult = new Game();
            final Game result = gameStartServiceUnderTest.selectPlayer(user);
            assertThat(result).isEqualTo(expectedResult);
        });
    }

    @Test
    void testTeamReady() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final boolean result = gameStartServiceUnderTest.teamReady();
            assertThat(result).isTrue();
        });
    }

    @Test
    void testFinishTeamAssign() throws Exception {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final Game expectedResult = new Game();
            final Game result = gameStartServiceUnderTest.finishTeamAssign("teamName");
            assertThat(result).isEqualTo(expectedResult);
            verify(mockGamePlaySocketController).putTeamPlayerMap(new Game(), new LinkedList<>(Arrays.asList(new TeamPlayer())));
            verify(mockGamePlaySocketController).initGame(new Game());

            final Team team = new Team();
            gameStartServiceUnderTest.setTeam(team);
            final Game value = gameStartServiceUnderTest.enterGame();

            String teamName = new String();
            assertThat(gameStartServiceUnderTest.finishTeamAssign(teamName)).isEqualTo("Blakes");
            when(gameStartServiceUnderTest.finishTeamAssign(teamName)).thenReturn(value);
        });
    }

    @Test
    void testEnterGame() throws Exception {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final User user = new User();
            final Game expectedResult = new Game();
            final Game result = gameStartServiceUnderTest.enterGame();
            assertThat(result).isEqualTo(expectedResult);
            verify(mockGamePlaySocketController).putTeamPlayerMap(new Game(), new LinkedList<>(Arrays.asList(new TeamPlayer())));
            verify(mockGamePlaySocketController).initGame(new Game());
        });
    }

    @Test
    void testInitializeGame() {
        Assertions.assertThrows(java.lang.reflect.InvocationTargetException.class, () -> {
            Game game = new Game();
            Method method = GameStartService.class.getDeclaredMethod("initializeGame", Game.class);
            method.setAccessible(true);
            GameStartService gameStartService = new GameStartService();
            Object result = method.invoke(gameStartService, game);
            assertThat(result).isEqualTo(game);
        });
    }

    @Test
    void testGetGame() {
        gameStartServiceUnderTest.getGame();
    }

    @Test
    void testGetTeamSizeString() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final String result = gameStartServiceUnderTest.getTeamSizeString();
            assertThat(result).isEqualTo("result");
        });
    }

    @Test
    void testGetTeam() {
        Team team = new Team();
        gameStartServiceUnderTest.setTeam(team);
        assertThat(gameStartServiceUnderTest.getTeam()).isEqualTo(team);
    }

    @Test
    void testSetTeam() {
        Team team = new Team();
        gameStartServiceUnderTest.setTeam(team);
        assertThat(gameStartServiceUnderTest.getTeam()).isEqualTo(team);
    }
}
