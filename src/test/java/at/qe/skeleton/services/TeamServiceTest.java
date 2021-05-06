package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TeamServiceTest {

    private TeamService teamServiceUnderTest;

    @BeforeEach
    public void setUp() {
        teamServiceUnderTest = new TeamService();
        teamServiceUnderTest.teamRepository = mock(TeamRepository.class);
    }

    @Test
    public void testGetAllTeams() {
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(teamServiceUnderTest.teamRepository.findAll()).thenReturn(teams);
        final List<Team> result = teamServiceUnderTest.getAllTeams();
    }

    @Test
    public void testGetTeamsByGame() {
        final Game game = null;
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(teamServiceUnderTest.teamRepository.findByGame(0)).thenReturn(teams);
        //final List<Team> result = teamServiceUnderTest.getTeamsByGame(game);
    }

    @Test
    public void testSaveTeam() {
        final User user = new User();
        final Team team = new Team();
        final Team result = teamServiceUnderTest.saveTeam(team);
    }

    @Test
    public void testCreateTeam() {
        final Game game = null;
        final User user = new User();
        final Team team = new Team();
        when(teamServiceUnderTest.teamRepository.save(any(Team.class))).thenReturn(team);
        //final Team result = teamServiceUnderTest.createTeam(game);
    }

    @Test
    public void testSavePlayerToTeam() {
        final User  user = new User();
        final Team team = new Team();
        final User player = new User();
        final Team result = teamServiceUnderTest.savePlayerToTeam(team, player);
    }

    @Test
    public void testDeletePlayerFromTeam() {
        final User user = new User();
        final Team team = new Team();
        final User tmpPlayer = new User();
        //final Team result = teamServiceUnderTest.deletePlayerFromTeam(team, tmpPlayer);
    }

    @Test
    public void testDeleteTeam() {
        final User user = new User();
        final Team team = new Team();
        when(teamServiceUnderTest.teamRepository.save(any(Team.class))).thenReturn(team);
        teamServiceUnderTest.deleteTeam(team);
        verify(teamServiceUnderTest.teamRepository).delete(any(Team.class));
    }

    @Test
    public void testGetTeamsByPlayer() {
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(teamServiceUnderTest.teamRepository.findAllByTeamPlayers(new User())).thenReturn(teams);
        final List<Team> result = teamServiceUnderTest.getTeamsByPlayer(user);
    }

    @Test
    public void testIsPlayerAssignedToEnemyTeam() {
        final Game game = null;
        final User tmpPlayer = new User();
        final User user = new User();
        final List<Team> teams = Arrays.asList(new Team());
        when(teamServiceUnderTest.teamRepository.findByGame(0)).thenReturn(teams);
        //final boolean result = teamServiceUnderTest.isPlayerAssignedToEnemyTeam(game, tmpPlayer);
        //assertThat(result).isTrue();
    }
}