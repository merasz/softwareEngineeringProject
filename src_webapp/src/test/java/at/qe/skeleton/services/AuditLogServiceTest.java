package at.qe.skeleton.services;

import at.qe.skeleton.model.AuditLog;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.AuditLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omnifaces.util.Messages;
import org.springframework.data.util.ClassTypeInformation;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.omnifaces.util.Messages.isEmpty;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

    @Mock
    private AuditLogRepository mockAuditLogRepository;

    private AuditLogService auditLogServiceUnderTest;

    @BeforeEach
    void setUp() {
        auditLogServiceUnderTest = new AuditLogService();
        auditLogServiceUnderTest = new AuditLogService(Object.class);
    }

    @Test
    void testSetClass() {
        auditLogServiceUnderTest.setClass(Object.class);
    }

    @Test
    void testDebug() {
        auditLogServiceUnderTest.debug("msg");
    }

    @Test
    void testInfo() {
        auditLogServiceUnderTest.info("msg");
    }

    @Test
    void testWarn() {
        auditLogServiceUnderTest.warn("msg");
    }

    @Test
    void testError() {
        auditLogServiceUnderTest.error("msg");
    }

    @Test
    void testSaveEntity() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final AuditLog entity = new AuditLog();
            final User user = new User();
            final String msg = null;
            assertTrue(msg == null);
            assertTrue(msg.isEmpty());
            //assertThat(msg, isEmpty());
            entity.setTime(new Date());
            entity.setUpdateUser(user);
            //entity.setClassInformation(className);

        });
    }
}
