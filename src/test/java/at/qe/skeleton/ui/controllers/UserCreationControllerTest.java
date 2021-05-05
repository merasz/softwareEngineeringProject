package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.controllers.demo.UserStatusController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCreationControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private UserStatusController mockUserStatusController;

    @InjectMocks
    private UserCreationController userCreationControllerUnderTest;

    @Test
    void testInit() {
        userCreationControllerUnderTest.init();

    }

    @Test
    void testDoCreateNewUser() {
        userCreationControllerUnderTest.doCreateNewUser();
    }

    @Test
    void testCreateUser() {
        userCreationControllerUnderTest.createUser();
    }

    //@Test
    void testDoReloadUser() {
        final User user = new User();
        when(mockUserService.loadUser("username")).thenReturn(user);
        userCreationControllerUnderTest.doReloadUser();
    }

    //@Test
    void testDoSaveUser() {
        when(mockUserService.isUsernameAlreadyTaken(new User())).thenReturn(false);
        final User user = new User();
        when(mockUserService.saveUser(new User())).thenReturn(user);
        userCreationControllerUnderTest.doSaveUser();
        verify(mockUserStatusController).addUserStatus(new User());
    }

    //@Test
    void testGetListRoles() {
        final List<UserRole> result = userCreationControllerUnderTest.getListRoles();
        assertThat(result).isEqualTo(Arrays.asList(UserRole.ADMIN));
    }
}