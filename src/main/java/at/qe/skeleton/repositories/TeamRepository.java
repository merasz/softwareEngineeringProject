package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;

import at.qe.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TeamRepository extends AbstractRepository<Team,Integer>{

    @Query("SELECT t FROM Team t WHERE t.game.gameId = :game")
    List<Team> findByGame(@Param("game") Integer game);

    List<Team> findAllByTeamPlayers(User user);
}
