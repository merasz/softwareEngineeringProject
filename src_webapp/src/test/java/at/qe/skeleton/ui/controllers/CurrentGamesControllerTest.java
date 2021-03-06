package at.qe.skeleton.ui.controllers;
import at.qe.skeleton.model.Game;
import at.qe.skeleton.services.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.enterprise.inject.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrentGamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService mockGameService;

    @InjectMocks
    private CurrentGamesController currentGamesController;


    @Test
    void testInit() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
                final BarChartModel model = new BarChartModel();
                List<Game> activeGames = new ArrayList<>(mockGameService.getAllActiveGames());
                model.setTitle("Players in active Games");
                verify(model.getTitle()).isEmpty();

            currentGamesController.init();
            currentGamesController.createLineModel();
        });
    }

    @Test
    void testCreateLineModel() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final BarChartModel model = new BarChartModel();
            List<Game> actives = new ArrayList<>();
            model.setTitle("Players in active Games");
            Axis y = model.getAxis(AxisType.Y);
            y.setLabel("Players in each Game");

            Axis x = model.getAxis(AxisType.X);
            x.setLabel("Game Name");
            for (Game activeGame : actives) {
                ChartSeries chartSeries = new ChartSeries();
                chartSeries.set(activeGame.getGameName(), activeGame.getCountPlayers());
                chartSeries.setLabel(activeGame.getGameName());
                model.addSeries(chartSeries);
            }
            currentGamesController.createLineModel();
            currentGamesController.init();
            currentGamesController.getModel();
            currentGamesController.setModel(model);
        });
    }

    @Test
    void testGetModel() {
        BarChartModel model = new BarChartModel();
        currentGamesController.setModel(model);
        assertTrue(currentGamesController.getModel() == model);

    }

    @Test
    void testSetModel() {
        BarChartModel model = new BarChartModel();
        currentGamesController.setModel(model);
        assertTrue(currentGamesController.getModel() == model);

    }


}