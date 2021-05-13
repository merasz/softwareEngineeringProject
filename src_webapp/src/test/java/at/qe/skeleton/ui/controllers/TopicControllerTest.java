package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testDoDeleteTopic() {
        Topic topic = new Topic();
        topicControllerUnderTest.doDeleteTopic();
        verify(mockTopicService).deleteTopic(new Topic("Geo"));
    }

    @Test
    void testDoDeleteTopic_TopicServiceThrowsIllegalArgumentException() {
        doThrow(IllegalArgumentException.class).when(mockTopicService).deleteTopic(new Topic("topicName"));
        topicControllerUnderTest.doDeleteTopic();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveTopic() {
        when(mockTopicService.saveTopic(new Topic("topicName"))).thenReturn(new Topic("topicName"));
        topicControllerUnderTest.doSaveTopic();
    }

    @Test
    void testGetTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockTopicService.getAllTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = topicControllerUnderTest.getTopics();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTopics_TopicServiceReturnsNoItems() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockTopicService.getAllTopics()).thenReturn(Collections.emptyList());
        final Collection<Topic> result = topicControllerUnderTest.getTopics();
        assertThat(result).isEqualTo(expectedResult);
    }
}
