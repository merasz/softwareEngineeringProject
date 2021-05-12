package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScoreServiceTest {

    private ScoreService scoreServiceUnderTest;

    @BeforeEach
    void setUp() {
        scoreServiceUnderTest = new ScoreService();
        scoreServiceUnderTest.userService = mock(UserService.class);
        scoreServiceUnderTest.scoreRepository = mock(ScoreRepository.class);
    }

    @Test
    void testGetMostValuedUsers() {
        final User user = new User();
        final List<User> expectedResult = Arrays.asList(user);
        final User user1 = new User();
        final List<User> users = Arrays.asList(user1);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersUsernames()).thenReturn(users);
        final List<User> result = scoreServiceUnderTest.getMostValuedUsers();
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMostValuedUserScores() {
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersScores()).thenReturn(Arrays.asList(0));
        final List<Integer> result = scoreServiceUnderTest.getMostValuedUserScores();
        assertThat(result).isEqualTo(Arrays.asList(0));
    }

    @Test
    void testGetUsersWithScores() {
        final Map<User, Integer> expectedResult = new HashMap<>();
        final User user = new User();
        final List<User> users = Arrays.asList(user);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersUsernames()).thenReturn(users);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersScores()).thenReturn(Arrays.asList(0));
        final Map<User, Integer> result = scoreServiceUnderTest.getUsersWithScores();
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUsersWithScoreAGame() {
        final Map<User, Integer> expectedResult = new HashMap<>();
        final User user = new User();
        final List<User> users = Arrays.asList(user);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersInAGame()).thenReturn(users);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersInAGameScore()).thenReturn(Arrays.asList(0));
        final Map<User, Integer> result = scoreServiceUnderTest.getUsersWithScoreAGame();
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetScoresForTeamByGame() {
        final User user = new User();
        final Game game = new Game();
        final Map<Team, Integer> expectedResult = new HashMap<>();
        final List<Team> teams = Arrays.asList(new Team());
        when(scoreServiceUnderTest.scoreRepository.getTeamsForGame(0)).thenReturn(teams);
        //when(scoreServiceUnderTest.scoreRepository.getForTeamsByGameScore()).thenReturn(Arrays.asList(0));
        //final Map<Team, Integer> result = scoreServiceUnderTest.getScoresForTeamByGame(game);
        //assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetScoresForTeams() {
        final User user = new User();
        final Game game = new Game();
        final List<Score> scores = Arrays.asList(new Score());
        when(scoreServiceUnderTest.scoreRepository.getScoresForTeamsByGame(new Game())).thenReturn(scores);
//        final List<Score> result = scoreServiceUnderTest.getScoresForTeams(game);
    }
}
