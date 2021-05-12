package at.qe.skeleton.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
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
