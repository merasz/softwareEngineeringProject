package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private TeamService mockTeamService;
    @Mock
    private GameService mockGameService;
    @Mock
    private TeamListController mockTeamListController;
    @Mock
    private PlayerListController mockPlayerListController;

    @InjectMocks
    private TeamController teamControllerUnderTest;

    @Test
    void testDoSetTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        teamControllerUnderTest.doSetTeam(game);

    }

    @Test
    void testDoSaveTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        final Team team = new Team(game);
        when(mockTeamService.saveTeam(new Team(new Game()))).thenReturn(team);

        final Game game1 = new Game();
        final Raspberry raspberry1 = new Raspberry();
        game1.setRaspberry(raspberry1);
        when(mockGameService.saveGame(new Game())).thenReturn(game1);

        teamControllerUnderTest.doSaveTeam();

        verify(mockTeamListController).setGame(new Game());
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveUserToTeam() {

        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        final Team team = new Team(game);
        when(mockTeamService.savePlayerToTeam(new Team(new Game()), new User())).thenReturn(team);

        final Game game1 = new Game();
        final Raspberry raspberry1 = new Raspberry();
        game1.setRaspberry(raspberry1);
        when(mockGameService.reloadGame(new Game())).thenReturn(game1);

        teamControllerUnderTest.doSaveUserToTeam();

        verify(mockPlayerListController).setTeam(new Team(new Game()));
    }

    @Test
    void testAddPlayerDialog() {
        teamControllerUnderTest.addPlayerDialog();

    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetAssignablePlayers() {

        final User user = new User();
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        final Raspberry raspberry1 = new Raspberry();
        final List<User> expectedResult = Arrays.asList(user);

        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEnabled(false);
        final Game game1 = new Game();
        game1.setScoreToWin(0);
        game1.setActive(false);
        game1.setTeamList(Arrays.asList(new Team(new Game())));
        game1.setCountPlayers(0);
        final Raspberry raspberry2 = new Raspberry();
        user1.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry3 = new Raspberry();
        final List<User> users = Arrays.asList(user1);
        when(mockPlayerListController.getAssignablePlayers(new Game())).thenReturn(users);

        final List<User> result = teamControllerUnderTest.getAssignablePlayers();


        assertThat(result).isEqualTo(expectedResult);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetAssignablePlayers_PlayerListControllerReturnsNoItems() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry1 = new Raspberry();
        final List<User> expectedResult = Arrays.asList(user);
        when(mockPlayerListController.getAssignablePlayers(new Game())).thenReturn(Collections.emptyList());
        final List<User> result = teamControllerUnderTest.getAssignablePlayers();

        assertThat(result).isEqualTo(expectedResult);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoDeletePlayer() {

        final Game game = new Game();
        game.setCountPlayers(0);
        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        when(mockTeamService.deletePlayerFromTeam(new Team(new Game()), new User())).thenReturn(team);

        final Game game1 = new Game();
        game1.setCountPlayers(0);
        final Raspberry raspberry1 = new Raspberry();
        raspberry1.setRaspberryId(0);
        when(mockGameService.reloadGame(new Game())).thenReturn(game1);
        teamControllerUnderTest.doDeletePlayer();
        verify(mockPlayerListController).setTeam(new Team(new Game()));
    }

    @Test
    void testDoClearTeam() {
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        final Team team = new Team(game);
        when(mockTeamService.saveTeam(new Team(new Game()))).thenReturn(team);
        final Game game1 = new Game();
        game1.setScoreToWin(0);
        game1.setCountPlayers(0);
        final Raspberry raspberry1 = new Raspberry();
        raspberry1.setRaspberryId(0);
        game1.setRaspberry(raspberry1);
        when(mockGameService.saveGame(new Game())).thenReturn(game1);

        teamControllerUnderTest.doClearTeam();

        verify(mockTeamListController).setGame(new Game());
    }

    @Test
    void testGetTeamByPlayerAndGame() {
        final User user = new User();
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        game.setRaspberry(raspberry);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry1 = new Raspberry();

        final Game game1 = new Game();
        game1.setCountPlayers(0);
        final Raspberry raspberry2 = new Raspberry();
        game1.setRaspberry(raspberry2);

        final Game game2 = new Game();
        final Raspberry raspberry3 = new Raspberry();
        game2.setRaspberry(raspberry3);
        final Team expectedResult = new Team(game2);
        final Game game3 = new Game();
        game3.setScoreToWin(0);
        final Raspberry raspberry4 = new Raspberry();
        game3.setRaspberry(raspberry4);
        final Team team = new Team(game3);
        when(mockTeamService.getTeamByPlayerAndGame(new User(), new Game())).thenReturn(team);
        final Team result = teamControllerUnderTest.getTeamByPlayerAndGame(user, game1);
        assertThat(result).isEqualTo(expectedResult);
    }
}
