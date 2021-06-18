package at.qe.skeleton.api.service;

import at.qe.skeleton.api.model.PiRequest;
import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.RaspberryRepository;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.RaspberryService;
import at.qe.skeleton.ui.controllers.gameSockets.GamePlaySocketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class APIServiceTest {

    private APIService apiServiceUnderTest;

    @BeforeEach
    void setUp() {
        apiServiceUnderTest = new APIService();
        apiServiceUnderTest.raspberryRepository = mock(RaspberryRepository.class);
        apiServiceUnderTest.raspberryService = mock(RaspberryService.class);
        apiServiceUnderTest.gameService = mock(GameService.class);
        apiServiceUnderTest.gamePlaySocketController = mock(GamePlaySocketController.class);
    }

    @Test
    void testGenerateApiKeyForRaspberry() {
        final Raspberry raspberry = new Raspberry();
        when(apiServiceUnderTest.raspberryRepository.save(any(Raspberry.class))).thenReturn(raspberry);
        final String result = apiServiceUnderTest.generateApiKeyForRaspberry(raspberry);
        assertThat(result).isNotEqualTo("6jFR96z6TXSqOWJp6cdLg0HgGvJZEmbIKlbA");
    }

    @Test
    void testUpdateTimeFlip() {
        final PiRequest piRequest = new PiRequest();
        final Raspberry raspberry = new Raspberry();
        when(apiServiceUnderTest.raspberryService.loadRaspberryByIp("ipAddress")).thenReturn(raspberry);
        final Game game = new Game();
        when(apiServiceUnderTest.gameService.getRunningGameByRaspberry(0)).thenReturn(game);
        apiServiceUnderTest.updateTimeFlip(piRequest);
    }
}
