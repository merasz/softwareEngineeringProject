package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;

import java.util.List;

public interface ScoreRepository extends AbstractRepository<Score, Integer> {

    List<Score> findAllByUser(User user);
    List<Score> findAllByGame(Game game);
    List<Score> findAllByGameAndTeam(Game game, Team team);
}
