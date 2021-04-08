package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Topic;

public interface TopicRepository extends AbstractRepository<Topic, String> {

    Topic findFirstByTopicName(String name);

}
