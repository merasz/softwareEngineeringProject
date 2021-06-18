package at.qe.skeleton.services;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.repositories.TopicRepository;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TermsServiceTest {
    @Autowired
    TermsService termsService;

    @Mock
    private TermsRepository mockTermsRepository;
    @Mock
    private TopicRepository mockTopicRepository;

    @InjectMocks
    private TermsService termsServiceUnderTest;

    @Test
    void testLoadTopic() {
        final Topic expectedResult = new Topic("topicName");
        when(mockTopicRepository.findFirstByTopicName("name")).thenReturn(new Topic("topicName"));
        final Topic result = termsServiceUnderTest.loadTopic("topicName");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSaveTopic() {
        final Topic topic = new Topic("Animals");
        termsServiceUnderTest.saveTopic("Animals", topic);
    }

    @Test
    public void testSaveTerm() {
        final Term term = new Term();
        final Term result = termsServiceUnderTest.saveTerm(term);
        assertThat(result).isEqualTo(term);
    }

    @Test
    public void testDeleteTopic() {
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> {
            final Topic topic = new Topic("topicName");
            final List<Term> terms = Arrays.asList(new Term("termName", new Topic("topicName")));
            when(mockTermsRepository.findAllByTopic(new Topic("topicName"))).thenReturn(terms);
            termsServiceUnderTest.deleteTopic(topic);
            verify(mockTopicRepository).delete(new Topic("topicName"));
        });
    }

    @Test
    void testDeleteTopic_TermsRepositoryReturnsNoItems() {
        final Topic topic = new Topic("topicName");
        when(mockTermsRepository.findAllByTopic(new Topic("topicName"))).thenReturn(Collections.emptyList());
        termsServiceUnderTest.deleteTopic(topic);
        verify(mockTopicRepository).delete(new Topic("topicName"));
    }

    @Test
    void testSetTopic() {
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> {
            final Topic topic = new Topic();
            final Topic result = termsServiceUnderTest.setTopic(topic);
            assertThat(result).isEqualTo(topic);
        });
    }

    @Test
    public void testImportTerms() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final JSONObject jsonObject = new JSONObject(new HashMap<>());
            final Topic topic = new Topic("topicName");
            when(mockTermsRepository.doesTermExits("name", "tn")).thenReturn(false);
            final Term term = new Term("termName", new Topic("topicName"));
            when(mockTermsRepository.save(any(Term.class))).thenReturn(term);
            termsServiceUnderTest.importTerms(jsonObject, topic);
        });
    }

    @Test
    void testGetTermsForTopic() {
        final Topic topic = new Topic();
        final List<Term> topicTerms = termsServiceUnderTest.getTermsForTopic(topic);
        final List<Term> result = mockTermsRepository.findAllByTopic(topic);
        assertThat(result).isEqualTo(topicTerms);
    }

    @Test
    void testGetTermsRepository() {
        List<Term> repositoryTerms = mockTermsRepository.findAll();
        TermsRepository result = termsServiceUnderTest.getTermsRepository();
        assertThat(result).isEqualTo(repositoryTerms);
    }

    @Test
    void testGetTopicRepository() {
        List<Topic> repositoryTopics = mockTopicRepository.findAll();
        TopicRepository result = termsServiceUnderTest.getTopicRepository();
        assertThat(result).isEqualTo(repositoryTopics);
    }

    @Test
    void testGetAllTerms() {
        final List<Term> terms = Arrays.asList(new Term("termName", new Topic("topicName")));
        when(mockTermsRepository.findAll()).thenReturn(terms);
    }

    @Test
    void testGetALlTerms() {
        Collection<Term> result = termsServiceUnderTest.getAllTerms();
        Collection<Term> expectedResult = mockTermsRepository.findAll();
        assertThat(result).isEqualTo(expectedResult);
    }
}
