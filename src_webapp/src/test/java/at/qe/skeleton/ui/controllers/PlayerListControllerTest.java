package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Assertions;
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
    void testFirst(){
        Team team = new Team();
        List<User> users = new ArrayList<>();
        assertThat(team).isNotEqualTo(null);

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

    @Test
    void testGetAssignablePlayers(){

        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            playerListController.getAssignablePlayers(new Game());
        });
    }
}

