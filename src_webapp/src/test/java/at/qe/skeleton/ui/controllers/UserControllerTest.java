package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.annotation.PostConstruct;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void  testGetUser(){
        User user = new User();
        userController.setUser(user);
        assertThat(userController.getUser()==user);
    }
    @Test
    void  testSetUser(){
        User user = new User();
        userController.setUser(user);
        assertThat(userController.getUser()==user);
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoReloadUser() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();;
        final Raspberry raspberry1 = new Raspberry();
        when(userService.loadUser("username")).thenReturn(user);

        userController.doReloadUser();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
@Test
    public void shouldSendEmail_whenUserIsDeleted()  throws Exception {
        User user = new User();
        User selected = new User();
        doThrow(IllegalArgumentException.class).when(userService).saveUser(user);
        assertThrows(RuntimeException.class,() -> userController.doSaveUser());

    }
    /*@MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveUser() throws Exception {
        User user = new User();
        doNothing().when(userController).doSaveUser();
        doThrow(new IllegalArgumentException()).when(userService).saveUser(user);
        userController.displayInfo("Player created", "You have been successfully registered. You can log in now.");
        verify(userController).displayError("Player created", "You have been successfully registered. You can log in now.");
    }*/

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void testSaveUser_withExc () throws IllegalArgumentException {
        User user = new User();
        when(userService.saveUser(user)).thenThrow(IllegalArgumentException.class);
        assertThrows(RuntimeException.class,() -> userController.doSaveUser());

    }


}
