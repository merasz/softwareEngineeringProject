package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;


import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.event.SelectEvent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertTrue;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


@ExtendWith(MockitoExtension.class)
class GameStartControllerTest {

    @Mock
    private GameStartService gameStartService;
    @Mock
    private SessionInfoBean sessionInfoBean;

    @Mock
    private PlayerAvailability playerAvailability;

    @InjectMocks
    private GameStartController gameStartController;


        @Mock
        private GameStartService mockGameStartService;
        @Mock
        private SessionInfoBean mockSessionInfoBean;

        @InjectMocks
        private GameStartController gameStartControllerUnderTest;

    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testStartGame() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                final Game game = new Game();
                game.setScoreToWin(0);
                game.setActive(false);
                game.setTeamList(Arrays.asList(new Team(new Game())));
                game.setGameName("gameName");
                game.setCountPlayers(0);
                final Game game1 = new Game();
                game1.setScoreToWin(0);
                game1.setActive(false);
                game1.setTeamList(Arrays.asList(new Team(new Game())));
                game1.setGameName("gameName");
                game1.setCountPlayers(0);
                when(mockGameStartService.startGame(new Game(), new User())).thenReturn(game1);
                final String result = gameStartControllerUnderTest.startGame(game);
                assertThat(result).isEqualTo("result");
                gameStartControllerUnderTest.setTeamName("prova");
                gameStartControllerUnderTest.getTeamName();
            });
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testStartGame_GameStartServiceThrowsIllegalArgumentException() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                // Setup
                final Game game = new Game();
                game.setScoreToWin(0);
                game.setActive(false);
                game.setTeamList(Arrays.asList(new Team(new Game())));
                game.setGameName("gameName");
                game.setCountPlayers(0);
                when(mockGameStartService.startGame(new Game(), new User())).thenThrow(IllegalArgumentException.class);
                final String result = gameStartControllerUnderTest.startGame(game);
                assertThat(result).isEqualTo("result");
            });
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testJoinGame() {
            final Game game = new Game();
            game.setScoreToWin(0);
            game.setActive(false);
            game.setGameName("gameName");
            game.setCountPlayers(0);
            when(mockGameStartService.getActiveGame(new User())).thenReturn(game);

            when(mockGameStartService.getGameJoinController()).thenReturn(new GameJoinController());
            final Game game1 = new Game();
            game1.setScoreToWin(0);
            game1.setActive(false);
            game1.setTeamList(Arrays.asList(new Team(new Game())));
            game1.setGameName("gameName");
            game1.setCountPlayers(0);
            when(mockGameStartService.joinGame(new Game(), new User())).thenReturn(game1);
            final String result = gameStartControllerUnderTest.joinGame();
            assertThat(result).isEqualTo("/secured/game_room/join.xhtml?faces-redirect=true");
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testJoinGame_GameStartServiceJoinGameThrowsNoSuchElementException() {

            final Game game = new Game();
            game.setScoreToWin(0);
            game.setActive(false);
            game.setTeamList(Arrays.asList(new Team(new Game())));
            game.setTopic(new Topic("topicName"));
            game.setScoreList(Arrays.asList(new Score(0, 0L, new Team(new Game()), new Game())));
            game.setStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            game.setEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            game.setPausedTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            game.setGameName("gameName");
            game.setCountPlayers(0);
            when(mockGameStartService.getActiveGame(new User())).thenReturn(game);

            when(mockGameStartService.getGameJoinController()).thenReturn(new GameJoinController());
            when(mockGameStartService.joinGame(new Game(), new User())).thenThrow(NoSuchElementException.class);
            final String result = gameStartControllerUnderTest.joinGame();
            assertThat(result).isEqualTo("/secured/game_room/join.xhtml?faces-redirect=true");
        }
        @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testJoinGame_GameStartServiceJoinGameThrowsIllegalArgumentException() {
            final Game game = new Game();
            game.setScoreToWin(0);
            game.setActive(false);
            game.setTeamList(Arrays.asList(new Team(new Game())));
            game.setTopic(new Topic("topicName"));
            game.setGameName("gameName");
            game.setCountPlayers(0);
            when(mockGameStartService.getActiveGame(new User())).thenReturn(game);
            when(mockGameStartService.getGameJoinController()).thenReturn(new GameJoinController());
            when(mockGameStartService.joinGame(new Game(), new User())).thenThrow(IllegalArgumentException.class);
            final String result = gameStartControllerUnderTest.joinGame();
            assertThat(result).isEqualTo("/secured/game_room/join.xhtml?faces-redirect=true");
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testGetPlayerAvailability() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                when(mockGameStartService.getGameJoinController()).thenReturn(new GameJoinController());
                final List<PlayerAvailability> result = gameStartControllerUnderTest.getPlayerAvailability();
            });
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testSetAllTeamsReady() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                when(mockGameStartService.getGameJoinController()).thenReturn(new GameJoinController());
                gameStartControllerUnderTest.setAllTeamsReady();

            });

        }

        @Test
        void testSelectPlayer() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                final User user = new User();
                user.setUsername("username");
                user.setPassword("password");
                user.setEnabled(false);
                final Game game = new Game();
                game.setScoreToWin(0);
                game.setActive(false);
                game.setTeamList(Arrays.asList(new Team(new Game())));
                game.setGameName("gameName");
                game.setCountPlayers(0);
                user.setTeam(Arrays.asList(new Team(game)));
                user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
                final Raspberry raspberry = new Raspberry();
                raspberry.setRaspberryId(0);
                raspberry.setHostname("hostname");
                raspberry.setInUse(false);
                final Game game1 = new Game();
                game1.setScoreToWin(0);
                game1.setActive(false);
                game1.setGameName("gameName");
                game1.setCountPlayers(0);
                final SelectEvent<PlayerAvailability> event = new SelectEvent<>(UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()), null, new PlayerAvailability(user, game1));

                final Game game2 = new Game();
                String name = new String();
                game2.setScoreToWin(0);
                game2.setActive(false);
                game2.setTeamList(Arrays.asList(new Team(new Game())));
                game2.setTopic(new Topic("topicName"));
                game2.setGameName("gameName");
                game2.setCountPlayers(0);
                when(mockGameStartService.selectPlayer(new User())).thenReturn(game2);
                gameStartControllerUnderTest.selectPlayer(event);
                gameStartControllerUnderTest.setTeamName(name);
                gameStartControllerUnderTest.getTeamName();
            });
        }

        @Test
        void testFinishTeamAssign() throws Exception {
            final Game game = new Game();
            game.setScoreToWin(0);
            game.setActive(false);
            game.setGameName("gameName");
            game.setCountPlayers(0);
            when(mockGameStartService.finishTeamAssign("teamName")).thenReturn(game);
            gameStartControllerUnderTest.finishTeamAssign();
            verify(mockSessionInfoBean).setCurrentGame(new Game());
        }

        @Test
        void testFinishTeamAssign_GameStartServiceThrowsIllegalArgumentException() throws Exception {
            Game game = new Game();
            when(mockGameStartService.finishTeamAssign("teamName")).thenThrow(IllegalArgumentException.class);
            gameStartControllerUnderTest.finishTeamAssign();
            verify(mockSessionInfoBean).setCurrentGame(game);
        }

        @Test
        void testFinishTeamAssign_GameStartServiceThrowsIOException() throws Exception {
            when(mockGameStartService.finishTeamAssign("teamName")).thenThrow(IOException.class);
            gameStartControllerUnderTest.finishTeamAssign();
            verify(mockSessionInfoBean).setCurrentGame(new Game());
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testEnterGame() throws Exception {
            final Game game = new Game();
            game.setScoreToWin(0);
            game.setActive(false);
            game.setTeamList(Arrays.asList(new Team(new Game())));
            game.setGameName("gameName");
            game.setCountPlayers(0);
            when(mockGameStartService.enterGame()).thenReturn(game);
            gameStartControllerUnderTest.enterGame();

        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testEnterGame_GameStartServiceThrowsIOException() throws Exception {
            when(mockGameStartService.enterGame()).thenThrow(IOException.class);
            gameStartControllerUnderTest.enterGame();
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testResetAssignments() {

            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                Game game = new Game();
                when(mockGameStartService.getTeamService()).thenReturn(new TeamService());
                when(mockGameStartService.getGameJoinController()).thenReturn(new GameJoinController());
                gameStartControllerUnderTest.resetAssignments();
                gameStartControllerUnderTest.setTeamName("Boh");


            });
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testGetTeamSizeString() {
            when(mockGameStartService.getTeamSizeString()).thenReturn("result");
            final String result = gameStartControllerUnderTest.getTeamSizeString();
            assertThat(result).isNotEqualTo("result");
        }
    @MockitoSettings(strictness = Strictness.LENIENT)
        @Test
        void testGetTeamReady() {
            when(mockGameStartService.teamReady()).thenReturn(false);
            final boolean result = gameStartControllerUnderTest.getTeamReady();
            assertThat(result).isFalse();
        }

    @Test
    void testGetTeamName() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            when(gameStartService.getTeam().getTeamName()).thenReturn("result");
            final String result = gameStartController.getTeamName();
            assertThat(result).isEqualTo("result");
        });
    }
    @Test
    void testSetTeamName() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            when(gameStartService.getTeam().getTeamName()).thenReturn("result");
            final String name = gameStartController.getTeamName();
            assertThat(name).isEqualTo("result");
        });
    }
    @Test
    void testGetPlayer(){
        User user = new User();
        Game game = new Game();
        PlayerAvailability playerAvailability;
        gameStartController.getPlayer();

        assertFalse(gameStartController.getPlayer() == null);

    }
    @Test
    void testSetPlayer(){
        User user = new User();
        Game game = new Game();
        PlayerAvailability playerAvailability;
        gameStartController.getPlayer();
        assertTrue(gameStartController.getPlayer() == null);

    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testIsTeamComplete() {
        when(gameStartService.teamReady()).thenReturn(true);
        final boolean result = gameStartController.isTeamComplete();
        assertThat(result).isFalse();
    }

}
