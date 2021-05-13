package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.RaspberryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaspberryListControllerTest {

    @Mock
    private RaspberryService mockRaspberryService;

    @InjectMocks
    private RaspberryListController raspberryListControllerUnderTest;

    @Test
    void testGetRaspberries() {
        final Raspberry raspberry = new Raspberry();
        final Collection<Raspberry> raspberries = Arrays.asList(raspberry);
        when(mockRaspberryService.getAllRaspberries()).thenReturn(raspberries);
        final Collection<Raspberry> result = raspberryListControllerUnderTest.getRaspberries();
    }

    @Test
    void testGetRaspberries_RaspberryServiceReturnsNoItems() {
        when(mockRaspberryService.getAllRaspberries()).thenReturn(Collections.emptyList());
        final Collection<Raspberry> result = raspberryListControllerUnderTest.getRaspberries();
    }
}
