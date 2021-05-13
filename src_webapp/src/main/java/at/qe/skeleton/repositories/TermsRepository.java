package at.qe.skeleton.repositories;

import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsRepository extends AbstractRepository<Term, String> {

    /**
     * Find first entry of a Term
     *
     * @param name
     * @return term
     */
    Term findFirstByTermName(String name);

    /**
     * Find all Terms by a topic.
     *
     * @param topic
     * @return list of topics
     */
    List<Term> findAllByTopic(Topic topic);

    /**
     * Checks if a Term in a specified Topic already exists.
     *
     * @param name
     * @param tn
     * @return true if exists else false
     */
    @Query("select count(t)>0 from Term t where t.termName = :name and t.topic.topicName = :tn")
    boolean doesTermExits(String name, String tn);

    /**
     * Counts how many terms a topic exists.
     *
     * @return list of GameTopicCounts
     */
    @Query("SELECT new at.qe.skeleton.model.GameTopicCount (t.topic,count(*)) from Term t group by t.topic")
    List<GameTopicCount> getAmountOfTerms();

    /**
     * Returns topics where a term exists.
     * @return
     */
    @Query("SELECT t.topic from Term t group by t.topic")
    List<Topic> getAmountOfTermsJustTerms();

}
