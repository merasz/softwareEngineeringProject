package at.qe.skeleton.model.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class LogEntryTypeTest {

    @Test
    void testGetDescription() {
        assertThat(LogEntryType.USER_LOGIN.getDescription()).isEqualTo("joined the party");
        assertThat(LogEntryType.USER_LOGOUT.getDescription()).isEqualTo("has left the building");
    }
}
