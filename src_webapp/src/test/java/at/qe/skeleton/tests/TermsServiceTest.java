package at.qe.skeleton.tests;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.repositories.TopicRepository;
import at.qe.skeleton.services.TermsService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
//        assertThat(result).isEqualTo(expectedResult);
    }

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

        Term term = new Term();
        term.setTermName(newName);
        termsService.saveTerm(term);

        term = termsService.getTermsRepository().findFirstByTermName(newName);
        Assertions.assertNotNull(term, "New term not found in database");

        Term existingTerm = new Term();
        existingTerm.setTermName(existingName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.saveTerm(existingTerm));

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
//        termsService.getTermsRepository().save(term);
//        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.deleteTopic(topic));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> termsService.setTopic(topic));

        fillTopic(topic);
        Assertions.assertDoesNotThrow(() -> termsService.setTopic(topic));
    }

    @Test
    void testSetTopic() {
        final Topic topic = new Topic("topicName");
        final Topic expectedResult = new Topic("topicName");
        final List<Term> terms = Arrays.asList(new Term("termName", new Topic("topicName")));
        when(mockTermsRepository.findAllByTopic(new Topic("topicName"))).thenReturn(terms);
//        final Topic result = termsServiceUnderTest.setTopic(topic);
//        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetNextTerm() {
        final User user = new User();
        final Game game = new Game();
        //final Term result = termsServiceUnderTest.getNextTerm(game);
    }

    @Test
    public void testImportTerms() {
        final JSONObject jsonObject = new JSONObject(new HashMap<>());
        final Topic topic = new Topic("topicName");
        when(mockTermsRepository.doesTermExits("name", "tn")).thenReturn(false);
        final Term term = new Term("termName", new Topic("topicName"));
        when(mockTermsRepository.save(any(Term.class))).thenReturn(term);
//        termsServiceUnderTest.importTerms(jsonObject, topic);
    }

    @Test
    void testGetTermsForTopic() {
        final Topic topic = new Topic("topicName");
        final List<Term> terms = Arrays.asList(new Term("termName", new Topic("topicName")));
        when(mockTermsRepository.findAllByTopic(new Topic("topicName"))).thenReturn(terms);
        final List<Term> result = termsServiceUnderTest.getTermsForTopic(topic);
    }

    @Test
    void testGetAllTerms() {
        final List<Term> terms = Arrays.asList(new Term("termName", new Topic("topicName")));
        when(mockTermsRepository.findAll()).thenReturn(terms);
        final Collection<Term> result = termsServiceUnderTest.getAllTerms();
    }
}
