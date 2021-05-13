package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameListControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameListController gameListControllerUnderTest;

    @Test
    void testGetGames() {

        final User user = new User();
        final Collection<Game> expectedResult = Arrays.asList(new Game());

        final User user1 = new User();
        final Collection<Game> games = Arrays.asList(new Game());
        when(gameService.getAllGames()).thenReturn(games);
        final Collection<Game> result = gameListControllerUnderTest.getGames();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetGames_GameServiceReturnsNoItems() {

        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        when(gameService.getAllGames()).thenReturn(Collections.emptyList());
        final Collection<Game> result = gameListControllerUnderTest.getGames();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetactivegGames() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Collection<Game> expectedResult = Arrays.asList(new Game());

        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        final Collection<Game> games = Arrays.asList(new Game());
        when(gameService.getAllActiveGames()).thenReturn(games);

        final Collection<Game> result = gameListControllerUnderTest.getactivegGames();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetactivegGames_GameServiceReturnsNoItems() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        when(gameService.getAllActiveGames()).thenReturn(Collections.emptyList());

        final Collection<Game> result = gameListControllerUnderTest.getactivegGames();
        assertThat(result).isEqualTo(expectedResult);
    }
}