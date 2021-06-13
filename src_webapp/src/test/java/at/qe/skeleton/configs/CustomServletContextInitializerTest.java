package at.qe.skeleton.configs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomServletContextInitializerTest {

    private CustomServletContextInitializer customServletContextInitializerUnderTest;

    @BeforeEach
    void setUp() {
        customServletContextInitializerUnderTest = new CustomServletContextInitializer();
    }

    @Test
    void testOnStartup() throws Exception {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final ServletContext sc = null;
            customServletContextInitializerUnderTest.onStartup(sc);
        });
    }
}
