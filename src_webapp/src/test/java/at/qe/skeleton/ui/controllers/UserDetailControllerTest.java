package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private UserListController mockUserListController;
    @Mock
    private SessionInfoBean sessionInfoBean;
    @Mock
    private UserDetailController userDetailController;


    @InjectMocks
    private UserDetailController userDetailControllerUnderTest;

    @Test
    void testInit() {

        userDetailControllerUnderTest.init();


    }

    @Test
    void testDoCreateNewUser() {
        userDetailControllerUnderTest.doCreateNewUser();

    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetSelectedUser() {
        User selectedUser = new User();
        User user = new User();
        userDetailController.setSelectedUser(user);
        assertFalse(userDetailController.getSelectedUser() == selectedUser);
        userDetailControllerUnderTest.doCreateNewUser();
    }


    @Test
    void testDoReloadUser() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setScoreToWin(0);
        game.setActive(false);
        game.setTeamList(Arrays.asList(new Team(new Game())));
        game.setTopic(new Topic("topicName"));
        game.setGameName("gameName");
        game.setCountPlayers(0);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        user.setRaspberry(raspberry);
        user.setCreateUser(new User());
        when(mockUserService.loadUser("username")).thenReturn(user);
        userDetailControllerUnderTest.doReloadUser();
    }

    @Test
    void testDoDeleteUser() {
        userDetailControllerUnderTest.doDeleteUser();
        verify(mockUserService).deleteUser(new User());
        verify(mockUserListController).loadUsers();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoDeleteUser_UserServiceThrowsIllegalArgumentException() {
        doThrow(IllegalArgumentException.class).when(mockUserService).deleteUser(new User());

        userDetailControllerUnderTest.doDeleteUser();
        verify(mockUserListController).loadUsers();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveNewUser() {
        when(mockUserService.isUsernameAlreadyTaken(new User())).thenReturn(false);
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setActive(false);
        game.setTeamList(Arrays.asList(new Team(new Game())));
        game.setTopic(new Topic("topicName"));
        game.setGameName("gameName");
        game.setCountPlayers(0);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        user.setRaspberry(raspberry);
        user.setCreateUser(new User());
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userDetailControllerUnderTest.doSaveNewUser();
        verify(mockUserListController).loadUsers();
    }

    @Test
    void testSignUp() {
        // Setup
        when(mockUserService.isUsernameAlreadyTaken(new User())).thenReturn(false);
        mockUserService.saveUser(new User());
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setScoreToWin(0);
        game.setActive(false);
        game.setTeamList(Arrays.asList(new Team(new Game())));
        game.setGameName("gameName");
        game.setCountPlayers(0);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry = new Raspberry();
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        user.setRaspberry(raspberry);
        user.setCreateUser(new User());
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userDetailControllerUnderTest.signUp();

    }

    @Test
    void testDoUpdateUser() {
        mockUserService.saveUser(new User());
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setTeamList(Arrays.asList(new Team(new Game())));
        game.setCountPlayers(0);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry = new Raspberry();
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userDetailControllerUnderTest.doUpdateUser();
        verify(mockUserListController).loadUsers();
    }

    @Test
    void testUpdatePasswordDialog() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setScoreToWin(0);
        game.setActive(false);
        game.setTeamList(Arrays.asList(new Team(new Game())));
        game.setTopic(new Topic("topicName"));
        game.setCountPlayers(0);
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Raspberry raspberry = new Raspberry();
        user.setRaspberry(raspberry);
        user.setCreateUser(new User());
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userDetailControllerUnderTest.updatePasswordDialog();
        verify(mockUserListController).loadUsers();
    }
    @Test
    void testUpdatePassword() throws IllegalArgumentException {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        game.setScoreToWin(0);
        final Raspberry raspberry = new Raspberry();
        user.setRaspberry(raspberry);
        user.setCreateUser(new User());
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userDetailControllerUnderTest.updatePasswordDialog();
        verify(mockUserListController).loadUsers();
        when(userDetailController.getPassword()).thenReturn("password");
        when(userDetailController.getPassword()).thenReturn(null);
        doThrow(IllegalArgumentException.class);

    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSaveUser() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        user.setCreateUser(new User());
        user.setUpdateUser(new User());
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userDetailControllerUnderTest.saveUser();
        verify(mockUserListController).loadUsers();
    }

    @Test
    void testGetListRoles() {
        final List<UserRole> result = userDetailControllerUnderTest.getListRoles();
        assertThat(result).isNotEqualTo(Arrays.asList(UserRole.ADMIN));
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetNewUser() {
        final User newUser = new User();
        final Team team = new Team();

        final List<User> newUsers = Arrays.asList();
        when(mockUserService.getAllUsers()).thenReturn(newUsers);
        userDetailController.setNewUser(newUser);
        assertThat(userDetailController.getNewUser() == newUsers);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetNewUser() {
        final User newUser = new User();
        final Team team = new Team();

        final List<User> newUsers = Arrays.asList();
        when(mockUserService.getAllUsers()).thenReturn(newUsers);
        userDetailController.getNewUser();
        assertThat(userDetailController.getNewUser() == newUsers);
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


