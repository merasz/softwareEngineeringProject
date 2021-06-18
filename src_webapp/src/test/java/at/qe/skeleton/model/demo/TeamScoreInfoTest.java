package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class TeamScoreInfoTest {

    @Mock
    private Team mockTeam;

    private TeamScoreInfo teamScoreInfoUnderTest;

    @BeforeEach
    void setUp() {
        teamScoreInfoUnderTest = new TeamScoreInfo(mockTeam, 0);
    }

    @Test
    void testTeamScoreInfo() {
        Integer score = 1;
        try {
            new TeamScoreInfo(mockTeam,score);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testGetTeam() {
        Team team = new Team();
        teamScoreInfoUnderTest.setTeam(team);
        assertThat(teamScoreInfoUnderTest.getTeam() == team);
    }

    @Test
    void testSetTeam() {
        Team team = new Team();
        teamScoreInfoUnderTest.setTeam(team);
        assertThat(teamScoreInfoUnderTest.getTeam() == team);
    }

    @Test
    void testGetScore() {
        Integer score = 1;
        teamScoreInfoUnderTest.setScore(score);
        assertThat(teamScoreInfoUnderTest.getScore() == 1);
    }
}
