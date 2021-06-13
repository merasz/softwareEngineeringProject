package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.ScoreService;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamListControllerTest {

    @Mock
    private TeamService teamService;
    @Mock
    private UserService mockUserService;
    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private TeamListController teamListController;

    @Test
    void testGetTeams() {
        final User user = new User();
        user.setUsername("username");
        final Collection<Team> expectedResult = Arrays.asList(new Team());
        final User user5 = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(teamService.getAllTeams()).thenReturn(Arrays.asList());
        final Collection<Team> result = teamListController.getTeams();
        assertThat(result).isNotEqualTo(expectedResult);
    }

    @Test
    void testGetTeams_TeamServiceReturnsNoItems() {
        final User user = new User();
        final User user2 = new User();
        final Collection<Team> expectedResult = Arrays.asList(new Team());
        when(teamService.getAllTeams()).thenReturn(Collections.emptyList());
        final Collection<Team> result = teamListController.getTeams();
        assertThat(result).isNotEqualTo(expectedResult);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTeamScores() {
        final User user = new User();
        final List<Score> scores = Arrays.asList(new Score());
        when(scoreService.getScoresForTeams(new Game())).thenReturn(scores);
        final Collection<Score> result = teamListController.getTeamScores();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTeamScores_ScoreServiceReturnsNoItems() {
        when(scoreService.getScoresForTeams(new Game())).thenReturn(Collections.emptyList());
        final Collection<Score> result = teamListController.getTeamScores();

    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTeamsByGame() {
        final User user = new User();
        final User user5 = new User();
        user5.setUsername("username");
        user5.setPassword("password");
        user5.setEnabled(false);

        final List<Team> teams = Arrays.asList();
        when(teamService.getTeamsByGame(new Game())).thenReturn(teams);

        final Collection<Team> result = teamListController.getTeamsByGame();

        assertThat(result).isEqualTo(teams);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTeamsByGame_TeamServiceReturnsNoItems() {
        final User user = new User();
        final User user1 = new User();
        final User user2 = new User();
        final Collection<Team> expectedResult = Arrays.asList(new Team());
        when(teamService.getTeamsByGame(new Game())).thenReturn(Collections.emptyList());
        final Collection<Team> result = teamListController.getTeamsByGame();
        assertThat(result).isNotEqualTo(expectedResult);
    }

    @Test
    void testDoSetGame() {
        final User user = new User();
        final Game game = new Game();
        teamListController.doSetGame(game);
    }
}