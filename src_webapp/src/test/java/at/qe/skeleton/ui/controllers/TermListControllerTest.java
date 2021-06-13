package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.services.GameStartService;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermListControllerTest {

    @Mock
    private TermsService termsService;
    @Mock
    private TopicService topicService;

    @InjectMocks
    private TermListController termListController;

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTerms(){
        final Collection<Term> terms = Arrays.asList((new Term()));
        when(termsService.getAllTerms()).thenReturn(terms);
        termListController.getTerm();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTermsByTopic() {
        final User user = new User();
        final List<Term> terms = Arrays.asList(new Term());
        when(termsService.getTermsForTopic(new Topic("topicName"))).thenReturn(terms);
        final List<Term> result = termListController.getTermsByTopic();
    }


    @Test
    void testDoSetTopic() {
        final Topic topic = new Topic("topicName");
        termListController.doSetTopic(topic);

    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTopic(){
        final List<Topic> terms = Arrays.asList((new Topic()));
        when(topicService.getAllTopics()).thenReturn(terms);
        termListController.getTopic();
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetTopic(){
        final List<Topic> terms = Arrays.asList((new Topic()));
        when(topicService.getAllTopics()).thenReturn(terms);
        termListController.setTopic((new Topic()));
    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testSetTerms(){
        final Collection<Term> terms = Arrays.asList((new Term()));
        when(termsService.getAllTerms()).thenReturn(terms);
        termListController.setTerm((new Term()));
    }


    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testGetTopics() {
        TopicService myService = mock(TopicService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.getAllTopics()).thenThrow(IllegalArgumentException.class);
        when(termsService.getTermsRepository()).thenReturn(null);

        termListController.getTopics();
    }

    /*
    @Test
    void testGetTermsOfTopic() {
        final Term term = new Term();
        TopicService myService = mock(TopicService.class, Mockito.RETURNS_DEEP_STUBS);
        when(myService.getAllTopics()).thenThrow(IllegalArgumentException.class);
        when(termsService.getTermsRepository()).thenReturn(null);
        termListController.getTermsOfTopic();
    }*/
}