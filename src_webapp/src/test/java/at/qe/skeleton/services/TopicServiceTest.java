package at.qe.skeleton.services;

import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.repositories.AuditLogRepository;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.repositories.TopicRepository;
import at.qe.skeleton.ui.beans.MessageBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository mockTopicRepository;
    @Mock
    private TermsRepository mockTermsRepository;
    @Mock
    private AuditLogRepository mockAuditLogRepository;
    @Mock
    private MessageBean mockMessageBean;

    @InjectMocks
    private TopicService topicServiceUnderTest;

    @Test
    void testGetAllTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockTopicRepository.findAll()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = topicServiceUnderTest.getAllTopics();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllTopicsAsc() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockTopicRepository.findAllAsc()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = topicServiceUnderTest.getAllTopicsAsc();
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testLoadTopic() {
        final Topic expectedResult = new Topic("topicName");
        when(mockTopicRepository.findFirstByTopicName("name")).thenReturn(new Topic("topicName"));
        final Topic result = topicServiceUnderTest.loadTopic("topicName");
        assertThat(result).isEqualTo(expectedResult);
    }

    //@Test
    void testSaveTopic() {
        final Topic topic = new Topic("topicName");
        final Topic expectedResult = new Topic("topicName");
        when(mockTopicRepository.findByTopicNameContaining("topicName")).thenReturn(Arrays.asList(new Topic("topicName")));
        when(mockTopicRepository.findFirstByTopicName("name")).thenReturn(new Topic("topicName"));
        when(mockTopicRepository.save(new Topic("topicName"))).thenReturn(new Topic("topicName"));
        final Topic result = topicServiceUnderTest.saveTopic(topic);
        assertThat(result).isEqualTo(expectedResult);
        verify(mockMessageBean).alertInformation("summary", "info");
    }

    //@Test
    void testDeleteTopic() {
        final Topic topic = new Topic("topicName");
        final List<Term> terms = Arrays.asList(new Term("termName", new Topic("topicName")));
        when(mockTermsRepository.findAllByTopic(new Topic("topicName"))).thenReturn(terms);
        topicServiceUnderTest.deleteTopic(topic);
        verify(mockTopicRepository).delete(new Topic("topicName"));
    }

    //@Test
    void testGetTopicByName() {
        final Topic topic = new Topic("topicName");
        final Topic expectedResult = new Topic("topicName");
        when(mockTopicRepository.findFirstByTopicName("name")).thenReturn(new Topic("topicName"));
        final Topic result = topicServiceUnderTest.getTopicByName(topic);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetTopicTermsAmount() {
        final List<GameTopicCount> gameTopicCounts = Arrays.asList(new GameTopicCount(new Topic("topicName"), 0L));
        when(mockTermsRepository.getAmountOfTerms()).thenReturn(gameTopicCounts);
        when(mockTopicRepository.findAll()).thenReturn(Arrays.asList(new Topic("topicName")));
        when(mockTermsRepository.getAmountOfTermsJustTerms()).thenReturn(Arrays.asList(new Topic("topicName")));
        final List<GameTopicCount> result = topicServiceUnderTest.getTopicTermsAmount();
    }
}