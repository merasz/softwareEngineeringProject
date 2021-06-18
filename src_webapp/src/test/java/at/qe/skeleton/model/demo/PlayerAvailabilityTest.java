package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerAvailabilityTest {

    @Mock
    private User mockUser;
    @Mock
    private Game mockGame;

    private PlayerAvailability playerAvailabilityUnderTest;

    @BeforeEach
    void setUp() {
        playerAvailabilityUnderTest = new PlayerAvailability(mockUser, mockGame);
    }

    @Test
    void testGetUser() {
        playerAvailabilityUnderTest.setUser(mockUser);
        assertThat(playerAvailabilityUnderTest.getUser() == mockUser);
    }

    @Test
    void testSetUser() {
        playerAvailabilityUnderTest.setUser(mockUser);
        assertThat(playerAvailabilityUnderTest.getUser() == mockUser);
    }

    @Test
    void testGetUsername() {
        when(mockUser.getUsername()).thenReturn("result");
        final String result = playerAvailabilityUnderTest.getUsername();
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testGetGame() {
        playerAvailabilityUnderTest.setGame(mockGame);
        assertThat(playerAvailabilityUnderTest.getGame() == mockGame);
    }

    @Test
    void testSetGame() {
        playerAvailabilityUnderTest.setGame(mockGame);
        assertThat(playerAvailabilityUnderTest.getGame() == mockGame);
    }

    @Test
    void testIsAvailable() {
        playerAvailabilityUnderTest.setAvailable(true);
        assertTrue(playerAvailabilityUnderTest.isAvailable() == true);
    }

    @Test
    void testGetAvailableString() {
        final String result = playerAvailabilityUnderTest.getAvailableString();
        assertThat(result).isEqualTo("yes");
    }

    @Test
    void testSetAvailable() {
        playerAvailabilityUnderTest.setAvailable(true);
        assertTrue(playerAvailabilityUnderTest.isAvailable() == true);
    }
}
