package at.qe.skeleton.repositories;

import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import org.omnifaces.cdi.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsRepository extends AbstractRepository<Term, String> {

    Term findFirstByTermName(String name);
    List<Term> findAllByTopic(Topic topic);

    @Query("select count(t)>0 from Term t where t.termName = :name and t.topic.topicName = :tn")
    boolean doesTermExits(String name, String tn);

    @Query("SELECT new at.qe.skeleton.model.GameTopicCount (t.topic,count(*)) from Term t group by t.topic")
    List<GameTopicCount> getAmountOfTerms();

    @Query("SELECT t.topic from Term t group by t.topic")
    List<Topic> getAmountOfTermsJustTerms();

}
