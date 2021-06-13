package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)

public class GameTest {

    @Mock
    private Game game;
    @Mock
    private Raspberry raspberry;
    @Mock
    private Topic topic;


    @Test
    void testGetScoreToWin() {
        Game game = new Game();
        game.getScoreToWin();
        assertTrue(game.getScoreToWin() == 0);
    }

    @Test
    void testSetScoreToWin() {
        Game game = new Game();
        game.getScoreToWin();
        assertTrue(game.getScoreToWin() == 0);
    }

    @Test
    void testGetGame() {
        Game game = new Game();
        game.setCountPlayers(20);
        assertTrue(game.getCountPlayers() == 20);
    }

    @Test
    void testIsActive() {
        Game game = new Game();
        game.isActive();
        assertTrue(game.isActive() == false);

    }

    @Test
    void testSetIsActive() {
        Game game = new Game();
        game.isActive();
        assertTrue(game.isActive() == false);

    }

    @Test
    void testGetRaspberry() {
        Game game = new Game();
        game.setRaspberry(raspberry);
        assertTrue(game.getRaspberry() == raspberry);
    }



    @Test
    void testSetTeamList() {
        Game game = new Game();
        List<Team> scores = new ArrayList<>();
        game.setTeamList(scores);
        assertTrue(game.getTeamList() == scores);
    }

    @Test
    void testGetTopic() {
        Topic topic = new Topic();
        Game game = new Game();
        game.setTopic(topic);
        assertTrue(game.getTopic() == topic);
    }

    @Test
    void testSetTopic() {
        Topic topic = new Topic();
        Game game = new Game();
        game.setTopic(topic);
        assertTrue(game.getTopic() == topic);
    }

    @Test
    void testGetScores() {
        Team team = new Team();
        List<Score> scores = new ArrayList<>();
        team.setScores(scores);
        assertTrue(team.getScores() == scores);
    }

    @Test
    void testSetScores() {
        Game game = new Game();
        List<Score> scoreList = new ArrayList<>();
        game.setScoreList(scoreList);
        assertTrue(game.getScoreList() == scoreList);
    }

    @Test
    void testGetScoresList() {
        Game game = new Game();
        List<Score> scoreList = new ArrayList<>();
        game.setScoreList(scoreList);
        assertTrue(game.getScoreList() == scoreList);
    }

    @Test
    void testGetStartTime() {
        Game game = new Game();
        Date date = new Date();
        game.setStartTime(date);
        assertTrue(game.getStartTime() == date);
    }

    @Test
    void testSetStartTime() {
        Game game = new Game();
        Date date = new Date();
        game.setStartTime(date);
        assertTrue(game.getStartTime() == date);
    }

    @Test
    void testGetEndTime() {
        Game game = new Game();
        Date date = new Date();
        game.setEndTime(date);
        assertTrue(game.getEndTime() == date);
    }

    @Test
    void testSetEndTime() {
        Game game = new Game();
        Date date = new Date();
        game.setEndTime(date);
        assertTrue(game.getEndTime() == date);
    }

    @Test
    void testSetPausedTime() {
        Game game = new Game();
        Date date = new Date();
        game.setPausedTime(date);
        assertTrue(game.getPausedTime() == date);
    }

    @Test
    void testGetPausedTime() {
        Game game = new Game();
        Date date = new Date();
        game.setPausedTime(date);
        assertTrue(game.getPausedTime() == date);
    }

    @Test
    void testGetSerialVersionUID() {
        assertThat(Topic.getSerialVersionUID()).isEqualTo(1L);
    }

    @Test
    void testGetGameId() {
        Game game = new Game();
        game.setGameId(2);
        assertTrue(game.getGameId() == 2);
    }

    @Test
    void testSetGameId() {
        Game game = new Game();
        game.setGameId(2);
        assertTrue(game.getGameId() == 2);
    }

    @Test
    void testGetGameName() {
        Game game = new Game();
        game.setGameName("GAME");
        assertTrue(game.getGameName() == "GAME");
    }

    @Test
    void testSetGameName() {
        Game game = new Game();
        game.setGameName("GAME");
        assertTrue(game.getGameName() == "GAME");
    }

    @Test
    void testSetCountPlayers() {
        Game game = new Game();
        game.getCountPlayers();
        assertTrue(game.getCountPlayers() == 0);
    }

    @Test
    void testGetCountPlayers() {
        Game game = new Game();
        game.getCountPlayers();
        assertTrue(game.getCountPlayers() == 0);
    }

    @Test
    void testGetTeamSize() {
        Game game = new Game();
        game.setTeamSize(4);
        assertTrue(game.getTeamSize() == 4);
    }

    @Test
    void testSetTeamSize() {
        Game game = new Game();
        game.setTeamSize(4);
        assertTrue(game.getTeamSize() == 4);
    }

    @Test
    void testEquals() {
        final boolean result = game.equals("o");
        assertThat(result).isFalse();
    }

    /*@Test
    void testHashCode() {
        final int result = game.hashCode();
        assertThat(result).isEqualTo(10);
    }*/
}







