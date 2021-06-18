package at.qe.skeleton.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    private ErrorMessage errorMessageUnderTest;
    private String message = "";

    @BeforeEach
    void setUp() {
        errorMessageUnderTest = new ErrorMessage();
    }

    @Test
    void testGetMessage() {
        assertThat(errorMessageUnderTest.getMessage() == message);
    }

    @Test
    void testReset() {
        errorMessageUnderTest.reset();
    }

    @Test
    void testPushMessage() {
        errorMessageUnderTest.pushMessage("msg");
    }

    @Test
    void testHasError() {
        final boolean result = errorMessageUnderTest.hasError();
        assertThat(result).isTrue();
    }
}
