package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

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


    @Test
    void testDoSaveUser() {
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        final Game game = new Game();
        final Raspberry raspberry = new Raspberry();
        final Raspberry raspberry1 = new Raspberry();
        when(userService.saveUser(new User())).thenReturn(user);

        userController.doSaveUser();

    }
}
