package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Task;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

@Component
@Scope("view")
public class GamePlayService extends GameService {

    @Autowired
    private TeamRepository teamRepository;

    private Game game;
    private Team team;
    private User user;
    private Task task;
    private String timerString;
    private boolean guessAccepted = false;

    /*
    public Game joinGame(User user) throws NoSuchElementException {
        this.user = user;
        this.game = getGameRepository().findActiveGameByRaspberry(user.getRaspberry().getRaspberryId());

        if (game == null ) {
            throw new NoSuchElementException("No active game found. Ask a game manager to create a new game.");
        }

        this.team = getTeamService().createTeam(game);
        this.game.getTeamList().add(team);
        return game;
    }

    public void selectPlayer(User user) {
        team.getTeamPlayers().add(user);
        this.team = getTeamService().saveTeam(team);
        this.game = getGameRepository().save(game);
        getGameJoinController().onSelect(user);
    }

    public void startGame(String teamName) throws IllegalArgumentException {
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException("Give your team a name first.");
        } else if (!teamReady()) {
            throw new IllegalArgumentException("You haven't assigned enough team mates yet.");
        }
        this.team.setTeamName(teamName);
        getTeamService().saveTeam(team);
        getGameRepository().save(game);
    }

    public boolean teamReady() {
        return game.getTeamSize() == team.getTeamPlayers().size();
    }
    */

    //region guessing round with timer loop
    //TODO: where task?
    public void runGameRound(Game game) {
        //get task
        long seconds = task.getDurationInMinutes() * 60L;
        LocalDateTime roundStartTime = LocalDateTime.now();
        LocalDateTime roundEndTime = roundStartTime.plusSeconds(seconds);
        long remainingSeconds = roundStartTime.until(roundEndTime, ChronoUnit.SECONDS);
        long before = remainingSeconds;
        timerString = buildTimeString(remainingSeconds);

        while(remainingSeconds >= 0 && !guessAccepted) {
            remainingSeconds = LocalDateTime.now().until(roundEndTime, ChronoUnit.SECONDS);
            if (remainingSeconds < before) {
                before = remainingSeconds;
                timerString = buildTimeString(remainingSeconds);
            }
        }
    }

    private String buildTimeString(long remainingSeconds) {
        return String.format("%02d", remainingSeconds / 60) + ":" + String.format("%02d", remainingSeconds % 60);
    }

    public String getTimerString() {
        return timerString;
    }

    public void setGuessAccepted(boolean accepted) {
        this.guessAccepted = accepted;
    }
    //endregion

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

    public Task getTask(Game game) {
        //TODO
        return null;
    }

    private TeamPlayer selectNextPlayer(Game game) {
        return getPlayers().get((game.getNrRound() - 1) % getNumPlayers());
    }
    //endregion

    public String updateTimer(Game game) {
        return null;
    }

    public int countGuessesForWin(Game game) {
        return game.getScoreToWin() / SUCCESS_POINTS;
    }

    public Game stopGame(Game game) {
        game.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        getGameRepository().save(game);
        return game;
    }

    public Game pauseGame(Game game, boolean paused) {
        if (paused) {
            game.setPausedTime(null);
        } else {
            game.setPausedTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        getGameRepository().save(game);
        return game;
    }

    //region getter & setter
    public Game getGame() {
        return game;
    }

    //endregion
}
