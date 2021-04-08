package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;

import java.util.List;

public interface TermsRepository extends AbstractRepository<Term, String> {

    Term findFirstByTermName(String name);
    List<Term> findAllByTopic(Topic topic);

}
