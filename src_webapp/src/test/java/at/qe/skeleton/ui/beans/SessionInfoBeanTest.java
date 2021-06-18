package at.qe.skeleton.ui.beans;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SessionInfoBeanTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private SessionInfoBean sessionInfoBeanUnderTest;

    @Test
    void testGetCurrentUserName() {
        final String result = sessionInfoBeanUnderTest.getCurrentUserName();
        assertThat(result).isEqualTo("");
    }

    @Test
    void testGetCurrentUserRoles() {
        final String result = sessionInfoBeanUnderTest.getCurrentUserRoles();
        assertThat(result).isEqualTo("");
    }

    @Test
    void testIsLoggedIn() {
        final boolean result = sessionInfoBeanUnderTest.isLoggedIn();
        assertThat(result).isFalse();
    }

    @Test
    void testHasRole() {
        final boolean result = sessionInfoBeanUnderTest.hasRole("role");
        assertThat(result).isFalse();
    }

    @Test
    void testGetCurrentGame() {
        Game game = new Game();
        sessionInfoBeanUnderTest.setCurrentGame(game);
        assertThat(sessionInfoBeanUnderTest.getCurrentGame() == game);
    }

    @Test
    void testSetCurrentGame() {
        Game game = new Game();
        sessionInfoBeanUnderTest.setCurrentGame(game);
        assertThat(sessionInfoBeanUnderTest.getCurrentGame() == game);
    }
}
