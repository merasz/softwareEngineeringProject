package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
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
class TeamPlayerTest {

    @Mock
    private User mockPlayer;
    @Mock
    private Team mockTeam;

    private TeamPlayer teamPlayerUnderTest;

    @BeforeEach
    void setUp() {
        teamPlayerUnderTest = new TeamPlayer(mockPlayer, mockTeam);
    }

    @Test
    void testTeamPlayer() {
        try {
            new TeamPlayer(mockPlayer, mockTeam);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testTeamPlayer_EmptyConstructor() {
        TeamPlayer teamPlayer = new TeamPlayer();
    }

    @Test
    void testGetTeam() {
        List<Team> teams = new ArrayList<>();
        mockPlayer.setTeam(teams);
        assertThat(teamPlayerUnderTest.getTeam() == teams);
    }

    @Test
    void testGetPlayer() {
        List<User> teamPlayers = new ArrayList<>();
        mockTeam.setTeamPlayers(teamPlayers);
        assertThat(teamPlayerUnderTest.getPlayer() == teamPlayers);
    }
}
