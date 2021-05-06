package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LobbyScoresControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private UserStatsService userStatsService;
    @Mock
    private TeamService teamService;
    @Mock
    private GameService gameService;
    @Mock
    private SessionInfoBean sessionInfoBean;
    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private LobbyScoresController lobbyScoresController;

    @Test
    void testInit() {
        when(gameService.getMostPopularTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final User user = new User();
        final List<User> users = Arrays.asList(user);
        when(scoreService.getMostValuedUsers()).thenReturn(users);

        when(scoreService.getMostValuedUserScores()).thenReturn(Arrays.asList(0));
        when(scoreService.getUsersWithScores()).thenReturn(new HashMap<>());
        when(scoreService.getUsersWithScoreAGame()).thenReturn(new HashMap<>());
        lobbyScoresController.init();
    }

    @Test
    void testInit_GameServiceReturnsNoItems() {
        when(gameService.getMostPopularTopics()).thenReturn(Collections.emptyList());

        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final List<User> users = Arrays.asList(user);
        when(scoreService.getMostValuedUsers()).thenReturn(users);

        when(scoreService.getMostValuedUserScores()).thenReturn(Arrays.asList(0));
        when(scoreService.getUsersWithScores()).thenReturn(new HashMap<>());
        when(scoreService.getUsersWithScoreAGame()).thenReturn(new HashMap<>());
        lobbyScoresController.init();
    }

    @Test
    void testInit_ScoreServiceGetMostValuedUsersReturnsNoItems() {
        when(gameService.getMostPopularTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        when(scoreService.getMostValuedUsers()).thenReturn(Collections.emptyList());
        when(scoreService.getMostValuedUserScores()).thenReturn(Arrays.asList(0));
        when(scoreService.getUsersWithScores()).thenReturn(new HashMap<>());
        when(scoreService.getUsersWithScoreAGame()).thenReturn(new HashMap<>());

        lobbyScoresController.init();

    }

    @Test
    void testInit_ScoreServiceGetMostValuedUserScoresReturnsNoItems() {
        when(gameService.getMostPopularTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final List<User> users = Arrays.asList(user);
        when(scoreService.getMostValuedUsers()).thenReturn(users);

        when(scoreService.getMostValuedUserScores()).thenReturn(Collections.emptyList());
        when(scoreService.getUsersWithScores()).thenReturn(new HashMap<>());
        when(scoreService.getUsersWithScoreAGame()).thenReturn(new HashMap<>());
        lobbyScoresController.init();

    }

    //@Test
    void testGetHighscores() {
        final List<Map.Entry<User, Integer>> expectedResult = Arrays.asList();
        final List<Map.Entry<User, Integer>> result = lobbyScoresController.getHighscores();
        assertThat(result).isEqualTo(expectedResult);
    }

    // @Test
    void testGetHighscoresAGame() {
        final List<Map.Entry<User, Integer>> expectedResult = Arrays.asList();
        final List<Map.Entry<User, Integer>> result = lobbyScoresController.getHighscoresAGame();
        assertThat(result).isEqualTo(expectedResult);
    }
}
