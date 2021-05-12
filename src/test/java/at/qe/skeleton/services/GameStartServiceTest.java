package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.ui.controllers.gameSockets.GamePlaySocketController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameStartServiceTest {

    @Mock
    private GamePlaySocketController mockGamePlaySocketController;

    @InjectMocks
    private GameStartService gameStartServiceUnderTest;

    //@Test
    void testStartGame() {
        final User user = new User();
        final Game game = new Game();
        final Game expectedResult = new Game();
        final Game result = gameStartServiceUnderTest.startGame(game, user);
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testJoinGame() {
        final User user = new User();
        final Game game = new Game();
        final Game expectedResult = new Game();
        final Game result = gameStartServiceUnderTest.joinGame(game, user);
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testGetActiveGame() {
        final User user = new User();
        final Game expectedResult = new Game();
        final Game result = gameStartServiceUnderTest.getActiveGame(user);
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testSelectPlayer() {
        final User user = new User();
        final Game expectedResult = new Game();
        final Game result = gameStartServiceUnderTest.selectPlayer(user);
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testTeamReady() {
        final boolean result = gameStartServiceUnderTest.teamReady();
        assertThat(result).isTrue();
    }

    //@Test
    void testFinishTeamAssign() throws Exception {
        final User user = new User();
        final Game expectedResult = new Game();
        final Game result = gameStartServiceUnderTest.finishTeamAssign("teamName");
        assertThat(result).isEqualTo(expectedResult);
        verify(mockGamePlaySocketController).putTeamPlayerMap(new Game(), new LinkedList<>(Arrays.asList(new TeamPlayer())));
        verify(mockGamePlaySocketController).initGame(new Game());
    }

    //@Test
    void testEnterGame() throws Exception {
        final User user = new User();
        final Game expectedResult = new Game();
        final Game result = gameStartServiceUnderTest.enterGame();
        assertThat(result).isEqualTo(expectedResult);
        verify(mockGamePlaySocketController).putTeamPlayerMap(new Game(), new LinkedList<>(Arrays.asList(new TeamPlayer())));
        verify(mockGamePlaySocketController).initGame(new Game());
    }

    //@Test
    void testGetTeamSizeString() {
        final String result = gameStartServiceUnderTest.getTeamSizeString();
        assertThat(result).isEqualTo("result");
    }
}
