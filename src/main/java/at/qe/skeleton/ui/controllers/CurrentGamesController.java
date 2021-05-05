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

    private LineChartModel model;

    @Autowired
    private GameService gameService;

    @PostConstruct
    public void init() {
        createLineModel();
    }

    public void createLineModel() {
        model = new LineChartModel();
        List<Game> activeGames = new ArrayList<>(gameService.getAllActiveGames());
        model.setTitle("Game ID / Current Players in each Game");

        for (Game activeGame : activeGames) {
            ChartSeries chartSeries = new ChartSeries();
            chartSeries.set(activeGame.getGameId(), activeGame.getCountPlayers());
            chartSeries.setLabel(activeGame.getGameName());
            model.addSeries(chartSeries);
        }
    }

    public LineChartModel getModel() {
        return model;
    }

    public void setModel(LineChartModel model) {
        this.model = model;
    }
}
