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
        final List<User> users = Arrays.asList(user);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersUsernames()).thenReturn(users);
    }

    @Test
    void testGetMostValuedUserScores() {
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersScores()).thenReturn(Arrays.asList(0));
        final List<Integer> result = scoreServiceUnderTest.getMostValuedUserScores();
        assertThat(result).isEqualTo(Arrays.asList(0));
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
        final User user = new User();
        final List<User> users = Arrays.asList(user);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersInAGame()).thenReturn(users);
        when(scoreServiceUnderTest.scoreRepository.getTopPlayersInAGameScore()).thenReturn(Arrays.asList(0));
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
