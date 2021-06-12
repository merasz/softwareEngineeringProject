package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

class GameCreationControllerTest {

    @Mock
    private GameService mockGameService;
    @Mock
    private TopicService mockTopicService;
    @Mock
    private TermsService mockTermsService;
    @Mock
    private UserService mockUserService;
    @Mock
    private TeamService mockTeamService;
    @Mock
    private SessionInfoBean mockSessionInfoBean;

    @InjectMocks
    private GameCreationController gameCreationController;

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testInit() {

        final User user = new User();
        when(mockSessionInfoBean.getCurrentUser()).thenReturn(user);
        final User user1 = new User();
        final Collection<User> users = Arrays.asList(user1);
        when(mockUserService.getAllUsers()).thenReturn(users);
        gameCreationController.init();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testInit_UserServiceReturnsNoItems() {
        final User user = new User();
        when(mockSessionInfoBean.getCurrentUser()).thenReturn(user);

        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());
        gameCreationController.init();
    }

    @Test
    void testDoCreateNewGame() {
        final User user = new User();
        when(mockSessionInfoBean.getCurrentUser()).thenReturn(user);
        gameCreationController.doCreateNewGame();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveGame() {
        when(mockTermsService.setTopic(new Topic("topicName"))).thenReturn(new Topic("topicName"));
        final User user = new User();
        final Game game = new Game();
        final User user1 = new User();
        when(mockGameService.saveGame(new Game())).thenReturn(game);
        final Team team = new Team();
        when(mockTeamService.saveTeam(new Team())).thenReturn(team);
        gameCreationController.getGame();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveGame_TermsServiceThrowsIllegalArgumentException() {
        when(mockTermsService.setTopic(new Topic("topicName"))).thenThrow(IllegalArgumentException.class);


        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Game game = new Game();
        when(mockGameService.saveGame(new Game())).thenReturn(game);
        final User user1 = new User();
        final User user3 = new User();
        final Team team = new Team();
        when(mockTeamService.saveTeam(new Team())).thenReturn(team);
        gameCreationController.doSaveGame();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetUserList() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Collection<User> users = Arrays.asList(user);
        when(mockUserService.getAllUsers()).thenReturn(users);
        gameCreationController.getGame();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetUserList_UserServiceReturnsNoItems() {
        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());
        gameCreationController.getGame();
        gameCreationController.displayInfo("Game deleted", "");

    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetNumberTeams() {
        final User user1 = new User();
        final Team team = new Team();
        final Collection<User> teams = Arrays.asList(user1);
        when(mockUserService.getAllUsers()).thenReturn(teams);
        gameCreationController.getNumberTeams();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetGame() {
        final User user1 = new User();
        final Team team = new Team();
        final Collection<Game> games = Arrays.asList();
        when(mockGameService.getAllGames()).thenReturn(games);
        gameCreationController.getGame();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetGame() {
        Game game = new Game();
        final Collection<Game> games = Arrays.asList();
        when(mockGameService.getAllGames()).thenReturn(games);
        gameCreationController.setGame(game);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetCurrentTopic() {
        final User user1 = new User();
        final Team team = new Team();
        final Topic topic = new Topic();
        final Collection<Topic> topics= Arrays.asList();
        gameCreationController.getCurrentTopic();

    }
    @Test
    void testSetCurrentTopic() {
        final User user = new User();
        final Team team = new Team();
        final Topic topic = new Topic();
        gameCreationController.setCurrentTopic(topic);
    }

    @Test
    void testSetNumberTeams() {
        final User user = new User();
        final Team team = new Team();
        final Topic topic = new Topic();
        int numberTeams= 2;
        gameCreationController.setNumberTeams(numberTeams);
    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testdoDeleteGame() {
        Game game = new Game();
        final Collection<Game> games2 = Arrays.asList(game);
        when(mockGameService.getAllGames()).thenReturn(games2);
        gameCreationController.doDeleteGame();
    }



}

