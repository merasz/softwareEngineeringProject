/*package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

    @Test
    void testInit() {
        final User user = new User();
        when(mockSessionInfoBean.getCurrentUser()).thenReturn(user);
        final User user1 = new User();
        final Collection<User> users = Arrays.asList(user1);
        when(mockUserService.getAllUsers()).thenReturn(users);
        gameCreationController.init();
    }

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

    @Test
    void testDoSaveGame() {
        when(mockTermsService.setTopic(new Topic("topicName"))).thenReturn(new Topic("topicName"));
        final User user = new User();
        final Game game = new Game();
        final User user1 = new User();
        when(mockGameService.saveGame(new Game())).thenReturn(game);
        final Team team = new Team();
        when(mockTeamService.saveTeam(new Team())).thenReturn(team);
        gameCreationController.doSaveGame();
    }

   // @Test
    void testDoSaveGame_TermsServiceThrowsIllegalArgumentException() {
        // Setup
        when(mockTermsService.setTopic(new Topic("topicName"))).thenThrow(IllegalArgumentException.class);

        // Configure GameService.saveGame(...).
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        final Game game = new Game();
        when(mockGameService.saveGame(new Game())).thenReturn(game);
        final User user1 = new User();
        final User user3 = new User();
        final Team team = new Team();
        when(mockTeamService.saveTeam(new Team())).thenReturn(team);
        //gameCreationController.doSaveGame();
    }

    @Test
    void testSetUserList() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Collection<User> users = Arrays.asList(user);
        when(mockUserService.getAllUsers()).thenReturn(users);
       // gameCreationController.setUserList();
    }

    @Test
    void testSetUserList_UserServiceReturnsNoItems() {
        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());
       // gameCreationController.setGame();

    }
}
*/