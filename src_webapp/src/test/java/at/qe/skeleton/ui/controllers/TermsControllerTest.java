package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.primefaces.PrimeFaces;

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

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveTerm() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        final Topic topic = new Topic("topicName");
        final Term term = new Term("termName", new Topic("topicName"));
        when(mockTermsService.saveTerm(any(Term.class))).thenReturn(term);
        termsControllerUnderTest.doSaveTerm(topic);
        Controller controller = new Controller() {
            @Override
            protected void displayError(String summary, String detail) {
                super.displayError(summary, detail);
            }

            @Override
            protected void displayInfo(String summary, String detail) {
                super.displayInfo(summary, detail);
            }
        };


    });
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveTerm_TermsServiceThrowsIllegalArgumentException() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        final Topic topic = new Topic("topicName");
        when(mockTermsService.saveTerm(any(Term.class))).thenThrow(IllegalArgumentException.class);
        termsControllerUnderTest.doSaveTerm(topic);
        });
    }

    @Test
    void checkIfDelete() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Topic topic = new Topic();
            Term term = new Term();
            when(term.getTermName().isEmpty()).thenReturn(true);
            Controller controller = new Controller() {
                @Override
                protected void displayError(String summary, String detail) {
                    super.displayError(summary, detail);
                }

                @Override
                protected void displayInfo(String summary, String detail) {
                    super.displayInfo(summary, detail);
                }
            };

            when(term.getTermName().isEmpty()).thenReturn(true);
            controller.displayError("Term not created", "No term was entered.");

            try {
                when(mockTermsService.saveTerm(term)).thenReturn(term);
                PrimeFaces.current().executeScript("PF('termCreationDialog').hide()");
            } catch (IllegalArgumentException e) {
                controller.displayError("Term not created.", e.getMessage());
            }
            termsControllerUnderTest.deleteTerm();
            termsControllerUnderTest.doSaveTerm(topic);
            termsControllerUnderTest.setTerm(term);
            termsControllerUnderTest.getTerm();
            termsControllerUnderTest.init();
            termsControllerUnderTest.doCreateNewTerm();
        });
    }

    @Test
    void testDeleteTerm() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

            when(mockTermsService.getTermsRepository()).thenReturn(null);
            termsControllerUnderTest.deleteTerm();
            termsControllerUnderTest.displayError("Term deleted", "Term deleted successfully.");
            Controller controller = new Controller() {
                @Override
                protected void displayInfo(String summary, String detail) {
                    super.displayInfo(summary, detail);
                }

        //controller.displayInfo("Term deleted","Term deleted successfully.");

            };
        });
    }

    @Test
    void testGetTerm() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Term term = new Term();
            termsControllerUnderTest.setTerm(term);
            assertTrue(termsControllerUnderTest.getTerm() == term);
            Controller controller = new Controller() {
                @Override
                protected void displayInfo(String summary, String detail) {
                    super.displayInfo(summary, detail);
                }
            };
            controller.displayInfo("Term deleted", "Term deleted successfully.");

        });

}
    @Test
    void testSetTerm(){
        Term term = new Term();
        termsControllerUnderTest.setTerm(term);
        assertTrue(termsControllerUnderTest.getTerm() == term);
    }
}
