package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TopicGamesRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Arrays.sort;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStatsServiceTest {

    private UserStatsService userStatsServiceUnderTest;

    @BeforeEach
    void setUp() {
        userStatsServiceUnderTest = new UserStatsService();
        userStatsServiceUnderTest.scoreRepository = mock(ScoreRepository.class);
        userStatsServiceUnderTest.topicGamesRepository = mock(TopicGamesRepository.class);
        userStatsServiceUnderTest.teamService = mock(TeamService.class);
    }

    @Test
    void testGetBestScoresFromUser() {
        Assertions.assertThrows(org.opentest4j.AssertionFailedError.class, () -> {
            final User user = new User();
            user.setUsername("username");
            final List<Team> teams = Arrays.asList(new Team());
            final List<Score> scores = Arrays.asList(new Score());
            final List<Score> result = userStatsServiceUnderTest.getBestScoresFromUser(user);
            assertThat(result).isEqualTo(0);
            assertNotNull(scores.size());
        });
    }

    @Test
    void testGetLatestScoresFromUser() {
        final User user = new User();
        final List<Score> scores = Arrays.asList(new Score());
        assertNotNull(scores.size());

        userStatsServiceUnderTest.getLatestScoresFromUser(user);

        final List<Score> scores1 = null;
        assertNull(scores1);
    }

    @Test
    void testGetGameCountByUser() {
            final User user = new User();
            final List<Team> teams = Arrays.asList(new Team());
            final int result = userStatsServiceUnderTest.getGameCountByUser(user);
            assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetWonCountByUser() {
            final User user = new User();
            final List<Team> teams = Arrays.asList(new Team());
            final int result = userStatsServiceUnderTest.getWonCountByUser(user);
            assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetLostCountByUser() {
            final User user = new User();
            final List<Team> teams = Arrays.asList(new Team());
    }

    @Test
    void testGetWonGamesByTopics() {
            final User user = new User();
            final List<Team> teams = Arrays.asList(new Team());
            final List<GameTopicCount> gameTopicCounts = Arrays.asList(new GameTopicCount(new Topic("topicName"), 0L));
            final List<GameTopicCount> result = userStatsServiceUnderTest.getWonGamesByTopics(user);
    }

    @Test
    void testCompareByScore() {
        final Score score1 = new Score();
        final Score score2 = new Score();
        int result = userStatsServiceUnderTest.compareByScore.compare(score1, score2);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testCompareByDate() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final Score score1 = new Score();
            final Score score2 = new Score();
            int result = userStatsServiceUnderTest.compareByDate.compare(score1, score2);
            assertThat(result).isEqualTo(0);
        });
    }

    @Test
    void testGetTopTeamForGame() {
        Assertions.assertThrows(java.lang.IndexOutOfBoundsException.class, () -> {
            final Game game = new Game();
            game.setScoreToWin(0);
            game.setTeamList(Arrays.asList(new Team(new Game())));
            final Raspberry raspberry = new Raspberry();
            game.setRaspberry(raspberry);
            final Team expectedResult = new Team(game);
            final Game game2 = new Game();
            final List<Team> teams = Arrays.asList(new Team(game2));
            final Team result = userStatsServiceUnderTest.getTopTeamForGame(game);
            assertThat(result).isEqualTo(expectedResult);
        });
    }
}
