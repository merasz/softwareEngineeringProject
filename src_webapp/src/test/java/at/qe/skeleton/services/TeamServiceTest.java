package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TeamServiceTest {

    private TeamService teamServiceUnderTest;

    @BeforeEach
    void setUp() {
        teamServiceUnderTest = new TeamService();
        teamServiceUnderTest.teamRepository = mock(TeamRepository.class);
    }

    @Test
    void testGetAllTeams() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        final List<Team> expectedResult = Arrays.asList(new Team(game));
        game.setRaspberry(raspberry);
        final List<Team> teams = Arrays.asList(new Team(game));
        when(teamServiceUnderTest.teamRepository.findAll()).thenReturn(teams);
        final List<Team> result = teamServiceUnderTest.getAllTeams();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTeamsByGame() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final List<Team> teams = Arrays.asList(new Team(game));
        when(teamServiceUnderTest.teamRepository.findByGame(0)).thenReturn(teams);
        final List<Team> result = teamServiceUnderTest.getTeamsByGame(game);
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        final Team expectedResult = new Team(game);
        when(teamServiceUnderTest.teamRepository.save(new Team(new Game()))).thenReturn(team);
        final Team result = teamServiceUnderTest.saveTeam(team);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSavePlayerToTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        final User player = new User();
        player.setUsername("username");
        player.setPassword("password");
        player.setEnabled(false);
        player.setTeam(Arrays.asList(new Team(game)));
        player.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        player.setCreateUser(new User());
        player.setUpdateUser(new User());
        final Team expectedResult = new Team(game);
        when(teamServiceUnderTest.teamRepository.save(new Team(new Game()))).thenReturn(team);
//        when(teamServiceUnderTest.teamRepository.findByTeamId(0L)).thenReturn(team2);
        final Team result = teamServiceUnderTest.savePlayerToTeam(team, player);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testReloadTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        final Team expectedResult = new Team(game);
        when(teamServiceUnderTest.teamRepository.findByTeamId(0L)).thenReturn(team);
        final Team result = teamServiceUnderTest.reloadTeam(team);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeletePlayerFromTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        final User tmpPlayer = new User();
        tmpPlayer.setUsername("username");
        tmpPlayer.setPassword("password");
        tmpPlayer.setEnabled(false);
        tmpPlayer.setTeam(Arrays.asList(new Team(game)));
        tmpPlayer.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Team expectedResult = new Team(game);
        when(teamServiceUnderTest.teamRepository.save(new Team(new Game()))).thenReturn(team);
        final Team result = teamServiceUnderTest.deletePlayerFromTeam(team, tmpPlayer);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        when(teamServiceUnderTest.teamRepository.save(new Team(new Game()))).thenReturn(team);
        teamServiceUnderTest.deleteTeam(team);
        verify(teamServiceUnderTest.teamRepository).delete(new Team(new Game()));
    }

    @Test
    void testGetTeamsByPlayer() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final List<Team> expectedResult = Arrays.asList(new Team(game));
        final List<Team> teams = Arrays.asList(new Team(game));
        when(teamServiceUnderTest.teamRepository.findAllByTeamPlayers(new User())).thenReturn(teams);
        final List<Team> result = teamServiceUnderTest.getTeamsByPlayer(user);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTeamByPlayerAndGame() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setGameName("gameName");
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Team expectedResult = new Team(game);
        final Team team = new Team(game);
        when(teamServiceUnderTest.teamRepository.findByTeamPlayersAndGame(new User(), new Game())).thenReturn(team);
        final Team result = teamServiceUnderTest.getTeamByPlayerAndGame(user, game);
        assertThat(result).isEqualTo(expectedResult);
    }
}
