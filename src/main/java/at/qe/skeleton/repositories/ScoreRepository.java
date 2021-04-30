package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import org.omnifaces.cdi.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends AbstractRepository<Score, Integer> {

    List<Score> findAllByUser(User user);
    List<Score> findAllByGame(Game game);
    List<Score> findAllByGameAndTeam(Game game, Team team);
    Score findFirstByUserAndGame(User user, Game game);

    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),MAX(s.totalRoundScore),s.user,s.team,s.game) from Score s WHERE s.user = :user GROUP BY s.team.teamId")
    List<Score> findGameScoresByUser(@Param(name = "user") User user);
}
