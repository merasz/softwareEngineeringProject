package at.qe.skeleton.repositories;

import at.qe.skeleton.model.TimeFlipConf;

public interface TimeFlipConfRepository extends AbstractRepository<TimeFlipConf, String> {

    /**
     * Returns the associated facet with its points and type back, how a guessing entry should be descibed and
     * how much its worth, also the time.
     *
     * @param facetId
     * @return TimeFlipConf
     */
    TimeFlipConf findByFacetId(int facetId);

}
