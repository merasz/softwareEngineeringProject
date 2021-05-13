package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Topic;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface TopicRepository extends AbstractRepository<Topic, String> {

    /**
     * Returns the first Topic with the given name.
     *
     * @param name
     * @return Topic
     */
    Topic findFirstByTopicName(String name);

    /**
     * Returns the first Topic which contains the given word.
     *
     * @param topicName
     * @return
     */
    List<Topic> findByTopicNameContaining(String topicName);

    /**
     * Returns all Topics in asc ordering.
     *
     * @return Collection of topic
     */
    @Query("Select t from Topic t order by t.topicName asc")
    Collection<Topic> findAllAsc();

}
