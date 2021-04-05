package at.qe.skeleton.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TermsRepository extends AbstractRepository<Term, String> {

    Term findFirstByName(String name);
    List<Term> findAllByTopic(Topic topic);


}
