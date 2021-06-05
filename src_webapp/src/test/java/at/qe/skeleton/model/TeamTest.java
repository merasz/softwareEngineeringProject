package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TeamTest {

    @Mock
    private Game mockGame;

    private Team teamUnderTest;

    @BeforeEach
    void setUp() {
        teamUnderTest = new Team(mockGame);
    }

    @Test
    void testGetTeamId() {
        Team team = new Team();
        team.setTeamId(1L);
        assertTrue(team.getTeamId() == 1L);
    }

    @Test
    void testSetTeamId() {
        Team team = new Team();
        team.setTeamId(1L);
        assertTrue(team.getTeamId() == 1L);
    }

    @Test
    void testGetTeamPlayers() {
        Team team = new Team();
        List<User> players = new ArrayList<>();
        team.setTeamPlayers(players);
        assertFalse(team.getTeamPlayers() == players);
    }

    @Test
    void testSetTeamPlayers() {
        Team team = new Team();
        List<User> players = new ArrayList<>();
        team.setTeamPlayers(players);
        //review
        assertFalse(team.getTeamPlayers() == players);
    }

    @Test
    void testGetTeamName() {
        Team team = new Team();
        team.setTeamName("MyTeam");
        assertTrue(team.getTeamName() == "MyTeam");
    }

    @Test
    void testSetTeamName() {
        Team team = new Team();
        team.setTeamName("MyTeam");
        assertTrue(team.getTeamName() == "MyTeam");
    }

    @Test
    void testGetGame() {
        Team team = new Team();
        Game game = new Game();
        team.setGame(game);
        assertTrue(team.getGame() == game);
    }

    @Test
    void testSetGame() {
        Team team = new Team();
        Game game = new Game();
        team.setGame(game);
        assertTrue(team.getGame() == game);
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
        Team team = new Team();
        List<Score> scores = new ArrayList<>();
        team.setScores(scores);
        assertTrue(team.getScores() == scores);
    }

    @Test
    void testEquals() {
        final boolean result = teamUnderTest.equals("o");
        assertThat(result).isFalse();
    }

    @Test
    void testHashCode() {
        final int result = teamUnderTest.hashCode();
        assertThat(result).isEqualTo(413);
    }

    @Test
    void testGetSerialVersionUID() {
        assertThat(Team.getSerialVersionUID()).isEqualTo(1L);
    }
}
