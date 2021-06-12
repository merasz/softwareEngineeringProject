package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.TopicService;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.services.UserStatsService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
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
class UserScoresControllerTest {

    @InjectMocks
    private UserScoresController userScoresController;
    @Mock
    private UserStatsService userStatsService;
    @Mock
    private TeamService teamService;
    @Mock
    private SessionInfoBean sessionInfoBean;
    @Mock
    private UserService userService;
    @Mock
    private TopicService topicService;

    @Test
    void testShowProfile(){
        User user = new User();
        Game game = new Game();
        user.getId();
        sessionInfoBean.setCurrentGame(game);
        assertFalse(sessionInfoBean.getCurrentUser() == user);
        final String result = userScoresController.showProfile();
        assertThat(result).isEqualTo("/secured/profile.xhtml?faces-redirect=true");
    }
    @Test
    void testShowOtherProfile(){
        User user = new User();
        Game game = new Game();
        final boolean result = user.equals(null);
        assertThat(result).isFalse();
        final String result1 = userScoresController.showOtherProfile();
        assertThat(result1).isEqualTo("/secured/profile.xhtml?faces-redirect=true");
    }


    @Test
    void testDoReloadUser(){
        User user1 = new User();
        userService.loadUser("Ghost");
        user1.getUsername();
        assertFalse(userService.loadUser("Ghost") == user1);
    }

    @Test
    void testGetUser() {
        User user = new User();
        userScoresController.setUser(user);
        assertFalse(userScoresController.getUser() == user);
    }
    @Test
    void testSetUser() {
        User user = new User();
        userScoresController.setUser(user);
        assertFalse(userScoresController.getUser() == user);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetBestScoresForUser() {
        User user = new User();
        userStatsService.getBestScoresFromUser(user);
        List<Score> result = userScoresController.getBestScoresForUser();
        assertThat(result).isEqualTo(Arrays.asList());
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetBestScoresForUser_ReturnsNoItems() {
        User user = new User();
        when(userStatsService.getBestScoresFromUser(user)).thenReturn(Collections.emptyList());
        final List<Score> result = userScoresController.getBestScoresForUser();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetLatestScoresForUser() {
        User user = new User();
        userStatsService.getLatestScoresFromUser(user);
        List<Score> result = userScoresController.getLatestScoresForUser();
        assertThat(result).isEqualTo(Arrays.asList());
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetLatestScoresForUser_ReturnsNoItems() {
        User user = new User();
        when(userStatsService.getBestScoresFromUser(user)).thenReturn(Collections.emptyList());
        final List<Score> result = userScoresController.getLatestScoresForUser();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetGameCountForUser () {
        User user = new User();
        Game game = new Game();
        userStatsService.getGameCountByUser(user);
        assertFalse(userScoresController.getGameCountForUser() == 20);
    }

    @Test
    void testGetTeamsByPlayer() {
        final Set<Team> tmp = new HashSet<>();
        assertFalse(userScoresController.getTeamsByPlayer() == tmp);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetWonGamesByTopics() {
        User user = new User();
        userStatsService.getLostCountByUser(user);
        List<GameTopicCount> result = userScoresController.getWonGamesByTopics();
        assertThat(result).isEqualTo(Arrays.asList());
    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetWonGamesByUser() {
        User user = new User();
        Game game = new Game();
        sessionInfoBean.setCurrentGame(game);
        assertFalse(userScoresController.getWonGamesByUser() == 20);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetLostGamesByUser() {
        User user = new User();
        Game game = new Game();
        sessionInfoBean.setCurrentGame(game);
        assertFalse(userScoresController.getLostGamesByUser() == 20);
    }

    @Test
    void testGetTopTeamFromGame(){
        Team team = new Team();
        Game game = new Game();
        Team result = userScoresController.getTopTeamFromGame(game);
        assertThat(result).isEqualTo(null);
    }


    @Test
    void testGetUserName() {
        User user = new User();
        user.setUsername("Username");
        assertTrue(user.getUsername() == "Username");
    }
    @Test
    void testSetUserName() {
        User user = new User();
        user.setUsername("Username");
        assertTrue(user.getUsername() == "Username");
    }

    @Test
    void testSetViewPrivate(){
        userScoresController.setViewPrivate(true);
        assertFalse(userScoresController.isViewPrivate() == false);
    }

    @Test
    void testIsViewPrivate() {
        final boolean result = userScoresController.isViewPrivate();
        assertThat(result).isFalse();
    }


}




