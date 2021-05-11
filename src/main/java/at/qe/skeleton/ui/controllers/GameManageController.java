package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.GameStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Scope("view")
public class GameManageController extends GameController implements Serializable {

    @Autowired
    private GameStartService gameStartService;

    public String getTimePlayed() {
        try {
            return new SimpleDateFormat("hh:mm:ss")
                    .format(new Date(getGameStatsService().getSecondsPlayed(getGame()) * 1000L));
        } catch (UnsupportedOperationException e) {
            return "...unfinished";
        }
    }

}
