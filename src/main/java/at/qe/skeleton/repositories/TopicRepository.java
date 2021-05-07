package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Topic;
import org.omnifaces.cdi.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface TopicRepository extends AbstractRepository<Topic, String> {

    Topic findFirstByTopicName(String name);
    List<Topic> findByTopicNameContaining(String topicName);

    @Query("Select t from Topic t order by t.topicName asc")
    Collection<Topic> findAllAsc();

    //@Query("SELECT t FROM Topic t where t.topicName =: topicName")
    //List<Topic> findByTopicName(@Param(converter = "topicName") String topicName);

    @Query("select distinct t from Topic t order by t.topicName asc")
    List<Topic> getAllTopicNames();
}
