package at.qe.skeleton.tests;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TermsServiceTest {
    @Autowired
    TermsService termsService;

    @Test
    public void testSaveTopic() {
        String newName = "TestTopic";
        String existingName = "TestTopic";
        termsService.saveTopic(newName, new Topic());

        Topic newTopic = termsService.getTopicRepository().findFirstByTopicName(newName);
        Assertions.assertNotNull(newTopic, "New topic not found in database");
        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.saveTopic(existingName, new Topic()));

        List<Topic> topics = termsService.getTopicRepository().findAll().stream()
                                .filter(x -> x.getTopicName().equals(existingName))
                                .collect(Collectors.toList());
        Assertions.assertEquals(1, topics.size(), "Topic exists multiple times in database");
    }

    @Test
    public void testSaveTerm() {
        String topicName = "TestTopic";
        String newName = "TestTerm";
        String existingName = "TestTerm";
        termsService.getTopicRepository().save(new Topic(topicName));

        Topic topic = termsService.getTopicRepository().findFirstByTopicName(topicName);
        termsService.saveTerm(newName, topic, new Term());

        Term newTerm = termsService.getTermsRepository().findFirstByTermName(newName);
        Assertions.assertNotNull(newTerm, "New term not found in database");
        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.saveTerm(existingName, topic, new Term()));

        List<Term> terms = termsService.getTermsRepository().findAll().stream()
                            .filter(x -> x.getTermName().equals(existingName))
                            .collect(Collectors.toList());
        Assertions.assertEquals(1, terms.size(), "Term exists multiple times in database");
    }

    @Test
    public void testDeleteTopic() {
        Topic topic = new Topic("TestTopic");
        termsService.getTopicRepository().save(topic);

        Assertions.assertDoesNotThrow(() -> termsService.deleteTopic(topic), "Topic could not be deleted");

        Term term = new Term();
        term.setTermName("TestTerm");
        term.setTopic(topic);
        termsService.getTermsRepository().save(term);
        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.deleteTopic(topic));
    }

    private Topic createTestTopic() {
        Topic topic = new Topic("TestTopic");
        termsService.getTopicRepository().save(topic);
        Term term = new Term();
        term.setTermName("TestTerm");
        term.setTopic(topic);
        termsService.getTermsRepository().save(term);
        return topic;
    }

    private void fillTopic(Topic topic) {
        for (int i = 0; i < 20; i++) {
            Term t = new Term();
            t.setTermName("TestTerm" + i);
            t.setTopic(topic);
            termsService.getTermsRepository().save(t);
        }
    }

    @Test
    public void testSetGameTopic() {
        Topic topic = createTestTopic();
        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.setGameTopic(topic));

        fillTopic(topic);
        Assertions.assertDoesNotThrow(() -> termsService.setGameTopic(topic));
    }

    @Test
    public void testGetNextTerm() {

    }

    @Test
    public void testImportTerms() {

    }
}
