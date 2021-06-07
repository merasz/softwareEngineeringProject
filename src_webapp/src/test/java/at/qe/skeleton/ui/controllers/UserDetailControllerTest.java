package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private UserListController userListController;
    @Mock
    private SessionInfoBean sessionInfoBean;

    @InjectMocks
    private UserDetailController userDetailController;

    @BeforeEach
    public void testInit() {
        final User user = new User();
        final List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        userDetailController.init();
    }

    @Test
    //@WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testDeleteUser() {
        int previousSize = userService.getAllUsers().size();

        User user = userService.loadUser("Alex");
        verify(userService).deleteUser(new User());
        userListController.getAllPlayers();

        try {
            userDetailController.doDeleteUser();
        } catch (NullPointerException e) {
            assertThat(previousSize - 1).isEqualTo(userService.getAllUsers().size());
        }
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void testDoReloadUser() {
        final User user = new User();
        user.getUsername();
        user.setPassword("password");
        user.setEnabled(false);
        //when(userDetailController.getNewUser().compareTo(userService.loadUser(user.getUsername())));
        when(userService.loadUser("username")).thenReturn(userService.saveUser(user));
        userDetailController.doReloadUser();
    }

    @Test
    void testGetListRoles() {
        final User user = new User();
        user.getRoles();
        final List<UserRole> result = userDetailController.getListRoles();
        assertThat(result).isEqualTo(Arrays.asList(UserRole.ADMIN));
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetNewUser() {
        final User newUser = new User();
        final Team team = new Team();

        final List<User> newUsers = Arrays.asList();
        when(userService.getAllUsers()).thenReturn(newUsers);
        userDetailController.setNewUser(newUser);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetNewUser() {
        final User newUser = new User();
        final Team team = new Team();

        final List<User> newUsers = Arrays.asList();
        when(userService.getAllUsers()).thenReturn(newUsers);
        userDetailController.getNewUser();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void testSaveUser() {
        User adminUser = userService.loadUser("admin");

        User toBeCreatedUser = new User();
        toBeCreatedUser.setUsername("testUser");
        toBeCreatedUser.setPassword("passwd");
        toBeCreatedUser.setEnabled(true);
        toBeCreatedUser.setRoles(Sets.newSet(UserRole.ADMIN, UserRole.PLAYER));
        userService.saveUser(toBeCreatedUser);

        when(userService.saveUser(new User())).thenReturn(toBeCreatedUser);
    }

}