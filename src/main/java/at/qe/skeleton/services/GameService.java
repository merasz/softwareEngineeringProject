package at.qe.skeleton.services;

import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    public GameRepository getGameRepository() {
        return gameRepository;
    }
}
