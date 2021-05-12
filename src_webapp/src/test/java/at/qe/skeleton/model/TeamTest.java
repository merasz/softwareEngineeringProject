package at.qe.skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TeamTest {
    @Test
    public void testSetTeamName() {
        Team team = new Team();
        team.setTeamName("Team Name");
        assertEquals("Team Name", team.getTeamName());
    }
}

