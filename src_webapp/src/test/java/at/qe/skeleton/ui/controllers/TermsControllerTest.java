package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermsControllerTest {

    @Mock
    private TermsService mockTermsService;

    @InjectMocks
    private TermsController termsControllerUnderTest;

    @Test
    void testInit() {
        termsControllerUnderTest.init();
    }

    @Test
    void testDoCreateNewTerm() {
        termsControllerUnderTest.doCreateNewTerm();
    }

    @Test
    void testDoSaveTerm() {
        final Topic topic = new Topic("topicName");
        final Term term = new Term("termName", new Topic("topicName"));
        when(mockTermsService.saveTerm(any(Term.class))).thenReturn(term);
        termsControllerUnderTest.doSaveTerm(topic);
    }

    @Test
    void testDoSaveTerm_TermsServiceThrowsIllegalArgumentException() {
        final Topic topic = new Topic("topicName");
        when(mockTermsService.saveTerm(any(Term.class))).thenThrow(IllegalArgumentException.class);
        termsControllerUnderTest.doSaveTerm(topic);

    }

    @Test
    void testDeleteTerm() {
        when(mockTermsService.getTermsRepository()).thenReturn(null);
        termsControllerUnderTest.deleteTerm();
        termsControllerUnderTest.displayError("Term deleted", "Term deleted successfully.");

    }
    @Test
    void testGetTerm(){
        Term term = new Term();
         termsControllerUnderTest.setTerm(term);
        assertTrue(termsControllerUnderTest.getTerm() == term);
    }
    @Test
    void testSetTerm(){
        Term term = new Term();
        termsControllerUnderTest.setTerm(term);
        assertTrue(termsControllerUnderTest.getTerm() == term);
    }
}
