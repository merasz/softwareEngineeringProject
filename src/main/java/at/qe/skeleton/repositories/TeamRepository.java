package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;

import at.qe.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TeamRepository extends AbstractRepository<Team,Integer>{

    /**
     * Function which returns the teams for a specified game.
     *
     * @param game
     * @return List of teams
     */
    @Query("SELECT t FROM Team t WHERE t.game.gameId = :game")
    List<Team> findByGame(@Param("game") Integer game);

    /**
     * Function which returns a list of Teams which is used to show up with who a player was playing already.
     *
     * @param user
     * @return List of teams
     */
    List<Team> findAllByTeamPlayers(User user);

    /**
     * Function which returns the team with the specified id.
     *
     * @param id
     * @return team
     */
    @Query("SELECT t from Team t WHERE t.teamId = :id")
    Team findByTeamId(@Param("id") Long id);

    /**
     * Function which returns a team by a specified game.
     *
     * @param user
     * @param game
     * @return team
     */
    Team findByTeamPlayersAndGame(User user, Game game);


}
