package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopicTest {

    private Topic topicUnderTest;

    @BeforeEach
    void setUp() {
        topicUnderTest = new Topic("topicName");
    }

    @Test
    void testGetTopicName() {
        Topic topic = new Topic();
        topic.setTopicName("Jobs");
        assertTrue("Jobs".equals(topic.getTopicName()));
    }

    @Test
    void testSetTopicName() {
        Topic topic = new Topic();
        topic.setTopicName("Jobs");
        assertTrue("Jobs".equals(topic.getTopicName()));
    }

    @Test
    void testGetTerms() {
        Topic topic = new Topic();
        List<Term> terms = new ArrayList<>();
        topic.setTerms(terms);
        assertTrue(topic.getTerms() == terms);
    }

    @Test
    void testSetTerms() {
        Topic topic = new Topic();
        List<Term> terms = new ArrayList<>();
        topic.setTerms(terms);
        assertTrue(topic.getTerms() == terms);
    }

    @Test
    void testGetSerialVersionUID() {
        assertThat(Topic.getSerialVersionUID()).isEqualTo(1L);
    }

    @Test
    void testGetGames() {
        Topic topic = new Topic();
        List<Game> games = new ArrayList<>();
        topic.setGames(games);
        assertTrue(topic.getGames() == games);
    }

    @Test
    void testSetGames() {
        Topic topic = new Topic();
        List<Game> games = new ArrayList<>();
        topic.setGames(games);
        assertTrue(topic.getGames() == games);
    }

    @Test
    void testGetCreateDate() {
        Topic topic = new Topic();
        Date date = new Date();
        topic.setCreateDate(date);
        assertTrue(topic.getCreateDate() == date);
    }

    @Test
    void testSetCreateDate() {
        Topic topic = new Topic();
        Date date = new Date();
        topic.setCreateDate(date);
        assertTrue(topic.getCreateDate() == date);
    }

    @Test
    void testIsNew() {
        final boolean result = topicUnderTest.isNew();
        assertThat(result).isTrue();
    }

    @Test
    void testGetUpdateDate() {
        Topic topic = new Topic();
        Date date = new Date();
        topic.setUpdateDate(date);
        assertTrue(topic.getUpdateDate() == date);
    }

    @Test
    void testSetUpdateDate() {
        Topic topic = new Topic();
        Date date = new Date();
        topic.setUpdateDate(date);
        assertTrue(topic.getUpdateDate() == date);
    }

    @Test
    void testGetUpdateTopic() {
        Topic topic = new Topic();
        topic.setUpdateTopic(topic);
        assertTrue(topic.getUpdateTopic() == topic);
    }

    @Test
    void testSetUpdateTopic() {
        Topic topic = new Topic();
        topic.setUpdateTopic(topic);
        assertTrue(topic.getUpdateTopic() == topic);
    }

    @Test
    void testGetId() {
        Integer integer = 1;
        topicUnderTest.setId(integer);
        assertTrue(topicUnderTest.getId() == integer);
    }

    @Test
    void testSetId() {
        Integer integer = 1;
        topicUnderTest.setId(integer);
        assertTrue(topicUnderTest.getId() == integer);
    }

    @Test
    void testEquals() {
        final boolean result = topicUnderTest.equals("obj");
        assertThat(result).isFalse();
    }

    @Test
    void testHashCode() {
        final int result = topicUnderTest.hashCode();
        assertThat(result).isEqualTo(469199209);
    }

    @Test
    void testToString() {
        final String result = topicUnderTest.toString();
        assertThat(result).isEqualTo("topicName");
    }
}
