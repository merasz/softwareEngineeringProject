package at.qe.skeleton.repositories;

import at.qe.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends AbstractRepository<Score, Integer> {

    List<Score> findAllByUser(User user);
    List<Scores> findAllByGame(Game game);
}
