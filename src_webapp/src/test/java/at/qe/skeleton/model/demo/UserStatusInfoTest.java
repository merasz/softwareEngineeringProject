package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class UserStatusInfoTest {

    @Mock
    private User mockUser;

    private UserStatusInfo userStatusInfoUnderTest;

    @BeforeEach
    void setUp() {
        userStatusInfoUnderTest = new UserStatusInfo(mockUser);
    }

    @Test
    void testGetUser() {
        User user = new User();
        userStatusInfoUnderTest.setUser(user);
        assertThat(userStatusInfoUnderTest.getUser() == user);
    }

    @Test
    void testSetUser() {
        User user = new User();
        userStatusInfoUnderTest.setUser(user);
        assertThat(userStatusInfoUnderTest.getUser() == user);
    }

    @Test
    void testGetStatus() {
        UserStatus userStatus = UserStatus.ONLINE;
        userStatusInfoUnderTest.setStatus(userStatus);
        assertThat(userStatusInfoUnderTest.getStatus() == UserStatus.ONLINE);
    }
}


