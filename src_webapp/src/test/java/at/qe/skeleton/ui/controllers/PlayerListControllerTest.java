package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerListControllerTest {

    private PlayerListController playerListController;

    @BeforeEach
    void setUp() throws Exception {
        playerListController = new PlayerListController();
        playerListController.userService = mock(UserService.class);
    }

    @Test
    void testGetPlayerByTeam() {
        final User user = new User();
        final Team team = new Team();
        final List<User> expectedResult = Arrays.asList(user);
        when(playerListController.userService.getUserByTeam(any(Team.class))).thenReturn(expectedResult);
    }


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

