package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerListControllerTest {
    @Mock
    UserService userService;
    @Mock
    SessionInfoBean sessionInfoBean;

    @InjectMocks
    private PlayerListController playerListController;

    @BeforeEach
    void setUp() throws Exception {
        playerListController = new PlayerListController();
        playerListController.userService = mock(UserService.class);
    }
    @Test
    void testMio(){
        Team team = new Team();
        List<User> users = new ArrayList<>();
        assertThat(team).isEqualTo(null);

    }
    /*@Test
    void  (){
        Game game = new Game();
        Team team = new Team();
        List<User> players= new ArrayList<>();
            Raspberry raspberry = sessionInfoBean.getCurrentUser().getRaspberry();

            when(game == null).thenReturn(true);
                    doReturn(userService.getUserByRaspberry(raspberry));
            assertThat(game).isEqualTo(null);
            List<User> playersInGame = game.getTeamList().stream().flatMap(t -> t.getTeamPlayers().stream()).collect(Collectors.toList());
            userService.getUserByRaspberry(raspberry).stream().filter(u -> !playersInGame.contains(u)).collect(Collectors.toList());
            playerListController.getAssignablePlayers(game);
            playerListController.setTeam(team);
        }*/


    /*@Test
    void testGetPlayerByTeam() {
        final User user = new User();
        final Team team = new Team();
        final List<User> expectedResult = new ArrayList<>();
        assertThat(team == null).isFalse();
        when(team != null).thenReturn(true);
        team.setTeamPlayers(expectedResult);
        team.getTeamPlayers();
        playerListController.getPlayerByTeam();


    }*/



    @Test
    void testDoSetTeam() {
        final User user = new User();
        final Team team = new Team();
        playerListController.doSetTeam(team);
    }

    @Test
    void testGetTeam() {
        final User user = new User();
        final Team team = new Team();
        playerListController.getTeam();
    }

    @Test
    void testSetTeam() {
        final User user = new User();
        final Team team = new Team();
        playerListController.setTeam(team);
    }
}

