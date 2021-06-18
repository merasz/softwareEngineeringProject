package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class LogEntryTest {

    private LogEntryType mockLogEntryType;

    @Mock
    private User mockUser;
    @Mock
    private Date mockDate;

    private LogEntry logEntryUnderTest;

    @BeforeEach
    void setUp() {
        logEntryUnderTest = new LogEntry(mockUser, LogEntryType.USER_LOGIN);
    }

    @Test
    void testGetUser() {
        User user = new User();
        logEntryUnderTest.setUser(user);
        assertTrue(logEntryUnderTest.getUser() == user);
    }

    @Test
    void testSetUser() {
        User user = new User();
        logEntryUnderTest.setUser(user);
        assertTrue(logEntryUnderTest.getUser() == user);
    }

    @Test
    void testGetTimestamp() {
        Date timestamp = new Date();
        logEntryUnderTest.setTimestamp(timestamp);
        assertTrue(logEntryUnderTest.getTimestamp() == timestamp);
    }

    @Test
    void testSetTimestamp() {
        Date timestamp = new Date();
        logEntryUnderTest.setTimestamp(timestamp);
        assertTrue(logEntryUnderTest.getTimestamp() == timestamp);
    }

    @Test
    void testGetLogType() {
        logEntryUnderTest.setLogType(mockLogEntryType);
        assertTrue(logEntryUnderTest.getLogType() == mockLogEntryType);
    }

    @Test
    void testSetLogType() {
        logEntryUnderTest.setLogType(mockLogEntryType);
        assertTrue(logEntryUnderTest.getLogType() == mockLogEntryType);
    }
}
