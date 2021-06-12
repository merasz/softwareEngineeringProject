package at.qe.skeleton.ui.controllers;


import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.GameStartService;
import static org.junit.Assert.fail;

import at.qe.skeleton.services.TopicService;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.primefaces.event.SelectEvent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class gGameControllerTest {


    @Mock
    private GameStartService gameStartService;
    @Mock
    private SessionInfoBean sessionInfoBean;

    @Mock
    private UserService userService;
    @Mock
    private TopicService topicService;
    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @Test
    void testSetUser() {
        User user = new User();
        gameController.setUser();
        assertFalse(gameController.getUser() == user);
    }
    @Test
    void testGetUser() {
        User user = new User();
        gameController.setUser();
        assertFalse(gameController.getUser() == user);
    }
    @Test
    void testGetGame() {
        Game game = new Game();
        gameController.setGame(game);
        assertTrue(gameController.getGame() == game);
    }
    @Test
    void testSetGame() {
        Game game = new Game();
        gameController.setGame(game);
        assertTrue(gameController.getGame() == game);
    }
    @Test
    void testGetUserService() {
        UserService userService1 = new UserService();
        gameController.setUserService(userService1);
        assertTrue(gameController.getUserService() == userService1);


    }
}
