package at.qe.skeleton.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
public class AuditLogEntry implements Serializable {
    private static final long serialVersionUID = 2L;

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

    public AuditLogEntry(int auditLogEntryId, String message, Date modificationDate) {
        this.auditLogEntryId = auditLogEntryId;
        this.message = message;
        this.modificationDate = modificationDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<AuditLogEvent> getAuditLogEvents() {
        return auditLogEvents;
    }

    public void setAuditLogEvents(Set<AuditLogEvent> auditLogEvents) {
        this.auditLogEvents = auditLogEvents;
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
}
