package at.qe.skeleton.repositories;

import at.qe.skeleton.model.AuditLog;
import org.springframework.stereotype.Repository;

/**
 * @author Üner Ismail
 */
@Repository
public interface AuditLogRepository extends AbstractRepository<AuditLog, Long>{


    /**
     * This function is for testing propose only.
     *
     * @param message to search
     * @return the message if found
     */

    /* Only for JUnit Test */
    AuditLog findByMessage(String message);
}
