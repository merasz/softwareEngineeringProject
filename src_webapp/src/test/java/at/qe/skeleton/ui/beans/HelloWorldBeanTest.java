package at.qe.skeleton.ui.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldBeanTest {

    private HelloWorldBean helloWorldBeanUnderTest;

    @BeforeEach
    void setUp() {
        helloWorldBeanUnderTest = new HelloWorldBean();
    }

    @Test
    void testGetHello() {
        final String result = helloWorldBeanUnderTest.getHello();
        assertThat(result).isEqualTo("Hello from a JSF-managed bean!");
    }
}
