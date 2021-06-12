package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.annotation.PostConstruct;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private UserListController userListController;
    @Mock
    private SessionInfoBean sessionInfoBean;

    @Mock
    private UserDetailController userDetailController;


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetSelectedUser() {
        User selectedUser = new User();
        User user = new User();
        userDetailController.setSelectedUser(user);
        assertFalse(userDetailController.getSelectedUser() == selectedUser);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetSelectedUser() {
        User selectedUser = new User();
        User user = new User();
        userDetailController.setSelectedUser(user);
        assertFalse(userDetailController.getSelectedUser() == selectedUser);
    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void shouldSendMsg_whenUserIsDeleted()  throws Exception {
        User selected = new User();
        doNothing().when(userListController).loadUsers();
        doNothing().when(userDetailController).doDeleteUser();
        doThrow(new IllegalArgumentException()).when(userService).deleteUser(null);
        //userListController.loadUsers();
        userDetailController.setSelectedUser(selected);
        verify(userDetailController).doDeleteUser();
    }
    /*@Test
    public void shouldSendMsg_whenUserIsDeleted() throws IllegalArgumentException{
        User selected = new User();
        List<User> users
        UserService userService = Mockito.mock(UserService.class);
        when(userService.deleteUser(selected)).thenReturn(null);
    }*/

    /*public void shouldSendEmail_whenUserIsDeleted()  throws Exception {
        User user = new User();
        User selected = new User();
        doNothing().when(userDetailController).doDeleteUser();
        doThrow(new IllegalArgumentException()).when(userService).deleteUser(user);
        userDetailController.doDeleteUser();
        verify(userDetailController).doDeleteUser();
    }*/

    /*@MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void testDoReloadUser() {
        final User user = new User();
        user.getUsername();
        user.setPassword("password");
        user.setEnabled(false);

        userDetailController.doReloadUser();

    }*/

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetNewUser() {
        final User newUser = new User();
        final Team team = new Team();

        final List<User> newUsers = Arrays.asList();
        when(userService.getAllUsers()).thenReturn(newUsers);
        userDetailController.setNewUser(newUser);
        assertThat(userDetailController.getNewUser() == newUsers);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetNewUser() {
        final User newUser = new User();
        final Team team = new Team();

        final List<User> newUsers = Arrays.asList();
        when(userService.getAllUsers()).thenReturn(newUsers);
        userDetailController.getNewUser();
        assertThat(userDetailController.getNewUser() == newUsers);
    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetListRoles() {
        UserDetailController userDetailController = new UserDetailController();
        List<UserRole> userRoleList = new ArrayList<>();
        userDetailController.getListRoles();
        assertFalse(userDetailController.getListRoles() ==userRoleList );
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetPassword() {
        User user = new User();
        UserDetailController userDetailController = new UserDetailController();
        userDetailController.setPassword("passwdRandom");
        assertTrue("passwdRandom".equals(userDetailController.getPassword()));


    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetPassword() {
        User user = new User();
        UserDetailController userDetailController = new UserDetailController();
        userDetailController.setPassword("passwdRandom");
        assertTrue("passwdRandom".equals(userDetailController.getPassword()));


    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetConfirmPassword() {
        User user = new User();
        UserDetailController userDetailController = new UserDetailController();
        userDetailController.setConfirmPassword("passwdRandom2");
        assertTrue("passwdRandom2".equals(userDetailController.getConfirmPassword()));


    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetConfirmPassword() {
        User user = new User();
        UserDetailController userDetailController = new UserDetailController();
        userDetailController.setConfirmPassword("passwdRandom2");
        assertTrue("passwdRandom2".equals(userDetailController.getConfirmPassword()));


    }

}

