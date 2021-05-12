package at.qe.skeleton.model;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TeamTest {
    @Test
    public void testSetGame() {
        Team team = new Team();
        Game game = new Game();
        team.setGame(game);
        assertSame(game, team.getGame());
    }

    @Test
    public void testSetScores() {
        Team team = new Team();
        ArrayList<Score> scoreList = new ArrayList<Score>();
        team.setScores(scoreList);
        assertSame(scoreList, team.getScores());
    }
}

