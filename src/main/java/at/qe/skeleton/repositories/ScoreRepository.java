package at.qe.skeleton.repositories;

import at.qe.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends AbstractRepository<Score, Integer> {

    List<Score> findAllByUser(User user);
    List<Score> findAllByGame(Game game);

    //@Query("SELECT s FROM Score s WHERE s.game = :game AND s.team = :team")
    List<Score> findAllByGameAndTeam(@Param("game") Game game, @Param("team") Team team);
}
