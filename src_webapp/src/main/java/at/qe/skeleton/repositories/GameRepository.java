package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends AbstractRepository<Game, Integer> {
    /**
     * Function searchs in Repo if game exists with specified ID.
     *
     * @param integer
     * @return the game by its ID
     */
    Game findByGameId(Integer integer);

    /**
     * Takes the ID of a raspberry and returns the last created game associated with this raspberry.
     *
     * @param raspberryId
     * @param page
     * @return
     */
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

    /**
     * Function for game statistics, which returns a list of the most choosen topics in all ever played games
     *
     * @return list of Topics asc sorted by there count
     */
    @Query("select g.topic from Game g group by g.topic order by count(*) desc")
    List<Topic> getMostPopularTopics();

    /**
     * Function which returns all active games - all games which are played at the moment.
     *
     * @return list of games
     */
    @Query("Select g from Game g where g.active = 1 and g.startTime is not NULL and g.endTime is NULL")
    List<Game> findAllActive();


}
