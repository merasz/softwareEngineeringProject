package at.qe.skeleton.services;

import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Scope("application")
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public Game createGame() {

    }

    public Game haltGame(Game game) {

    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
        for (Score s : scoreRepository.findAllByGame(game)) {
            scoreRepository.delete(s);
        }
    }

    //total game score stats
    public Score getGameScores(Game game) {
        return sumScores(game.getScores());
    }

    //scores per team
    public List<Score> getTeamScores(Game game) {
        List<Score> scores = new ArrayList<>();
        for (Team t : game.getTeams()) {
            scores.add(sumScores(scoreRepository.findAllByGameAndTeam(game, t)));
        }
        return scores;
    }

    private Score sumScores(List<Score> scores) {
        Score score = new Score();
        for (Score s : scores) {
            //sum up all corresponding score-entries
            //save to new Score
        }
        return score;
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }
}
