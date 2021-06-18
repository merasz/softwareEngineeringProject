package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuditLogEntryTest {

    @Mock
    private int mockAuditLogId;
    @Mock
    private String mockMessage;
    @Mock
    private Date mockModificationDate;

    private AuditLogEntry auditLogEntryUnderTest;

    @BeforeEach
    void setUp() {
        auditLogEntryUnderTest = new AuditLogEntry(mockAuditLogId, mockMessage, mockModificationDate);
        }
    @Test
    void testGetSerialVersionUID() {
        assertThat(auditLogEntryUnderTest.getSerialVersionUID()).isEqualTo(2L);
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
        auditLogEntryUnderTest.setAuditLogId(mockAuditLogId);
        assertTrue(auditLogEntryUnderTest.getAuditLogId() == mockAuditLogId);
    }
    
    @Test
    void testSetAuditLogId() {
        auditLogEntryUnderTest.setAuditLogId(mockAuditLogId);
        assertTrue(auditLogEntryUnderTest.getAuditLogId() == mockAuditLogId);
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


