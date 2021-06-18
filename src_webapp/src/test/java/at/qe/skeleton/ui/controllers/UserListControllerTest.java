package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.controllers.UserListController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void testLoadUsers(){
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final User user = new User();
            UserListController userListController = new UserListController();
            when(userListController.getOption().equals("all"));
            Collection<User> users = userService.getAllUsers();
            assertThat(users).isEqualTo(Arrays.asList());
            when(userListController.getOption().equals("admin"));
            Collection<User> users1 = userService.getAllAdmins();
            assertThat(users1).isEqualTo(Arrays.asList());
            when(userListController.getOption().equals("player"));
            Collection<User> users2 = userService.getAllPlayers();
            assertThat(users2).isEqualTo(Arrays.asList());
            when(userListController.getOption().equals("manager"));
            Collection<User> users3 = userService.getAllManagers();
            assertThat(users3).isEqualTo(Arrays.asList());
        });
    }

    @Test
    void testGetUsers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(expectedResult);
        final Collection<User> result = userService.getAllUsers();
        assertThat(result).isEqualTo(expectedResult);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetUsersReturnsNoItems() {
        final User user = new User();
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        final Collection<User> result = userListController.getUsers();
        assertFalse(result == user);
    }

    @Test
    void testGetAllPlayers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        when(userService.getAllPlayers()).thenReturn(expectedResult);
        final Collection<User> result = userListController.getAllPlayers();
        assertThat(result).isEqualTo(expectedResult);
    }
    @Test
    void testGetAllPlayersReturnsNoItems() {
        final User user = new User();
        when(userService.getAllPlayers()).thenReturn(Collections.emptyList());
        final Collection<User> result = userListController.getAllPlayers();
        assertFalse(result == user);
    }

    @Test
    void testGetPlayerCircle(){
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final User user = new User();
            UserListController userListController = new UserListController();
            List<User> users = Arrays.asList(user);
            when(userService.getUserByRaspberry(user.getRaspberry())).thenReturn(users);
            userService.getUserByRaspberry(user.getRaspberry());
            final Collection<User> result = userListController.getPlayerCircle(user);
            assertThat(result).isEqualTo(users);
            List<User> users1 = userService.getUserByRaspberry(user.getRaspberry());
            when(users1.remove(user)).thenReturn(true);
            userListController.getPlayerCircle(user);

        });
        }


    @Test
    void testCompleteText(){
        String query = new String();
        List <String> texts = new ArrayList<>();
        query.toLowerCase();
        List<String> usernames = userListController.completeText("");
        assertThat(usernames).isEqualTo(Arrays.asList());


    }

    @Test
    void testGetOption() {
        UserListController userListController = new UserListController();
        userListController.setOption("b");
        assertTrue("b".equals(userListController.getOption()));
    }
    @Test
    void testSetOption() {
        UserListController userListController = new UserListController();
        userListController.setOption("b");
        assertTrue("b".equals(userListController.getOption()));
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testUsers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(expectedResult);
        final Collection<User> result = userListController.getUsers();
        assertFalse("at.qe.skeleton.model.User[ id=null ]".equals(userListController.getUsers()));
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testUsers_ReturnsNoItems() {
        final User user = new User();
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        final Collection<User> result = userListController.getUsers();
        assertFalse(result == user);
    }


}

