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

    @Query("select s.user from Score s group by s.game.gameId, s.user.username order by sum(s.totalRoundScore) desc")
    List<User> getTopPlayersInAGame();

    @Query("select sum(s.totalRoundScore) from Score s group by s.game.gameId, s.user.username order by sum(s.totalRoundScore) desc")
    List<Integer> getTopPlayersInAGameScore();

    @Query("select sum(s.totalRoundScore) from Score as s  group by s.game.gameId, s.team.teamId order by sum(s.totalRoundScore) desc")
    List<Integer> getTeamScoreForGame();

    @Query("select s.game.gameId from Score as s  group by s.game.gameId, s.team.teamId order by sum(s.totalRoundScore) desc")
    List<Integer> getAllGameIdsForTeamScoreForGame();

    @Query("select s.team.teamId from Score as s  group by s.game.gameId, s.team.teamId order by sum(s.totalRoundScore) desc")
    List<Integer> getAllTeamIdsForTeamScoreForGame();

    @Query("select Distinct s.team from Score as s where s.game.gameId = :game")
    List<Team> getTeamsForGame(Integer game);

    @Query("select sum(s.totalRoundScore) from Score as s where s.game.gameId = :gameId group by :teams")
    List<Integer> getForTeamsByGameScore(Integer gameId, List<Team> teams);

    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),SUM(s.totalRoundScore),s.team,s.game) from Score s WHERE s.game = :game GROUP BY s.team.teamId")
    List<Score> getScoresForTeamsByGame(@Param(name = "game") Game game);

    @Query("select s.team from Score s WHERE s.game = :game group by s.game.gameId, s.team order by sum(s.totalRoundScore) desc")
    List<Team> getTopTeamInAGame(@Param(name = "game") Game game);

}
