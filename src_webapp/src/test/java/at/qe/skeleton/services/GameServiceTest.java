package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TopicRepository;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository mockGameRepository;
    @Mock
    private ScoreRepository mockScoreRepository;

    @InjectMocks
    private GameService gameServiceUnderTest;

    @Test
    void testGetAllActiveGames() {
        Collection<Game> result = gameServiceUnderTest.getAllActiveGames();
        Collection<Game> expectedResult = mockGameRepository.findAllActive();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveGame() {
        final Game game = new Game();
        final Game expectedResult = mockGameRepository.save(game);
        final Game result = gameServiceUnderTest.saveGame(game);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testReloadGame() {
        final Game game = new Game();
        final Game result = gameServiceUnderTest.reloadGame(game);
        final Game expectedResult = mockGameRepository.findByGameId(game.getGameId());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteGame() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final Game game = new Game();
            gameServiceUnderTest.deleteGame(game);
            try {
                mockGameRepository.delete(game);
            } catch (DataIntegrityViolationException e) {
                mockGameRepository.delete(game);
            }
        });
    }

    @Test
    void testGetRunningGameByRaspberry() {
        final Raspberry raspberry = new Raspberry();
        Game result = gameServiceUnderTest.getRunningGameByRaspberry(raspberry.getRaspberryId());
        Game expectedResult = mockGameRepository.findActiveGameByRaspberry(raspberry.getRaspberryId());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetScoreRepository() {
        List<Score> scores = mockScoreRepository.findAll();
        final ScoreRepository result = gameServiceUnderTest.getScoreRepository();
        assertThat(result).isEqualTo(scores);
    }

    @Test
    void testGetTeamService() {
        TeamService teamService = new TeamService();
        TeamService result = gameServiceUnderTest.getTeamService();
        assertThat(result).isEqualTo(teamService);
    }

    @Test
    void testGetGameJoinController() {
        GameJoinController gameJoinController = new GameJoinController();
        GameJoinController result = gameServiceUnderTest.getGameJoinController();
        assertThat(result).isEqualTo(gameJoinController);
    }

    @Test
    void testGetAllGames() {
        Collection<Game> result = gameServiceUnderTest.getAllGames();
        Collection<Game> games = mockGameRepository.findAll();
        assertThat(result).isEqualTo(games);
    }

    @Test
    void testGetPersonalGames() {
        Raspberry raspberry = new Raspberry();
        Collection<Game> result = gameServiceUnderTest.getPersonalGames(raspberry);
        Collection<Game> personalGames = mockGameRepository.findAllByRaspberry(raspberry);
        assertThat(result).isEqualTo(personalGames);
    }

    @Test
    void testGetMostPopularTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockGameRepository.getMostPopularTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = gameServiceUnderTest.getMostPopularTopics();
        assertThat(result).isEqualTo(expectedResult);
    }
}
