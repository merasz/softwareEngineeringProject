package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScoreServiceTest {

    private ScoreService scoreServiceUnderTest;

    @Mock
    private ScoreRepository scoreRepository;

    @BeforeEach
    void setUp() {
        scoreServiceUnderTest = new ScoreService();
        scoreServiceUnderTest.userService = mock(UserService.class);
        scoreServiceUnderTest.scoreRepository = mock(ScoreRepository.class);
    }

    @Test
    void testGetMostValuedUsers() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final List<User> result = scoreServiceUnderTest.getMostValuedUsers();
            final List<User> expectedResult = scoreRepository.getTopPlayersUsernames();
            assertThat(result).isEqualTo(expectedResult);
        });
    }

    @Test
    void testGetMostValuedUserScores() {
        Map<User, Integer> usersWithScores = scoreServiceUnderTest.getUsersWithScores();
    }

    @Test
    void testGetUsersWithScores() {
        final User user = new User();
        final List<User> users = Arrays.asList(user);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersUsernames()).thenReturn(users);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersScores()).thenReturn(Arrays.asList(0));
    }

    @Test
    void testGetUsersWithScoreAGame() {
        List<User> users = scoreServiceUnderTest.scoreRepository.getTopPlayersInAGame();
        List<Integer> scores = scoreServiceUnderTest.scoreRepository.getTopPlayersInAGameScore();
        Map<User, Integer> userIntegerMap = scoreServiceUnderTest.getUsersWithScores();
    }

    @Test
    void testGetScoresForTeamByGame() {
        final List<Team> teams = Arrays.asList(new Team());
        when(scoreServiceUnderTest.scoreRepository.getTeamsForGame(0)).thenReturn(teams);
    }

    @Test
    void testGetScoresForTeams() {
        final List<Score> scores = new ArrayList<>();
        final Game game = new Game();
        assertThat(scoreServiceUnderTest.getScoresForTeams(game)).isEqualTo(scores);
    }
}
