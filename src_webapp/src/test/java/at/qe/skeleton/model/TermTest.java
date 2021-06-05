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
class TermTest {

    @Mock
    private Topic mockTopic;

    private Term termUnderTest;

    @BeforeEach
    void setUp() {
        termUnderTest = new Term("termName", mockTopic);
    }

    @Test
    void testGetTermName() {
        Term term = new Term();
        term.setTermName("chicken");
        assertTrue(term.getTermName() == "chicken");
    }

    @Test
    void testSetTermName() {
        Term term = new Term();
        term.setTermName("chicken");
        assertTrue(term.getTermName() == "chicken");
    }

    @Test
    void testGetTopic() {
        Term term = new Term();
        Topic topic = new Topic();
        term.setTopic(topic);
        assertTrue(topic.equals(term.getTopic()));
    }

    @Test
    void testSetTopic() {
        Term term = new Term();
        Topic topic = new Topic();
        term.setTopic(topic);
        assertTrue(topic.equals(term.getTopic()));
    }

    @Test
    void testGetSerialVersionUID() {
        assertThat(Term.getSerialVersionUID()).isEqualTo(1L);
    }
}
