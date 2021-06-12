package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.services.RaspberryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaspberryControllerTest {

    @Mock
    private RaspberryService raspberryService;

    @InjectMocks
    private RaspberryController raspberryController;

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoReloadRaspberry() {
        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        when(raspberryService.loadRaspberryByIp("ipAddress")).thenReturn(raspberry);

        raspberryController.doReloadRaspberry();
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoSaveRaspberry() {
        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        when(raspberryService.saveRaspberry(any(Raspberry.class))).thenReturn(raspberry);

        raspberryController.doSaveRaspberry();

    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoDeleteRaspberry() {
        raspberryController.doDeleteRaspberry();

        verify(raspberryService).deleteRaspberry(any(Raspberry.class));
    }

    @Test
    void testDoCreateRaspberry() {

        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        when(raspberryService.createNewRaspberry()).thenReturn(raspberry);

        raspberryController.doCreateRaspberry();


    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testDoInvalidateApiKey() {

        final Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId(0);
        raspberry.setHostname("hostname");
        raspberry.setInUse(false);
        raspberry.setIpAddress("ipAddress");
        raspberry.setApiKey("apiKey");
        when(raspberryService.invalidateApiKey(any(Raspberry.class))).thenReturn(raspberry);

        raspberryController.doInvalidateApiKey();

    }

    @Test
    void testSetRaspberry() {
        Raspberry raspberry = new Raspberry();
        raspberryController.setRaspberry(raspberry);
        assertTrue(raspberryController.getRaspberry() == raspberry);
    }

    @Test
    void testGetRaspberry() {
        Raspberry raspberry = new Raspberry();
        raspberryController.setRaspberry(raspberry);
        assertTrue(raspberryController.getRaspberry() == raspberry);

    }

    @Test
    void testSetIpAddress() {
        raspberryController.setIpAddress("ipaddress");
        assertTrue(raspberryController.getIpAddress() == "ipaddress");

    }
    @Test
    void testGetIpAddress() {
        raspberryController.setIpAddress("ipaddress");
        assertTrue(raspberryController.getIpAddress() == "ipaddress");
    }
    @Test
    void testGetAction() {
        raspberryController.setAction("no");
        assertTrue(raspberryController.getAction() == "no");
    }
    @Test
    void testSetAction() {
        raspberryController.setAction("no");
        assertTrue(raspberryController.getAction() == "no");
    }
    @Test
    void getRaspberryService(){
        RaspberryService raspberryService = new RaspberryService();
        raspberryService.getAllRaspberries();
        assertTrue(raspberryController.getRaspberryService() == raspberryService);
    }
}

