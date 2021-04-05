package at.qe.skeleton.repositories;

public interface TopicRepository extends AbstractRepository<Topic, String> {

    Topic findFirstByName(String name);

}
