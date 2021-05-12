package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.RaspberryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RaspberryServiceTest {

    @Mock
    private RaspberryRepository mockRaspberryRepository;

    @InjectMocks
    private RaspberryService raspberryServiceUnderTest;

    //@Test
    void testGetAllRaspberries() {
        final List<Raspberry> raspberries = Arrays.asList(new Raspberry());
        when(mockRaspberryRepository.findAll()).thenReturn(raspberries);
        //final Collection<Raspberry> result = raspberryServiceUnderTest.getAllRaspberries();
        //verify
    }

    //@Test
    void testLoadRaspberryByIp() {
        final User user = new User();
        final Raspberry raspberry = new Raspberry();
        when(mockRaspberryRepository.findByIpAddress("ipAddress")).thenReturn(raspberry);
        final Raspberry result = raspberryServiceUnderTest.loadRaspberryByIp("ipAddress");
        //verify
    }

    //@Test
    void testCreateNewRaspberry() {
        final Raspberry result = raspberryServiceUnderTest.createNewRaspberry();
        //verify
    }

    //@Test
    void testDeleteRaspberry() {
        final Raspberry raspberry = new Raspberry();
        raspberryServiceUnderTest.deleteRaspberry(raspberry);
        verify(mockRaspberryRepository).delete(any(Raspberry.class));
    }

    //@Test
    void testSaveRaspberry() {
        final Raspberry raspberry = new Raspberry();
        when(mockRaspberryRepository.save(any(Raspberry.class))).thenReturn(raspberry);
        final Raspberry result = raspberryServiceUnderTest.saveRaspberry(raspberry);
    }

    //@Test
    void testInvalidateApiKey() {
        final Raspberry raspberry = new Raspberry();
        when(mockRaspberryRepository.save(any(Raspberry.class))).thenReturn(raspberry);
        final Raspberry result = raspberryServiceUnderTest.invalidateApiKey(raspberry);
    }
}
