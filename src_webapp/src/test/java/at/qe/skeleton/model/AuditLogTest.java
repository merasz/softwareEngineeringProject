package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuditLogTest {
    @Test
    void testGetSerialVersionUID() {
        assertThat(Term.getSerialVersionUID()).isEqualTo(1L);
    }
    @Test
    void testSetId() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId(3);
        assertTrue(auditLog.getId() == 3);
    }
    @Test
    void testGetId() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId(3);
        assertTrue(auditLog.getId() == 3);
    }

    @Test
    void testSetMessage() {
        AuditLog auditLog = new AuditLog();
        auditLog.setMessage("Ciao");
        assertTrue("Ciao".equals(auditLog.getMessage()));
    }
    @Test
    void testGetMessage() {
        AuditLog auditLog = new AuditLog();
        auditLog.setMessage("Boh");
        assertTrue("Boh".equals(auditLog.getMessage()));
    }

    @Test
    void testGetUpdateUser() {
        User user = new User();
        user.setUpdateUser(user);
        assertTrue(user.getUpdateUser() == user);
    }
    @Test
    void testSetUpdateUser() {
        User user = new User();
        user.setUpdateUser(user);
        assertTrue(user.getUpdateUser() == user);
    }

    @Test
    void testGetTime() {
        AuditLog auditLog = new AuditLog();
        Date date = new Date();
        auditLog.setTime(date);
        assertTrue(auditLog.getTime() == date);
    }
    @Test
    void testSetClassInformation() {
        AuditLog auditLog = new AuditLog();
        auditLog.setClassInformation("abc");
        assertTrue("abc".equals(auditLog.getClassInformation()));
    }
    @Test
    void testIsNew() {
        AuditLog auditLog = new AuditLog();
        final boolean result = auditLog.isNew();
        assertThat(result).isTrue();
    }

}
