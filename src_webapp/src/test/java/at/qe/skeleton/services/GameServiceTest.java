package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TopicRepository;
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
        final List<Game> games = Arrays.asList(new Game());
        when(mockGameRepository.findAllActive()).thenReturn(games);
    }

    @Test
    void testSaveGame() {
        final Game game = new Game();
        final Game expectedResult = new Game();
        final Game result = gameServiceUnderTest.saveGame(game);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testReloadGame() {
        final Game game = new Game();
        final Game expectedResult = new Game();
        final Game result = gameServiceUnderTest.reloadGame(game);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteGame() {
        final Game game = new Game();
        gameServiceUnderTest.deleteGame(game);
        when(mockScoreRepository.findGameScoresByGame(game)).thenReturn(null);
        when(mockTeamService.getTeamsByGame(game)).thenReturn(null);
    }

    @Test
    void testGetRunningGameByRaspberry() {
        final Game game = new Game();
        when(mockGameRepository.findActiveGameByRaspberry(0)).thenReturn(game);
    }

    @Test
    void testGetScoreRepository() {
        List<Score> scores = mockScoreRepository.findAll();
        final ScoreRepository result = gameServiceUnderTest.getScoreRepository();
        assertThat(result).isEqualTo(scores);
    }

    @Test
    void testGetGameJoinController() {


    }

    @Test
    void testGetAllGames() {
        final List<Game> games = Arrays.asList(new Game());
        when(mockGameRepository.findAll()).thenReturn(games);
    }

    @Test
    void testGetMostPopularTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockGameRepository.getMostPopularTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = gameServiceUnderTest.getMostPopularTopics();
        assertThat(result).isEqualTo(expectedResult);
    }
}
