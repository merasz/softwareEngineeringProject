package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class GameListControllerTest {
    @Mock
    private User user;

    @Mock
    private GameService gameService;

    @Mock
    private SessionInfoBean sessionInfoBean;

    @InjectMocks
    private GameListController gameListController;



    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetGames() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        GameService gameService = Mockito.mock(GameService.class);
        GameListController gameListController = new GameListController();
        final User user = new User();
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        final Game game = new Game();
        final Collection<Game> games = Arrays.asList(new Game());
        when(gameService.getAllGames()).thenReturn(games);
        gameListController.getGames();
        verify(gameListController).getGames();
        final Collection<Game> result = gameListController.getGames();
        assertThat(result).isEqualTo(expectedResult);
        });

    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetGames_GameServiceReturnsNoItems() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        when(gameService.getAllGames()).thenReturn(Collections.emptyList());
        final Collection<Game> result = gameListController.getGames();
        assertThat(result).isEqualTo(expectedResult);
        assertEquals(gameService.getPersonalGames(user.getRaspberry()), gameListController.getGames());
        verify(gameService).getAllGames();
            });
    }

    @Test
    void testGetActivegGames() {
                Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Collection<Game> expectedResult = Arrays.asList(new Game());

        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        final Collection<Game> games = Arrays.asList(new Game());
        when(gameService.getAllActiveGames()).thenReturn(games);

        final Collection<Game> result = gameListController.getActiveGames();

        assertThat(result).isEqualTo(expectedResult);
            });
    }

    @Test
    void testGetactiveGames_GameServiceReturnsNoItems() {


        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        when(gameService.getAllActiveGames()).thenReturn(Collections.emptyList());

        final Collection<Game> result = gameListController.getActiveGames();
        assertThat(result).isNotEqualTo(expectedResult);

    }
    @Test
    public void testSetImageForConditionOne() {
                Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

                    User user = new User();
                    when(user.getRoles().contains(UserRole.ADMIN)).thenReturn(true);
                    assertThat(gameService.getAllGames());
                    when(user.getRoles().contains(UserRole.ADMIN)).thenReturn(false);
                    assertThat(gameService.getPersonalGames(user.getRaspberry()));
                });


    }
    @Test
    void testGetStatusString(){
        Game game = new Game();
        game.getEndTime();
        gameListController.getStatusString(game);
    }
            }








