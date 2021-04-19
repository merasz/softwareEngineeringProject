package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Topic;
import org.omnifaces.cdi.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface TopicRepository extends AbstractRepository<Topic, String> {

    Topic findFirstByTopicName(String name);

    List<Topic> findByTopicNameContaining(String topicName);

    @Query("SELECT t FROM Topic t where t.topicName =: topicName")
    List<Topic> findByTopicName(@Param(converter = "topicName") String topicName);

}
