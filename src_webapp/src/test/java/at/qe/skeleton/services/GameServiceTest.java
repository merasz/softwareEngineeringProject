package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository mockGameRepository;
    @Mock
    private ScoreRepository mockScoreRepository;
    @Mock
    private TeamService mockTeamService;
    @Mock
    private GameJoinController mockGameJoinController;

    @InjectMocks
    private GameService gameServiceUnderTest;

    @Test
    void testGetAllActiveGames() {
        final User user = new User();
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        final User user1 = new User();
        final List<Game> games = Arrays.asList(new Game());
        when(mockGameRepository.findAllActive()).thenReturn(games);
        final Collection<Game> result = gameServiceUnderTest.getAllActiveGames();
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveGame() {
        final User user = new User();
        final Game game = new Game();
        final Game expectedResult = new Game();
//        when(mockGameRepository.save(new Game()));
//        final Game result = gameServiceUnderTest.saveGame(game);
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testReloadGame() {
        final User user = new User();
        final Game game = new Game();
        final Game expectedResult = new Game();
//        when(mockGameRepository.findByGameId(0)).thenReturn(game);
//        final Game result = gameServiceUnderTest.reloadGame(game);
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetRunningGameByRaspberry() {
        final User user = new User();
        final Game expectedResult = new Game();
        final Game game = new Game();
        when(mockGameRepository.findActiveGameByRaspberry(0)).thenReturn(game);
        final Game result = gameServiceUnderTest.getRunningGameByRaspberry(0);
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testIncrementNumPlayers() {
        //gameServiceUnderTest.incrementNumPlayers(0);
    }

    @Test
    void testGetAllGames() {
        final User user = new User();
        final Collection<Game> expectedResult = Arrays.asList(new Game());
        final List<Game> games = Arrays.asList(new Game());
        when(mockGameRepository.findAll()).thenReturn(games);
        final Collection<Game> result = gameServiceUnderTest.getAllGames();
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMostPopularTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockGameRepository.getMostPopularTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = gameServiceUnderTest.getMostPopularTopics();
        assertThat(result).isEqualTo(expectedResult);
    }
}
