package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.RaspberryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaspberryListControllerTest {

    @Mock
    private RaspberryService raspberryService;

    @InjectMocks
    private RaspberryListController raspberryListController;

    @Test
    void testGetRaspberries() {
        final User user = new User();
        final Collection<Raspberry> raspberries = Arrays.asList(new Raspberry());
        when(raspberryService.getAllRaspberries()).thenReturn(raspberries);
        final Collection<Raspberry> result = raspberryListController.getRaspberries();
    }

    @Test
    void testGetRaspberries_RaspberryServiceReturnsNoItems() {
        when(raspberryService.getAllRaspberries()).thenReturn(Collections.emptyList());
        final Collection<Raspberry> result = raspberryListController.getRaspberries();
    }
}
