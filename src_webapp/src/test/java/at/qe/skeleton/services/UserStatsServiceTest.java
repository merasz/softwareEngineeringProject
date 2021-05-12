package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TopicGamesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(userStatsServiceUnderTest.teamService.getTeamsByPlayer(new User())).thenReturn(teams);
        final List<Score> scores = Arrays.asList(new Score());
        when(userStatsServiceUnderTest.scoreRepository.findGameScoresByUser(Arrays.asList(new Team()))).thenReturn(scores);
//        final List<Score> result = userStatsServiceUnderTest.getBestScoresFromUser(user);
    }

    @Test
    void testGetLatestScoresFromUser() {
        final User user = new User();
        final List<Score> scores = Arrays.asList(new Score());
        when(userStatsServiceUnderTest.scoreRepository.findGameScoresByUser(Arrays.asList(new Team()))).thenReturn(scores);
        final List<Score> result = userStatsServiceUnderTest.getLatestScoresFromUser(user);
    }

    @Test
    void testGetGameCountByUser() {
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(userStatsServiceUnderTest.teamService.getTeamsByPlayer(new User())).thenReturn(teams);
        when(userStatsServiceUnderTest.scoreRepository.countGamesByTeam(Arrays.asList(new Team()))).thenReturn(0);
//        final int result = userStatsServiceUnderTest.getGameCountByUser(user);
//        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetWonCountByUser() {
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(userStatsServiceUnderTest.teamService.getTeamsByPlayer(new User())).thenReturn(teams);
        when(userStatsServiceUnderTest.scoreRepository.countWonGamesByUser(Arrays.asList(new Team()))).thenReturn(0);
//        final int result = userStatsServiceUnderTest.getWonCountByUser(user);
//        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetLostCountByUser() {
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(userStatsServiceUnderTest.teamService.getTeamsByPlayer(new User())).thenReturn(teams);
        when(userStatsServiceUnderTest.scoreRepository.countLostGamesByUser(Arrays.asList(new Team()))).thenReturn(0);
//        final int result = userStatsServiceUnderTest.getLostCountByUser(user);
//        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetWonGamesByTopics() {
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(userStatsServiceUnderTest.teamService.getTeamsByPlayer(new User())).thenReturn(teams);
        final List<GameTopicCount> gameTopicCounts = Arrays.asList(new GameTopicCount(new Topic("topicName"), 0L));
        when(userStatsServiceUnderTest.topicGamesRepository.countWonGamesByUserAndTopic(Arrays.asList(new Team()))).thenReturn(gameTopicCounts);
//        final List<GameTopicCount> result = userStatsServiceUnderTest.getWonGamesByTopics(user);
    }
}