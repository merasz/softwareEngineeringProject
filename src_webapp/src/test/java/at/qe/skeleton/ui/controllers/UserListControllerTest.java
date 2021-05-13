package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserListControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserListController userListController;

    @Test
    void testInit() {
        final User user = new User();
        final Collection<User> users = Arrays.asList(user);
        userListController.init();
    }

    @Test
    void testGetUsers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(expectedResult);
        final Collection<User> result = userService.getAllUsers();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllPlayers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        when(userService.getAllPlayers()).thenReturn(expectedResult);
        final Collection<User> result = userListController.getAllPlayers();
        assertThat(result).isEqualTo(expectedResult);
    }

}
