package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicListControllerTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicListController topicListController;

    @Test
    void testGetTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(topicService.getAllTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = topicListController.getTopics();
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testGetTopics_TopicServiceReturnsNoItems() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(topicService.getAllTopics()).thenReturn(Collections.emptyList());
        final Collection<Topic> result = topicListController.getTopics();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTopicNames() {
        when(topicService.getAllTopics()).thenReturn(Arrays.asList(new Topic("value")));
        final List<String> result = topicListController.getTopicNames();
        assertThat(result).isEqualTo(Arrays.asList("value"));
    }

    @Test
    void testGetTopicNames_TopicServiceReturnsNoItems() {
        when(topicService.getAllTopics()).thenReturn(Collections.emptyList());
        final List<String> result = topicListController.getTopicNames();
        assertThat(result).isEqualTo(Arrays.asList());
    }
}
