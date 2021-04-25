package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Raspberry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RaspberryRepository extends AbstractRepository<Raspberry, String> {

    @Query("SELECT r FROM Raspberry r WHERE r.ipAddress = :ipAddress")
    Raspberry findByIpAddress(@Param("ipAddress") String ipAddress);

}
