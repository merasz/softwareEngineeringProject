package at.qe.skeleton.repositories;

import at.qe.skeleton.model.*;
import org.omnifaces.cdi.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ScoreRepository extends AbstractRepository<Score, Integer> {

    List<Score> findAllByUser(User user);
    List<Score> findAllByGame(Game game);
    List<Score> findAllByGameAndTeam(Game game, Team team);
    Score findFirstByUserAndGame(User user, Game game);

    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),SUM(s.totalRoundScore),s.team,s.game) from Score s WHERE s.team in :teams GROUP BY s.game.gameId")
    List<Score> findGameScoresByUser(@Param(name = "teams") List<Team> teams);

    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),SUM(s.totalRoundScore),s.team,s.game) from Score s WHERE s.game = :game GROUP BY s.team.teamId")
    List<Score> findGameScoresByGame(@Param(name = "game") Game game);

    @Query("SELECT count( distinct s.game) from Score s WHERE s.team in :teams")
    int countGamesByTeam(@Param(name = "teams") List<Team> teams);

    @Query("SELECT count(distinct s.game) from Score s WHERE s.team in :teams GROUP BY s.game.gameId HAVING SUM(s.totalRoundScore) >= s.game.scoreToWin")
    Integer countWonGamesByUser(@Param(name = "teams") List<Team> teams);

    @Query("SELECT count(distinct s.game) from Score s WHERE s.team in :teams GROUP BY s.game.gameId HAVING SUM(s.totalRoundScore) < s.game.scoreToWin")
    Integer countLostGamesByUser(@Param(name = "teams") List<Team> teams);

    @Query("select s.user from Score s group by s.user order by sum(s.totalRoundScore) desc")
    List<User> getTopPlayersUsernames();

    @Query("select sum(s.totalRoundScore) from Score s group by s.user order by sum(s.totalRoundScore) desc")
    List<Integer> getTopPlayersScores();
}
