package at.qe.skeleton.api.model;

import at.qe.skeleton.model.Team;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PiRequestTest {

    private PiRequest piRequestUnderTest;

    @BeforeEach
    void setUp() {
        piRequestUnderTest = new PiRequest();
    }

    @Test
    void testGetId() {
        piRequestUnderTest.setId(1L);
        assertThat(piRequestUnderTest.getId() == 1L);
    }

    @Test
    void testSetId() {
        piRequestUnderTest.setId(1L);
        assertThat(piRequestUnderTest.getId() == 1L);
    }

    @Test
    void testGetIpAddress() {
        String macAddress = new String();
        piRequestUnderTest.setIpAddress(macAddress);
        assertThat(piRequestUnderTest.getIpAddress() == macAddress);
    }

    @Test
    void testSetIpAddress() {
        String macAddress = new String();
        piRequestUnderTest.setIpAddress(macAddress);
        assertThat(piRequestUnderTest.getIpAddress() == macAddress);
    }

    @Test
    void testGetFacetId() {
        piRequestUnderTest.setFacetId(1);
        assertThat(piRequestUnderTest.getFacetId() == 1);
    }

    @Test
    void testSetFacetId() {
        piRequestUnderTest.setFacetId(1);
        assertThat(piRequestUnderTest.getFacetId() == 1);
    }

    @Test
    void testGetVal() {
        String val = new String();
        piRequestUnderTest.setVal(val);
        assertThat(piRequestUnderTest.getVal() == val);
    }

    @Test
    void testSetVal() {
        String val = new String();
        piRequestUnderTest.setVal(val);
        assertThat(piRequestUnderTest.getVal() == val);
    }

    @Test
    void testEquals() {
        final boolean result = piRequestUnderTest.equals("o");
        assertThat(result).isFalse();
    }

    @Test
    void testHashCode() {
        final int result = piRequestUnderTest.hashCode();
        assertThat(result).isEqualTo(29791);
    }
}
