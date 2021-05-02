package at.qe.skeleton.repositories;

import at.qe.skeleton.model.TimeFlipConf;

public interface TimeFlipConfRepository extends AbstractRepository<TimeFlipConf, String> {

    TimeFlipConf findByFacetId(int facetId);

}
