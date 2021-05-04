package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TermsRepository extends AbstractRepository<Term, String> {

    Term findFirstByTermName(String name);
    List<Term> findAllByTopic(Topic topic);

    @Query("select count(t)>0 from Term t where t.termName = :name and t.topic.topicName = :tn")
    boolean doesTermExits(String name, String tn);
}
