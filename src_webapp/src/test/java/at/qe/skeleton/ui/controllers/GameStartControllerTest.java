package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.primefaces.event.SelectEvent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameStartControllerTest {

    @Mock
    private GameStartService gameStartService;
    @Mock
    private SessionInfoBean sessionInfoBean;

    @InjectMocks
    private GameStartController gameStartController;



    @Test
    void testStartMio(){
        Game game = new Game();

    }
    @Test
    void testStartGame() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        GameStartService myService = mock(GameStartService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.startGame(new Game(), new User())).thenReturn(game);
        final String result = myService.getTeamSizeString();
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testStartGame_GameStartServiceThrowsIllegalArgumentException() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        GameStartService myService = mock(GameStartService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.startGame(new Game(), new User())).thenThrow(IllegalArgumentException.class);

        final String result = myService.getTeamSizeString();

        assertThat(result).isEqualTo(null);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testJoinGame() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        when(gameStartService.getActiveGame(new User())).thenReturn(game);

        when(gameStartService.getGameJoinController()).thenReturn(new GameJoinController());
        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEnabled(false);
        final Game game1 = new Game();
        when(gameStartService.joinGame(new Game(), new User())).thenReturn(game1);

        final String result = gameStartController.joinGame();
        assertThat(result).isEqualTo("/secured/game_room/join.xhtml?faces-redirect=true");
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testJoinGame_GameStartServiceJoinGameThrowsNoSuchElementException() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        when(gameStartService.getActiveGame(new User())).thenReturn(game);

        when(gameStartService.getGameJoinController()).thenReturn(new GameJoinController());
        when(gameStartService.joinGame(new Game(), new User())).thenThrow(NoSuchElementException.class);

        final String result = gameStartController.joinGame();

        assertThat(result).isEqualTo("/secured/game_room/join.xhtml?faces-redirect=true");
        boolean allTeamsEntered = gameStartService.getGameJoinController().allReadyToStart(game);
        assertThat(allTeamsEntered).isFalse();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testJoinGame_GameStartServiceJoinGameThrowsIllegalArgumentException() {
        final User user = new User();
        final Game game = new Game();
        when(gameStartService.getActiveGame(new User())).thenReturn(game);

        when(gameStartService.getGameJoinController()).thenReturn(new GameJoinController());
        when(gameStartService.joinGame(new Game(), new User())).thenThrow(IllegalArgumentException.class);
        final String result = gameStartController.joinGame();
        assertThat(result).isNotEqualTo("/secured/game_room/join.xhtml?faces-redirect=true");
    }

    @Test
    void testGetPlayerAvailability() {
        when(gameStartService.getGameJoinController()).thenReturn(new GameJoinController());
        final List<PlayerAvailability> result = gameStartController.getPlayerAvailability();
        assertTrue(gameStartController.getPlayerAvailability() == result);
    }

    @Test
    void testSetAllTeamsReady() {
        GameStartController myService = mock(GameStartController.class, Mockito.RETURNS_DEEP_STUBS);
        when(gameStartService.getGameJoinController()).thenReturn(new GameJoinController());
        //assertTrue(gameStartController.setAllTeamsReady() == myService);
    }

    @Test
    void testSelectPlayer() {
        final User user = new User();
        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        final SelectEvent<PlayerAvailability> event = new SelectEvent<>(UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()), null, new PlayerAvailability(user, new Game()));
        final Game game = new Game();
        when(gameStartService.selectPlayer(new User())).thenReturn(game);
        gameStartController.selectPlayer(event);
        verify(gameStartController).selectPlayer(event);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testFinishTeamAssign() throws Exception {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        when(gameStartService.finishTeamAssign("teamName")).thenReturn(game);
        gameStartController.finishTeamAssign();
        verify(sessionInfoBean).setCurrentGame(new Game());
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testFinishTeamAssign_GameStartServiceThrowsIllegalArgumentException() throws Exception {
        GameStartService myService = mock(GameStartService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.finishTeamAssign("teamName")).thenThrow(IllegalArgumentException.class);
        gameStartController.finishTeamAssign();
        verify(sessionInfoBean).setCurrentGame(new Game());
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testFinishTeamAssign_GameStartServiceThrowsIOException() throws Exception {
        GameStartService myService = mock(GameStartService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.finishTeamAssign("teamName")).thenThrow(IOException.class);
        gameStartController.finishTeamAssign();
        verify(sessionInfoBean).setCurrentGame(new Game());
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testEnterGame() throws Exception {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        when(gameStartService.enterGame()).thenReturn(game);
        gameStartController.enterGame();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testEnterGame_GameStartServiceThrowsIOException() throws Exception {
        GameStartService myService = mock(GameStartService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.enterGame()).thenThrow(IOException.class);
        gameStartController.enterGame();
    }
    @Test
    void testGetTeamName() {
        when(gameStartService.getTeam().getTeamName()).thenReturn("result");
        final String result = gameStartController.getTeamName();
        assertThat(result).isEqualTo("result");
    }
    @Test
    void testSetTeamName() {
        when(gameStartService.getTeam().getTeamName()).thenReturn("result");
        final String name = gameStartController.getTeamName();
        assertThat(name).isEqualTo("result");
    }
    @Test
    void testGetPlayer(){
        User user = new User();
        Game game = new Game();
        PlayerAvailability playerAvailability;
        gameStartController.getPlayer();
        assertTrue(gameStartController.getPlayer() == null);

    }
    @Test
    void testSetPlayer(){
        User user = new User();
        Game game = new Game();
        PlayerAvailability playerAvailability;
        gameStartController.getPlayer();
        assertTrue(gameStartController.getPlayer() == null);

    }

    @Test
    void testGetTeamSizeString() {
        when(gameStartService.getTeamSizeString()).thenReturn("result");
        final String result = gameStartController.getTeamSizeString();
        assertThat(result).isEqualTo("result");
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTeamReady() {
        when(gameStartService.teamReady()).thenReturn(true);
        final boolean result = gameStartController.getTeamReady();
        assertThat(result).isTrue();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testIsTeamComplete() {
        when(gameStartService.teamReady()).thenReturn(true);
        final boolean result = gameStartController.isTeamComplete();
        assertThat(result).isFalse();
    }

}
