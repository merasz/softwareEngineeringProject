package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ScoreRepository mockScoreRepository;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    public void testGetAllUsers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAll()).thenReturn(users);
        final Collection<User> result = userServiceUnderTest.getAllUsers();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testLoadUser() {
        final User expectedResult = new User();
        expectedResult.setUsername("username");
        final User user = new User();
        user.setUsername("username");
        when(mockUserRepository.findFirstByUsername("username")).thenReturn(user);
        final User result = userServiceUnderTest.loadUser("username");
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    public void testSaveUser() {
        final User user = new User();
        user.setUsername("username");
        final User expectedResult = new User();
        when(mockUserRepository.findFirstByUsername("username")).thenReturn(user);
        final User result = userServiceUnderTest.saveUser(user);
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    public void testDeleteUser() {
        final User user = new User();
        final User user1 = new User();
        final List<User> users = Arrays.asList(user1);
        when(mockUserRepository.findByRole(UserRole.ADMIN)).thenReturn(users);
        final List<Score> scores = Arrays.asList(new Score());
        when(mockScoreRepository.findAllByUser(new User())).thenReturn(scores);
        userServiceUnderTest.deleteUser(user);
        verify(mockScoreRepository).delete(any(Score.class));
        verify(mockUserRepository).delete(new User());
    }

    //@Test
    public void testCreateUser() {
        final User user = new User();
        final User expectedResult = new User();
        //when(mockUserRepository.findFirstByUsername("username")).thenReturn(user);
        when(mockUserRepository.save(new User())).thenReturn(user);
        final User result = userServiceUnderTest.createUser(user, "username", "password");
        assertThat(result).isEqualTo(expectedResult);
        //assertThatThrownBy(() -> userServiceUnderTest.createUser(user, "username", "password")).isInstanceOf(IllegalArgumentException.class);
    }

    //@Test
    void testUpdatePassword() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final User expectedResult = new User();
        expectedResult.setUsername("username");
        expectedResult.setPassword("password");
        when(mockUserRepository.findFirstByUsername("username")).thenReturn(user);
        when(mockUserRepository.save(new User())).thenReturn(user);
        final User result = userServiceUnderTest.updatePassword(user, "password", "confirm");
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testUpdateRoles() {
        final User user = new User();
        final User expectedResult = new User();
        when(mockUserRepository.save(new User())).thenReturn(user);
        final User result = userServiceUnderTest.updateRoles(user, new UserRole[]{UserRole.ADMIN});
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllAdmins() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAllAdmins()).thenReturn(users);
        final Collection<User> result = userServiceUnderTest.getAllAdmins();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllManagers() {
        final User user = new User();
        final Collection<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAllManagers()).thenReturn(users);
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
        final User user = new User();
        final Team team = new Team();
        final List<User> expectedResult = Arrays.asList(user);
        final List<User> users = Arrays.asList(user);
        when(mockUserRepository.findAllPlayersByTeam(any(Team.class))).thenReturn(users);
        final List<User> result = userServiceUnderTest.getUserByTeam(team);
        assertThat(result).isEqualTo(expectedResult);
    }
}
