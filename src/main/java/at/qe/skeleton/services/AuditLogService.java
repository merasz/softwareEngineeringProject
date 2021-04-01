package at.qe.skeleton.services;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import at.qe.skeleton.model.AuditLog;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.AuditLogRepository;
import at.qe.skeleton.ui.beans.SessionInfoBean;

/**
 * Service for logging.
 * @author Üner Ismail
 */
@Service
@Scope("application")
public class AuditLogService implements Serializable {

    private static final long serialVersionUID = 6;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    @Lazy // Circular Dependencies
    private SessionInfoBean sessionInfo;

    private String className = this.getClass().getName();
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private boolean databaseEnabled = true;

    /**
     *  Constructor for Spring
     */
    public AuditLogService() {
    }


    /**
     * Constructor to use the logger without a database
     *
     * @param c the class to log
     */
    public AuditLogService(Class<?> c) {
        databaseEnabled = false;
        setClass(c);
    }

    /**
     * Call this method first to get the class otherwise {@link AuditLogService} is set as default.
     *
     * @param c the class to log
     */
    public void setClass(Class<?> c) {
        className = c.getName();
        log = LoggerFactory.getLogger(className);
    }

    /**
     * This method saves the log to the database.
     *
     * @param msg the message to log
     * @return the updated log
     */
    private AuditLog saveEntity(String msg) {
        if (!databaseEnabled) {
            return null;
        }

        if (msg == null || msg.isEmpty()) {
            return null;
        }

        AuditLog entity = new AuditLog();
        User user = sessionInfo.getCurrentUser();

        entity.setMessage(msg);
        entity.setTime(new Date());
        entity.setUpdateUser(user);
        entity.setClassInformation(className);

        return auditLogRepository.save(entity);
    }


    /**
     * Log with Debug level. The log will printed only in the Console.
     *
     * @param msg the message to log
     */
    public void debug(String msg) {
        log.debug(msg);
    }

    /**
     * Log with Info level. The log will printed only in the Console.
     *
     * @param msg the message to log
     */
    public void info(String msg) {
        log.info(msg);
    }


    /**
     * Log with Warning level. The log will printed in the Console and saved in the database.
     *
     * @param msg the message to log
     */
    public void warn(String msg) {
        log.warn(msg);
        saveEntity(msg);
    }

    /**
     * Log with Error level. The log will printed in the Console and saved in the database.
     *
     * @param msg the message to log
     */
    public void error(String msg) {
        log.error(msg);
        saveEntity(msg);
    }
}