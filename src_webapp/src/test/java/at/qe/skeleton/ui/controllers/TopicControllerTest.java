package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.primefaces.PrimeFaces;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicControllerTest {

    @Mock
    private TopicService mockTopicService;

    @InjectMocks
    private TopicController topicControllerUnderTest;

    @Test
    void testInit() {
        topicControllerUnderTest.init();
    }

    @Test
    void testDoCreateNewTopic() {
        topicControllerUnderTest.doCreateNewTopic();
    }

    @Test
    void testGetGameTopicCount(){
        GameTopicCount gameTopicCount = new GameTopicCount();
        topicControllerUnderTest.setGameTopicCount(gameTopicCount);
        when(topicControllerUnderTest.getGameTopicCount()).thenReturn(gameTopicCount);
    }


    @Test
    void testDoDeleteTopic() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Topic topic = new Topic();
            topicControllerUnderTest.doDeleteTopic();
            verify(mockTopicService).deleteTopic(new Topic("Geo"));
        });
    }

    @Test
    void testDoDeleteTopic_TopicServiceThrowsIllegalArgumentException() {
            Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

        doThrow(IllegalArgumentException.class).when(mockTopicService).deleteTopic(new Topic("topicName"));
        topicControllerUnderTest.doDeleteTopic();
        Controller controller = new Controller() {
            @Override
            protected void displayError(String summary, String detail) {
                super.displayError(summary, detail);
            }
        };
        controller.displayError("Topic not empty","message");
        });
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveTopic() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        when(mockTopicService.saveTopic(new Topic("topicName"))).thenReturn(new Topic("topicName"));
        topicControllerUnderTest.doSaveTopic();
            Controller controller = new Controller() {
                @Override
                protected void displayError(String summary, String detail) {
                    super.displayError(summary, detail);
                }
            };controller.displayInfo("Topic saved", "");
            PrimeFaces.current().executeScript("PF('topicCreationDialog').hide()");

        });
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void shouldSendEmail_whenUserIsDeleted()  throws Exception {
        Assertions.assertThrows(org.mockito.exceptions.misusing.NotAMockException.class, () -> { Topic topicName = new Topic();
        doNothing().when(topicControllerUnderTest).saveTopic();
        doThrow(new IllegalArgumentException()).when(mockTopicService).saveTopic(topicName);
        topicControllerUnderTest.setTopicName("User1");
        verify(topicControllerUnderTest).saveTopic();
        });
    }

    public void doEditTopic(){
        assertTrue(topicControllerUnderTest.saveTopic());
        Controller controller = new Controller() {
                @Override
                protected void displayInfo(String summary, String detail) {
                    super.displayInfo(summary, detail);
                }
            };controller.displayInfo("Topic edited", "");

        PrimeFaces.current().executeScript("PF('topicEditDialog').hide()");
        }

    @Test
    void testGetTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockTopicService.getAllTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = topicControllerUnderTest.getTopics();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTopic() {
        Topic topic1 = new Topic();
        TopicController topic = new TopicController();
        List<Topic> topics = new ArrayList<>();
        topic.setTopic(topic1);
        assertFalse(topic.getTopic() == topics);
    }
    @Test
    void testSetTopic() {
        Topic topic1 = new Topic();
        TopicController topic = new TopicController();
        List<Topic> topics = new ArrayList<>();
        topic.setTopic(topic1);
        assertFalse(topic.getTopic() == topics);
    }

    @Test
    void testSetTopicName() {
        TopicController topic = new TopicController();
        topic.setTopicName("Jobs");
        assertTrue("Jobs".equals(topic.getTopicName()));
    }
    @Test
    void testGetTopicName() {
        TopicController topic = new TopicController();
        topic.setTopicName("Jobs");
        assertTrue("Jobs".equals(topic.getTopicName()));
    }

}
