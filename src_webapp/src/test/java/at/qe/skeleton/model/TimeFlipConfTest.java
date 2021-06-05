package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static at.qe.skeleton.model.RequestType.PANTOMIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeFlipConfTest {

//    private TimeFlipConf timeFlipConfUnderTest;

//    @BeforeEach
//    void setUp() {
//        timeFlipConfUnderTest = new TimeFlipConf();
//    }

    @Test
    void testGetFacetId() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setFacetId(5);
        assertTrue(timeFlipConf.getFacetId() == 5);
    }

    @Test
    void testGetFacePoint() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setFacetPoint(2);
        assertTrue(timeFlipConf.getFacetPoint() == 2);
    }

    @Test
    void testGetRequestType() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setRequestType(PANTOMIME);
        assertTrue(timeFlipConf.getRequestType() == PANTOMIME);
    }

    @Test
    void testSetFacetId() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setFacetId(5);
        assertTrue(timeFlipConf.getFacetId() == 5);
    }

    @Test
    void testSetFacetPoint() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setFacetPoint(2);
        assertTrue(timeFlipConf.getFacetPoint() == 2);
    }

    @Test
    void testSetRequestType() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setRequestType(PANTOMIME);
        assertTrue(timeFlipConf.getRequestType() == PANTOMIME);
    }

    @Test
    void testGetTime() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setTime(10);
        assertTrue(timeFlipConf.getTime() == 10);
    }

    @Test
    void testSetTime() {
        TimeFlipConf timeFlipConf = new TimeFlipConf();
        timeFlipConf.setTime(10);
        assertTrue(timeFlipConf.getTime() == 10);
    }
}
