package at.qe.skeleton.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
public class AuditLogEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auditLogId;

    private int auditLogEntryId;
    private String message;
    private Date modificationDate;

    @ElementCollection(targetClass = AuditLogEvent.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Auditlogentry_AuditLogEvent")
    @Enumerated(EnumType.STRING)
    private Set<AuditLogEvent> auditLogEvents;

    public AuditLogEntry() {
    }

    public int getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(int auditLogId) {
        this.auditLogId = auditLogId;
    }

    public int getAuditLogEntryId() {
        return auditLogEntryId;
    }

    public void setAuditLogEntryId(int auditLogEntryId) {
        this.auditLogEntryId = auditLogEntryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public AuditLogEntry(int auditLogId, int auditLogEntryId, String message, Date modificationDate) {
        this.auditLogId = auditLogId;
        this.auditLogEntryId = auditLogEntryId;
        this.message = message;
        this.modificationDate = modificationDate;
    }
}
