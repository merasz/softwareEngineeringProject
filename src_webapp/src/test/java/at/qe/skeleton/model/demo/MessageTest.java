package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MessageTest {

    private Message messageUnderTest;

    @Mock
    private Date mockDate;

    @BeforeEach
    void setUp() {
        messageUnderTest = new Message();
    }

    @Test
    void testGetFrom() {
        User from = new User();
        messageUnderTest.setFrom(from);
        assertThat(messageUnderTest.getFrom() == from);
    }

    @Test
    void testGetTo() {
        List<User> to = new ArrayList<>();
        messageUnderTest.setTo(to);
        assertThat(messageUnderTest.getTo() == to);
    }

    @Test
    void testGetText() {
        messageUnderTest.setText("hello");
        assertThat(messageUnderTest.getText() == "hello");
    }

    @Test
    void testHashCode() {
        final int result = messageUnderTest.hashCode();
        assertThat(result).isEqualTo(89010296);
    }

    @Test
    void testEquals() {
        final boolean result = messageUnderTest.equals("obj");
        assertThat(result).isFalse();
        /*
        final boolean timestamp1 = mockDate.equals(null);
        final boolean timestamp2 = mockDate.equals(null);
        final boolean result1 = (timestamp1 != timestamp2);
        assertThat(result1).isFalse();

         */
    }

    @Test
    void testCompareTo() {
        final Message o = new Message();
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(false);
        o.setTo(Arrays.asList(user));
        o.setTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        o.setText("text");
        final int result = messageUnderTest.compareTo(o);
        assertThat(result).isEqualTo(1);
    }
}
