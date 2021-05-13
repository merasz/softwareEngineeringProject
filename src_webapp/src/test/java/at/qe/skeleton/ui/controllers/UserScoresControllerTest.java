package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.services.UserStatsService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class UserScoresControllerTest {

    private UserScoresController userScoresController;
    private UserStatsService userStatsService;
    private TeamService teamService;
    private SessionInfoBean sessionInfoBean;
    private UserService userService;

    private List<User> mockUsers;
    private List<Raspberry> mockRasp;
    private List<Game> mockGames;
    private List<Team> mockTeam;
    private Topic topic;
    private List<Score> mockScore;

    @Before
    public void setup() {
        userScoresController = mock(UserScoresController.class);
        userStatsService = mock(UserStatsService.class);
        teamService = mock(TeamService.class);
        sessionInfoBean = mock(SessionInfoBean.class);
        userService = mock(UserService.class);

        setMockRasp();
        setMockUsers();
        topic = new Topic();
        topic.setTopicName("Geo");
        topic.setCreateDate(new Date(System.currentTimeMillis()));
        setMockGames();
        setMockTeam();
        setMockScore();
        setMockRoles();
    }

    private void setMockRoles() {
        Set<UserRole> userRole = new HashSet<>();
        userRole.add(UserRole.PLAYER);
        mockUsers.get(0).setRoles((userRole));
    }

    @Test
    public void testGetBestScoresForUser() throws InterruptedException {
        userService = new UserService();
        userService.saveUser(mockUsers.get(0));
        assertEquals(mockUsers.get(0), userService.loadUser("user1"));

    }

    private void setMockGames() {
        Game game1 = new Game();
        game1.setActive(true);
        game1.setCountPlayers(4);
        game1.setEndTime(new Date(System.currentTimeMillis()));
        game1.setGameName("mytestgame1");
        game1.setScoreToWin(12);
        game1.setStartTime(new Date(System.currentTimeMillis()));
        game1.setTeamSize(2);
        game1.setRaspberry(mockRasp.get(0));
        game1.setTopic(topic);

        mockGames = new ArrayList<>();
        mockGames.add(game1);
    }


    private void setMockTeam() {
        Team team1 = new Team();
        Team team2 = new Team();

        mockTeam = new ArrayList<>();
        team1.setTeamName("team1");
        team1.setGame(mockGames.get(0));
        List<User> myTestUsers = new ArrayList<>();
        myTestUsers.add(mockUsers.get(0));
        myTestUsers.add(mockUsers.get(1));
        team1.setTeamPlayers(myTestUsers);
        team1.setGame(mockGames.get(0));
        mockTeam.add(team1);
        myTestUsers.clear();
        myTestUsers.add(mockUsers.get(2));
        myTestUsers.add(mockUsers.get(3));
        team2.setTeamPlayers(myTestUsers);
        team2.setTeamName("team2");
        mockTeam.add(team2);
    }

    private void setMockRasp() {
        Raspberry rasp1 = new Raspberry();
        rasp1.setApiKey("abcdefg");
        rasp1.setHostname("raspi");
        rasp1.setInUse(true);
        rasp1.setIpAddress("192.168.0.1");
        mockRasp = new ArrayList<>();
        mockRasp.add(rasp1);
    }

    private void setMockUsers() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        user1.setUsername("user1");
        user1.setCreateDate(new Date(System.currentTimeMillis()));
        user1.setEnabled(true);
        user1.setPassword("passwd");

        user2.setUsername("user2");
        user2.setCreateDate(new Date(System.currentTimeMillis()));
        user2.setEnabled(true);
        user2.setPassword("passwd");

        user3.setUsername("user3");
        user3.setCreateDate(new Date(System.currentTimeMillis()));
        user3.setEnabled(true);
        user3.setPassword("passwd");

        user4.setUsername("user4");
        user4.setCreateDate(new Date(System.currentTimeMillis()));
        user4.setEnabled(true);
        user4.setPassword("passwd");

        mockUsers = new ArrayList<>();
        mockUsers.add(user1);
        mockUsers.add(user2);
        mockUsers.add(user3);
        mockUsers.add(user4);
    }

    private void setMockScore() {
        mockScore = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Score score1 = new Score();
            score1.setTotalRoundScore(3);
            score1.setGame(mockGames.get(0));
            if(i%2 == 0)
                score1.setTeam(mockTeam.get(0));
            else
                score1.setTeam(mockTeam.get(0));
            score1.setUser(mockUsers.get(0));
            mockScore = new ArrayList<>();
            mockScore.add(score1);
        }

    }

}
