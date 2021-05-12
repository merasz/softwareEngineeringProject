package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermListControllerTest {

    @Mock
    private TermsService termsService;
    @Mock
    private TopicService topicService;

    @InjectMocks
    private TermListController termListController;


    //@Test
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

    //@Test
    void testGetTermsOfTopic1() {
        when(termsService.getTermsRepository()).thenReturn(null);
        final List<Term> result = termListController.getTermsOfTopic();
    }

    //@Test
    void testGetTermsOfTopic() {
        final Term term = new Term();
        when(termsService.getTermsRepository()).thenReturn(null);
        final List<Term> result = termListController.getTermsOfTopic();
    }
}
