package at.qe.skeleton.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

    private AuditLogService auditLogServiceUnderTest;

    @BeforeEach
    void setUp() {
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
}
