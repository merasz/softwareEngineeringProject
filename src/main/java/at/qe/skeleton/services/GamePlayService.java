package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Scope("application")
public class GamePlayService extends GameService {

    //region next round
    //TODO
    public Game nextTurn(Game game) {
        TeamPlayer tp = selectNextPlayer(game);
        setCurrentTeam(tp.team);
        setCurrentPlayer(tp.player);
        //selectNextTerm();
        game.incrementNrRound();
        getGameRepository().save(game);
        return game;
    }

    private TeamPlayer selectNextPlayer(Game game) {
        return getPlayers().get((game.getNrRound() - 1) % getNumPlayers());
    }
    //endregion

    public int countGuessesForWin(Game game) {
        return game.getScoreToWin() / SUCCESS_POINTS;
    }

    public Game stopGame(Game game) {
        game.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        return game;
    }

    public Game pauseGame(Game game) {
        game.setPausedTime(Timestamp.valueOf(LocalDateTime.now()));
        return game;
    }
}
