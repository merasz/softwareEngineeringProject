package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends AbstractRepository<Game, Integer> {

    Game findByGameId(Integer integer);

    @Query("SELECT g FROM Game g WHERE g.active = true AND :r = g.raspberry.raspberryId ORDER BY g.gameId DESC")
    List<Game> internalFindActiveGameByRaspberry(@Param("r") int raspberryId, Pageable page);

    default Game findActiveGameByRaspberry(int raspberryId) {
        List<Game> games = internalFindActiveGameByRaspberry(raspberryId, PageRequest.of(0,1));
        if (games.isEmpty()) {
            return null;
        } else {
            return games.get(0);
        }
    }

    @Query("select g.topic from Game g group by g.topic order by count(*) desc")
    List<Topic> getMostPopularTopics();

}
