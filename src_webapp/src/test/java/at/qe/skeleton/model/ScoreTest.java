package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ScoreTest {

    @Mock
    private Team mockTeam;
    @Mock
    private Game mockGame;

    private Score scoreUnderTest;

    @BeforeEach
    void setUp() {
        scoreUnderTest = new Score(0, 0L, mockTeam, mockGame);
    }

    @Test
    void testGetScoreId() {
        Score score = new Score();
        score.setScoreId(1);
        assertTrue(score.getScoreId() == 1);
    }

    @Test
    void testSetScoreId() {
        Score score = new Score();
        score.setScoreId(1);
        assertTrue(score.getScoreId() == 1);
    }

    @Test
    void testGetTotalRoundScore() {
        Score score = new Score();
        score.setTotalRoundScore(3);
        assertTrue(score.getTotalRoundScore() == 3);
    }

    @Test
    void testSetTotalRoundScore() {
        Score score = new Score();
        score.setTotalRoundScore(3);
        assertTrue(score.getTotalRoundScore() == 3);
    }

    @Test
    void testGetUser() {
        Score score = new Score();
        User user = new User();
        score.setUser(user);
        assertTrue(score.getUser() == user);
    }

    @Test
    void testSetUser() {
        Score score = new Score();
        User user = new User();
        score.setUser(user);
        assertTrue(score.getUser() == user);
    }

    @Test
    void testGetTeam() {
        Score score = new Score();
        Team team = new Team();
        score.setTeam(team);
        assertTrue(score.getTeam() == team);
    }

    @Test
    void testSetTeam() {
        Score score = new Score();
        Team team = new Team();
        score.setTeam(team);
        assertTrue(score.getTeam() == team);
    }

    @Test
    void testGetSerialVersionUID() {
        assertThat(Score.getSerialVersionUID()).isEqualTo(1L);
    }

    @Test
    void testGetGame() {
        Score score = new Score();
        Game game = new Game();
        score.setGame(game);
        assertTrue(score.getGame() == game);
    }

    @Test
    void testSetGame() {
        Score score = new Score();
        Game game = new Game();
        score.setGame(game);
        assertTrue(score.getGame() == game);
    }
}
