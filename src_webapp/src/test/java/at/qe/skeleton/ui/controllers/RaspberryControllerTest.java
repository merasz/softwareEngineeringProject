package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.RaspberryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}