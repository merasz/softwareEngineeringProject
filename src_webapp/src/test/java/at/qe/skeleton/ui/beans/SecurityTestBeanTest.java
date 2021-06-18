package at.qe.skeleton.ui.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityTestBeanTest {

    private SecurityTestBean securityTestBeanUnderTest;

    @BeforeEach
    void setUp() {
        securityTestBeanUnderTest = new SecurityTestBean();
    }

    @Test
    void testGetTestString() {
        securityTestBeanUnderTest.setTestString("test");
        assertThat(securityTestBeanUnderTest.getTestString() == "test");
    }

    @Test
    void testSetTestString() {
        securityTestBeanUnderTest.setTestString("test");
        assertThat(securityTestBeanUnderTest.getTestString() == "test");
    }

    @Test
    void testIsShowOkDialog() {
        assertFalse(securityTestBeanUnderTest.isShowOkDialog());
    }

    @Test
    void testGetPerformedAction() {
        String performedAction = "";
        assertThat(securityTestBeanUnderTest.getPerformedAction() == performedAction);
    }

    @Test
    void testDoAdminAction() {
        securityTestBeanUnderTest.doAdminAction();
    }

    @Test
    void testDoManagerAction() {
        securityTestBeanUnderTest.doManagerAction();
    }

    @Test
    void testDoPlayerAction() {
        securityTestBeanUnderTest.doPlayerAction();
    }

    @Test
    void testDoHideOkDialog() {
        securityTestBeanUnderTest.doHideOkDialog();
    }
}
