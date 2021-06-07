package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GameTopicCountTest {

    @Mock
    private Topic topic;

    private GameTopicCount gameTopicCount;

    @Test
    void testGetTopic() {
        GameTopicCount gameCount = new GameTopicCount();
        Topic topic = new Topic();
        gameCount.setTopic(topic);
        assertTrue(topic.equals(gameCount.getTopic()));
    }

    @Test
    void testSetTopic() {
        GameTopicCount gameCount = new GameTopicCount();
        Topic topic = new Topic();
        gameCount.setTopic(topic);
        assertTrue(topic.equals(gameCount.getTopic()));
    }
    @Test
    void testGetGameCount() {
        GameTopicCount gameCount = new GameTopicCount();
        gameCount.setGameCount(10);
        assertTrue(gameCount.getGameCount() == 10);
    }

    @Test
    void testSetGameCount() {
            GameTopicCount gameCount = new GameTopicCount();
            gameCount.setGameCount(10);
            assertTrue(gameCount.getGameCount() == 10);
    }


}