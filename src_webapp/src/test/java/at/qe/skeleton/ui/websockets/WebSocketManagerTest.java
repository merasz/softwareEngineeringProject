package at.qe.skeleton.ui.websockets;

import at.qe.skeleton.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omnifaces.cdi.PushContext;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WebSocketManagerTest {

    @Mock
    private PushContext mockUserRegistrationChannel;
    @Mock
    private PushContext mockMessageChannel;
    @Mock
    private PushContext mockJoinChannel;
    @Mock
    private PushContext mockScoreChannel;
    @Mock
    private PushContext mockTimeChannel;
    @Mock
    private PushContext mockTermChannel;
    @Mock
    private PushContext mockGameChannel;
    @Mock
    private PushContext mockInfoChannel;

    @InjectMocks
    private WebSocketManager webSocketManagerUnderTest;

    @Test
    void testGetUserRegistrationChannel() {
        webSocketManagerUnderTest.getUserRegistrationChannel();
    }

    @Test
    void testGetMessageChannel() {
        webSocketManagerUnderTest.getMessageChannel();
    }

    @Test
    void testGetJoinChannel() {
        webSocketManagerUnderTest.getJoinChannel();
    }

    @Test
    void testGetScoreChannel() {
        webSocketManagerUnderTest.getScoreChannel();
    }

    @Test
    void testGetTimeChannel() {
        webSocketManagerUnderTest.getTimeChannel();
    }

    @Test
    void testGetTermChannel() {
        webSocketManagerUnderTest.getTermChannel();
    }

    @Test
    void testGetGameChannel() {
        webSocketManagerUnderTest.getGameChannel();
    }

    @Test
    void testGetInfoChannel() {
        webSocketManagerUnderTest.getInfoChannel();
    }
}

