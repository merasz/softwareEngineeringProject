package at.qe.skeleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.faces.webapp.FacesServlet;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
class MainTest {

    private Main mainUnderTest;

    @BeforeEach
    void setUp() {
        mainUnderTest = new Main();
    }

    @Test
    void testServletRegistrationBean() {
        final ServletRegistrationBean<FacesServlet> result = mainUnderTest.servletRegistrationBean();
    }

    @Test
    void testMain() {
        Main.main(new String[]{"Hello", "World"});
    }
}
