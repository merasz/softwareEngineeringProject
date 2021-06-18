package at.qe.skeleton.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomAuthenticationFailureHandlerTest {

    private CustomAuthenticationFailureHandler customAuthenticationFailureHandlerUnderTest;

    @BeforeEach
    void setUp() {
        customAuthenticationFailureHandlerUnderTest = new CustomAuthenticationFailureHandler();
    }

    @Test
    void testOnAuthenticationFailure() throws Exception {
        final HttpServletRequest httpServletRequest = new MockHttpServletRequest();
        final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        final AuthenticationException e = null;
        customAuthenticationFailureHandlerUnderTest.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
