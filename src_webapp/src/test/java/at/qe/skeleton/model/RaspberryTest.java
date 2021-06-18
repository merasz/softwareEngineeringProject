package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RaspberryTest {
    @Test
    void testGetRaspberryId() {
        Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(10);
        assertTrue(raspberry.getRaspberryId() == 10);
    }
    @Test
    void testSetRaspberryId() {
        Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(10);
        assertTrue(raspberry.getRaspberryId() == 10);
    }

    @Test
    void testGetHostname() {
        Raspberry raspberry = new Raspberry();
        raspberry.setHostname("hostname");
        assertTrue(raspberry.getHostname() == "hostname");
    }

    @Test
    void testSetHostname() {
        Raspberry raspberry = new Raspberry();
        raspberry.setHostname("hostname");
        assertTrue(raspberry.getHostname() == "hostname");
    }


    @Test
    void testGetUsers() {
        Raspberry user = new Raspberry();
        List<User> users = new ArrayList<>();
        user.setUser(users);
        assertTrue(user.getUser() == users);
    }
    @Test
    void testSetUsers() {
        Raspberry user = new Raspberry();
        List<User> users = new ArrayList<>();
        user.setUser(users);
        assertTrue(user.getUser() == users);
    }

    @Test
    void testIsInUse() {
        Raspberry raspberry = new Raspberry();
        raspberry.isInUse();
        assertTrue(raspberry.isInUse() == false);
    }

    @Test
    void testSetInUse() {
        Raspberry raspberry = new Raspberry();
        raspberry.isInUse();
        assertTrue(raspberry.isInUse() == false);
    }

    @Test
    void testGetIpAddress() {
        Raspberry raspberry = new Raspberry();
        raspberry.setIpAddress("ipadress");
        assertTrue(raspberry.getIpAddress() == "ipadress");
    }

    @Test
    void testSetIpAddress() {
        Raspberry raspberry = new Raspberry();
        raspberry.setIpAddress("ipadress");
        assertTrue(raspberry.getIpAddress() == "ipadress");

    }


    @Test
    void testSetApiKey() {
        Raspberry raspberry = new Raspberry();
        raspberry.setApiKey("key");
        assertTrue(raspberry.getApiKey() == "key");

    }
    @Test
    void testGetApiKey() {
        Raspberry raspberry = new Raspberry();
        raspberry.setApiKey("key");
        assertTrue(raspberry.getApiKey() == "key");

    }
}


