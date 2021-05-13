package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private SessionInfoBean sessionInfoBean;

    @InjectMocks
    private UserDetailController userDetailController;

    @Test
    //@WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testDeleteUser() {
        int previousSize = userService.getAllUsers().size();

        User user = userService.loadUser("Ale");
        verify(userService).deleteUser(new User());
        userDetailController.setUser(user);

        try {
            userDetailController.doDeleteUser();
        } catch (NullPointerException e) {
            assertThat(previousSize - 1).isEqualTo(userService.getAllUsers().size());
        }
    }


    @Test
    public void testDoReloadUser() {
        final User user = new User();
        user.setPassword("password");
        user.setEnabled(false);
        //when(userService.loadUser("username")).thenReturn(userService.loadUser(user));
        //userDetailController.doReloadUser();
    }

    @Test
    void testGetListRoles() {
        final User user = new User();
        user.getRoles();
        final List<UserRole> result = userDetailController.getListRoles();
        assertThat(result).isEqualTo(Arrays.asList(UserRole.ADMIN));
    }



    @Test
    public void testSaveUser() {
        User adminUser = userService.loadUser("admin");

        User toBeCreatedUser = new User();
        toBeCreatedUser.setUsername("testUser");
        toBeCreatedUser.setPassword("passwd");
        toBeCreatedUser.setEnabled(true);
        toBeCreatedUser.setRoles(Sets.newSet(UserRole.ADMIN, UserRole.PLAYER));
        userService.saveUser(toBeCreatedUser);

        when(userService.saveUser(new User())).thenReturn(toBeCreatedUser);
    }

}


