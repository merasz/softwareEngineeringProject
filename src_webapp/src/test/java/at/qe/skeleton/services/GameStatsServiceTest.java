package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//class GameStatsServiceTest {
//
//    private GameStatsService gameStatsServiceUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        gameStatsServiceUnderTest = new GameStatsService();
//    }
//
//    @Test
//    void testUpdateScores() {
//        final User user = new User();
//        final Game game = new Game();
//        final Term term = new Term();
//        final Game expectedResult = new Game();
////        final Game result = gameStatsServiceUnderTest.updateScores(game, 0, term);
////        assertThat(result).isEqualTo(expectedResult);
//    }
//
//    @Test
//    void testGetSecondsPlayed() {
//        final Game game = new Game();
////        final int result = gameStatsServiceUnderTest.getSecondsPlayed(game);
////        assertThat(result).isEqualTo(0);
//    }
//
//    @Test
//    void testGetPlayerScore() {
//        final User user = new User();
//        final Game game = new Game();
////        final Score result = gameStatsServiceUnderTest.getPlayerScore(game, user);
//        // verify result
//    }
//
//    @Test
//    void testGetGameScores() {
//        final User user = new User();
//        final Game game = new Game(0, 0, 0, new Topic("topicName"), new Raspberry(0, "hostname", Arrays.asList(user), false, "ipAddress"));
////        final Score result = gameStatsServiceUnderTest.getGameScores(game);
//        // verify result
//    }
//
//    @Test
//    void testGetTeamScores() {
//        final User user = new User();
//        final Game game = new Game();
//        //final List<Score> result = gameStatsServiceUnderTest.getTeamScores(game);
//        // verify result
//    }
//}
