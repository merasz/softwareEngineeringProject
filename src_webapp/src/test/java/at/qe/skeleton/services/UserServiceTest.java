package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.UserRepository;
import at.qe.skeleton.ui.controllers.demo.ChatManagerController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;

import at.qe.skeleton.services.UserService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Some very basic tests for {@link UserService}.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
//@SpringBootTest
//@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ScoreRepository mockScoreRepository;
    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private ChatManagerController mockChatManagerController;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDatainitialization() {
        Assertions.assertEquals(4, userService.getAllUsers().size(), "Insufficient amount of users initialized for test data source");
        for (User user : userService.getAllUsers()) {
            if ("admin".equals(user.getUsername())) {
                Assertions.assertTrue(user.getRoles().contains(UserRole.ADMIN), "User \"" + user + "\" does not have role ADMIN");
                Assertions.assertNotNull(user.getCreateUser(), "User \"" + user + "\" does not have a createUser defined");
                Assertions.assertNotNull(user.getCreateDate(), "User \"" + user + "\" does not have a createDate defined");
                Assertions.assertNull(user.getUpdateUser(), "User \"" + user + "\" has a updateUser defined");
                Assertions.assertNull(user.getUpdateDate(), "User \"" + user + "\" has a updateDate defined");
            } else if ("user1".equals(user.getUsername())) {
                Assertions.assertTrue(user.getRoles().contains(UserRole.GAME_MANAGER), "User \"" + user + "\" does not have role GAME MANAGER");
                Assertions.assertNotNull(user.getCreateUser(), "User \"" + user + "\" does not have a createUser defined");
                Assertions.assertNotNull(user.getCreateDate(), "User \"" + user + "\" does not have a createDate defined");
                Assertions.assertNull(user.getUpdateUser(), "User \"" + user + "\" has a updateUser defined");
                Assertions.assertNull(user.getUpdateDate(), "User \"" + user +"\" has a updateDate defined");
            } else if ("user2".equals(user.getUsername())) {
                Assertions.assertTrue(user.getRoles().contains(UserRole.PLAYER), "User \"" + user + "\" does not have role PLAYER");
                Assertions.assertNotNull(user.getCreateUser(), "User \"" + user + "\" does not have a createUser defined");
                Assertions.assertNotNull(user.getCreateDate(), "User \"" + user + "\" does not have a createDate defined");
                Assertions.assertNull(user.getUpdateUser(), "User \"" + user + "\" has a updateUser defined");
                Assertions.assertNull(user.getUpdateDate(), "User \"" + user + "\" has a updateDate defined");
            } else  if ("elvis".equals(user.getUsername())) {
                Assertions.assertTrue(user.getRoles().contains(UserRole.ADMIN), "User \"" + user + "\" does not have role ADMIN");
                Assertions.assertNotNull(user.getCreateUser(), "User \"" + user + "\" does not have a createUser defined");
                Assertions.assertNotNull(user.getCreateDate(), "User \"" + user + "\" does not have a createDate defined");
                Assertions.assertNull(user.getUpdateUser(), "User \"" + user + "\" has a updateUser defined");
                Assertions.assertNull(user.getUpdateDate(), "User \"" + user + "\" has a updateDate defined");
            } else {
                Assertions.fail("Unknown user \"" + user.getUsername() + "\" loaded from test data source via UserService.getAllUsers");
            }
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteUser() {
        String username = "user1";
        User adminUser = userService.loadUser("admin");
        Assertions.assertNotNull(adminUser, "Admin user could not be loaded from test data source");
        User toBeDeletedUser = userService.loadUser(username);
        Assertions.assertNotNull(toBeDeletedUser, "User \"" + username + "\" could not be loaded from test data source");

        userService.deleteUser(toBeDeletedUser);

        Assertions.assertEquals(3, userService.getAllUsers().size(), "No user has been deleted after calling UserService.deleteUser");
        User deletedUser = userService.loadUser(username);
        Assertions.assertNull(deletedUser, "Deleted User \"" + username + "\" could still be loaded from test data source via UserService.loadUser");

        for (User remainingUser : userService.getAllUsers()) {
            Assertions.assertNotEquals(toBeDeletedUser.getUsername(), remainingUser.getUsername(), "Deleted User \"" + username + "\" could still be loaded from test data source via UserService.getAllUsers");
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdateUser() {
        String username = "user1";
        User adminUser = userService.loadUser("admin");
        Assertions.assertNotNull(adminUser, "Admin user could not be loaded from test data source");
        User toBeSavedUser = userService.loadUser(username);
        Assertions.assertNotNull(toBeSavedUser, "User \"" + username + "\" could not be loaded from test data source");

        Assertions.assertNull(toBeSavedUser.getUpdateUser(), "User \"" + username + "\" has a updateUser defined");
        Assertions.assertNull(toBeSavedUser.getUpdateDate(), "User \"" + username + "\" has a updateDate defined");

        userService.saveUser(toBeSavedUser);

        User freshlyLoadedUser = userService.loadUser("user1");
        Assertions.assertNotNull(freshlyLoadedUser, "User \"" + username + "\" could not be loaded from test data source after being saved");
        Assertions.assertNotNull(freshlyLoadedUser.getUpdateUser(), "User \"" + username + "\" does not have a updateUser defined after being saved");
        Assertions.assertEquals(adminUser, freshlyLoadedUser.getUpdateUser(), "User \"" + username + "\" has wrong updateUser set");
        Assertions.assertNotNull(freshlyLoadedUser.getUpdateDate(), "User \"" + username + "\" does not have a updateDate defined after being saved");
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateUser() {
        User adminUser = userService.loadUser("admin");
        Assertions.assertNotNull(adminUser, "Admin user could not be loaded from test data source");

        String username = "newuser";
        String password = "passwd";
        User toBeCreatedUser = new User();
        toBeCreatedUser.setUsername(username);
        toBeCreatedUser.setPassword(password);
        toBeCreatedUser.setEnabled(true);
        toBeCreatedUser.setRoles(Sets.newSet(UserRole.PLAYER, UserRole.GAME_MANAGER));
        userService.saveUser(toBeCreatedUser);

        User freshlyCreatedUser = userService.loadUser(username);
        Assertions.assertNotNull(freshlyCreatedUser, "New user could not be loaded from test data source after being saved");
        Assertions.assertEquals(username, freshlyCreatedUser.getUsername(), "New user could not be loaded from test data source after being saved");
        Assertions.assertTrue(freshlyCreatedUser.getRoles().contains(UserRole.GAME_MANAGER), "User \"" + username + "\" does not have role GAME MANAGER");
        Assertions.assertTrue(freshlyCreatedUser.getRoles().contains(UserRole.PLAYER), "User \"" + username + "\" does not have role PLAYER");
        Assertions.assertNotNull(freshlyCreatedUser.getCreateUser(), "User \"" + username + "\" does not have a createUser defined after being saved");
        Assertions.assertEquals(adminUser, freshlyCreatedUser.getCreateUser(), "User \"" + username + "\" has wrong createUser set");
        Assertions.assertNotNull(freshlyCreatedUser.getCreateDate(), "User \"" + username + "\" does not have a createDate defined after being saved");
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testExceptionForEmptyUsername() {
//        Assertions.assertThrows(org.springframework.orm.jpa.JpaSystemException.class, () -> {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            User adminUser = userService.loadUser("admin");
            Assertions.assertNotNull(adminUser, "Admin user could not be loaded from test data source");

            User toBeCreatedUser = new User();
            userService.saveUser(toBeCreatedUser);
        });
    }

    @Test
    public void testUnauthenticateddLoadUsers() {
//        Assertions.assertThrows(org.springframework.security.authentication.AuthenticationCredentialsNotFoundException.class, () -> {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            for (User user : userService.getAllUsers()) {
                Assertions.fail("Call to userService.getAllUsers should not work without proper authorization");
            }
        });
    }

    @Test
    @WithMockUser(username = "user", authorities = {"EMPLOYEE"})
    public void testUnauthorizedLoadUsers() {
//        Assertions.assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            for (User user : userService.getAllUsers()) {
                Assertions.fail("Call to userService.getAllUsers should not work without proper authorization");
            }
        });
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedLoadUser() {
//        Assertions.assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            User user = userService.loadUser("admin");
            Assertions.fail("Call to userService.loadUser should not work without proper authorization for other users than the authenticated one");
        });
    }

    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testAuthorizedLoadUser() {
        String username = "user1";
        User user = userService.loadUser(username);
        Assertions.assertEquals(username, user.getUsername(), "Call to userService.loadUser returned wrong user");
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedSaveUser() {
//        Assertions.assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            String username = "user1";
            User user = userService.loadUser(username);
            Assertions.assertEquals(username, user.getUsername(), "Call to userService.loadUser returned wrong user");
            userService.saveUser(user);
        });
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedDeleteUser() {
//        Assertions.assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            User user = userService.loadUser("user1");
            Assertions.assertEquals("user1", user.getUsername(), "Call to userService.loadUser returned wrong user");
            userService.deleteUser(user);
        });
    }

    @Test
    void testGetAllUsers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAll()).thenReturn(users);
        final Collection<User> result = userServiceUnderTest.getAllUsers();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testLoadUser() {
        final User expectedResult = new User();
        expectedResult.setUsername("username");
        final User user = new User();
        when(mockUserRepository.findFirstByUsername("username")).thenReturn(user);
        final User result = userServiceUnderTest.loadUser("username");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveUser() {
        final User user = new User();
        user.setUsername("username");
        final User expectedResult = new User();
        expectedResult.setUsername("username");
        final User user1 = new User();
        when(mockUserRepository.findFirstByUsername("username")).thenReturn(user1);
        final User user2 = new User();
        user2.setUsername("username");
        when(mockUserRepository.save(new User())).thenReturn(user2);
        final User result = userServiceUnderTest.saveUser(user);
        assertThat(result).isEqualTo(expectedResult);
    }
/*
    @Test
    void testDeleteUser() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final User user1 = new User();
        final Set<User> users = new HashSet<>(Arrays.asList(user1));
        when(mockChatManagerController.getPossibleRecipients()).thenReturn(users);
        final User user2 = new User();
        user2.setUsername("username");
        user2.setPassword("password");
        final List<User> users1 = Arrays.asList(user2);
        when(mockUserRepository.findByRole(UserRole.ADMIN)).thenReturn(users1);
        final Game game1 = new Game();
        final Game game2 = new Game();
        final List<Score> scores = Arrays.asList(new Score(0, 0L, new Team(game1), game2));
        when(mockScoreRepository.findAllByUser(new User())).thenReturn(scores);
        final Game game = new Game();
        final Team team = new Team(game);
        when(mockTeamRepository.save(new Team(new Game()))).thenReturn(team);
        userServiceUnderTest.deleteUser(user);
        verify(mockScoreRepository).delete(any(Score.class));
        verify(mockUserRepository).delete(new User());
    }

 */

    @Test
    void testDeleteUser_ChatManagerControllerReturnsNoItems() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Game game = new Game();
        user.setTeam(Arrays.asList(new Team(game)));
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        when(mockChatManagerController.getPossibleRecipients()).thenReturn(Collections.emptySet());
        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEnabled(false);
        final Game game1 = new Game();
        user1.setTeam(Arrays.asList(new Team(game1)));
        user1.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final List<User> users = Arrays.asList(user1);
        when(mockUserRepository.findByRole(UserRole.ADMIN)).thenReturn(users);
        final Game game2 = new Game();
        final Game game3 = new Game();
        final List<Score> scores = Arrays.asList(new Score(0, 0L, new Team(game2), game3));
        when(mockScoreRepository.findAllByUser(new User())).thenReturn(scores);
        final Game game4 = new Game();
        final Team team = new Team(game4);
        when(mockTeamRepository.save(new Team(new Game()))).thenReturn(team);
        userServiceUnderTest.deleteUser(user);
        verify(mockScoreRepository).delete(any(Score.class));
        verify(mockUserRepository).delete(new User());
    }

    @Test
    void testDeleteUser_ThrowsIllegalArgumentException() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final User user1 = new User();
        user1.setUsername("username");
        user1.setPassword("password");
        final Set<User> users = new HashSet<>(Arrays.asList(user1));
        when(mockChatManagerController.getPossibleRecipients()).thenReturn(users);
        final User user2 = new User();
        user2.setUsername("username");
        user2.setPassword("password");
        final List<User> users1 = Arrays.asList(user2);
        when(mockUserRepository.findByRole(UserRole.ADMIN)).thenReturn(users1);
        final Game game1 = new Game();
        final Game game2 = new Game();
        final List<Score> scores = Arrays.asList(new Score(0, 0L, new Team(game1), game2));
        when(mockScoreRepository.findAllByUser(new User())).thenReturn(scores);
        final Game game3 = new Game();
        final Team team = new Team(game3);
        when(mockTeamRepository.save(new Team(new Game()))).thenReturn(team);
        assertThatThrownBy(() -> userServiceUnderTest.deleteUser(user)).isInstanceOf(IllegalArgumentException.class);
        verify(mockScoreRepository).delete(any(Score.class));
        verify(mockUserRepository).delete(new User());
    }

    @Test
    void testGetAllAdmins() {
        final User user = new User();
        user.setUsername("username");
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN)));
        final Collection<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAllAdmins()).thenReturn(users);
        final Collection<User> result = userServiceUnderTest.getAllAdmins();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllManagers() {
        final User user = new User();
        user.setUsername("username");
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.GAME_MANAGER)));
        final Collection<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAllManagers()).thenReturn(users);
        final Collection<User> result = userServiceUnderTest.getAllAdmins();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllManagers_UserRepositoryReturnsNoItems() {
        final User user = new User();
        user.setUsername("username");
        final Collection<User> expectedResult = Arrays.asList(user);
        when(mockUserRepository.findAllManagers()).thenReturn(Collections.emptyList());
        final Collection<User> result = userServiceUnderTest.getAllManagers();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllPlayers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        final User user1 = new User();
        final List<User> users = Arrays.asList(user1);
        when(mockUserRepository.findAllPlayers()).thenReturn(users);
        final Collection<User> result = userServiceUnderTest.getAllPlayers();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUserByTeam() {
        final Game game = new Game();
        final Team team = new Team(game);
        final User user = new User();
        final List<User> expectedResult = Arrays.asList(user);
        final User user1 = new User();
        user1.setUsername("username");
        final List<User> users = Arrays.asList(user1);
        when(mockUserRepository.findAllPlayersByTeam(new Team(new Game()))).thenReturn(users);
        final List<User> result = userServiceUnderTest.getUserByTeam(team);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testIsUsernameAlreadyTaken() {
        final User user = new User();
        user.setUsername("username");
        final User user1 = new User();
        user1.setUsername("username");
        final List<User> users = Arrays.asList(user1);
        when(mockUserRepository.findAll()).thenReturn(users);
        final boolean result = userServiceUnderTest.isUsernameAlreadyTaken(user);
        assertThat(result).isTrue();
    }

    @Test
    void testGetUserByRaspberry() {
        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        final User user = new User();
        user.setUsername("username");
        final List<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAllByRaspberry(any(Raspberry.class))).thenReturn(users);
        final List<User> result = userServiceUnderTest.getUserByRaspberry(raspberry);
        assertThat(result).isEqualTo(expectedResult);
    }
}