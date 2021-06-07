package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuditLogEntryTest {
    private AuditLogEntry auditLogEntry;

    @BeforeEach
    void setUp() {
        auditLogEntry = new AuditLogEntry();
        }
    @Test
    void testGetSerialVersionUID() {
        assertThat(Term.getSerialVersionUID()).isEqualTo(1L);
    }

    @Test
    void testSetAuditLogEvents() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        Set<AuditLogEvent> auditLogEvents = new HashSet<>();
        auditLogEntry.setAuditLogEvents(auditLogEvents);
        assertTrue(auditLogEntry.getAuditLogEvents() == auditLogEvents);
    }
    @Test
    void testGetAuditLogEvents() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        Set<AuditLogEvent> auditLogEvents = new HashSet<>();
        auditLogEntry.setAuditLogEvents(auditLogEvents);
        assertTrue(auditLogEntry.getAuditLogEvents() == auditLogEvents);
    }
    @Test
    void testGetAuditLogId() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setAuditLogEntryId(10);
        assertTrue(auditLogEntry.getAuditLogEntryId() == 10);
    }
    @Test
    void testSetAuditLogId() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setAuditLogEntryId(10);
        assertTrue(auditLogEntry.getAuditLogEntryId() == 10);
    }
    @Test
    void testGetAuditLogEntryId() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setAuditLogEntryId(13);
        assertTrue(auditLogEntry.getAuditLogEntryId() == 13);
    }
    @Test
    void testSetAuditLogEntryId() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setAuditLogEntryId(13);
        assertTrue(auditLogEntry.getAuditLogEntryId() == 13);
    }

    @Test
    void testSetMessage() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setMessage("Hello");
        assertTrue("Hello".equals(auditLogEntry.getMessage()));
    }
    @Test
    void testGetMessage() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setMessage("Bye");
        assertTrue("Bye".equals(auditLogEntry.getMessage()));
    }

    @Test
    void testSetUpdateDate() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        Date date = new Date();
        auditLogEntry.setModificationDate(date);
        assertTrue(auditLogEntry.getModificationDate() == date);
    }
    @Test
    void testGetUpdateDate() {
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        Date date = new Date();
        auditLogEntry.setModificationDate(date);
        assertTrue(auditLogEntry.getModificationDate() == date);
    }







}


