package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.*;
import java.io.*;
import java.util.*;

@Controller
@Scope("view")
public class CurrentGamesController implements Serializable {

    private BarChartModel model;

    @Autowired
    private GameService gameService;

    @PostConstruct
    public void init() {
        createLineModel();
    }

    public void createLineModel() {
        model = new BarChartModel();
        List<Game> activeGames = new ArrayList<>(gameService.getAllActiveGames());
        model.setTitle("Players in active Games");

        Axis y = model.getAxis(AxisType.Y);
        y.setLabel("Players in each Game");

        Axis x = model.getAxis(AxisType.X);
        x.setLabel("Game Name");

        for (Game activeGame : activeGames) {
            ChartSeries chartSeries = new ChartSeries();
            chartSeries.set(activeGame.getGameName(), activeGame.getCountPlayers());
            chartSeries.setLabel(activeGame.getGameName());
            model.addSeries(chartSeries);
        }
    }

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }
}
