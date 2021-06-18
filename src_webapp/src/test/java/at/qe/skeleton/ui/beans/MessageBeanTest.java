package at.qe.skeleton.ui.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageBeanTest {

    private MessageBean messageBeanUnderTest;

    @BeforeEach
    void setUp() {
        messageBeanUnderTest = new MessageBean();
    }

    @Test
    void testAlertInformation() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            messageBeanUnderTest.alertInformation("summary", "info");
        });
    }

    @Test
    void testAlertError() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            messageBeanUnderTest.alertError("summary", "info");
        });
    }
}
