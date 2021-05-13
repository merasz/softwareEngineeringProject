package at.qe.skeleton.repositories;

import at.qe.skeleton.model.*;
import org.omnifaces.cdi.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends AbstractRepository<Score, Integer> {

    /**
     * Function which return all scores for a user
     *
     * @param user
     * @return list of scores
     */
    List<Score> findAllByUser(User user);

    /**
     * Function returns score from each user in the game.
     * @param teams
     * @return list of scores
     */
    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),SUM(s.totalRoundScore),s.team,s.game) from Score s WHERE s.team in :teams GROUP BY s.game.gameId")
    List<Score> findGameScoresByUser(@Param(name = "teams") List<Team> teams);

    /**
     * Function returns all produced scores from a game.
     * @param game
     * @return list of scores
     */
    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),SUM(s.totalRoundScore),s.team,s.game) from Score s WHERE s.game = :game GROUP BY s.team.teamId")
    List<Score> findGameScoresByGame(@Param(name = "game") Game game);

    /**
     * Function which counts the scores for a team.
     *
     * @param teams
     * @return int
     */
    @Query("SELECT count( distinct s.game) from Score s WHERE s.team in :teams")
    int countGamesByTeam(@Param(name = "teams") List<Team> teams);

    /**
     * Function which counts how many games a user won
     * @param teams
     * @return Integer
     */
    @Query("SELECT count(distinct s.game) from Score s WHERE s.team in :teams GROUP BY s.game.gameId HAVING SUM(s.totalRoundScore) >= s.game.scoreToWin")
    Integer countWonGamesByUser(@Param(name = "teams") List<Team> teams);

    /**
     * Function which counts how many games a user lost.
     * @param teams
     * @return Integer
     */
    @Query("SELECT count(distinct s.game) from Score s WHERE s.team in :teams GROUP BY s.game.gameId HAVING SUM(s.totalRoundScore) < s.game.scoreToWin")
    Integer countLostGamesByUser(@Param(name = "teams") List<Team> teams);

    /**
     * Function which returns a list of Users desc of their scores they ever reached in all their games.
     *
     * @return list of users
     */
    @Query("select s.user from Score s group by s.user order by sum(s.totalRoundScore) desc")
    List<User> getTopPlayersUsernames();

    /**
     * Returns the highest scores of each user sorted by desc, highest score of a user first.
     *
     * @return List of integer
     */
    @Query("select sum(s.totalRoundScore) from Score s group by s.user order by sum(s.totalRoundScore) desc")
    List<Integer> getTopPlayersScores();

    /**
     * Function which returns the players of a game sorted by their scores, highest first.
     * @return
     */
    @Query("select s.user from Score s group by s.game.gameId, s.user.username order by sum(s.totalRoundScore) desc")
    List<User> getTopPlayersInAGame();

    /**
     * Function which returns the users/players score of a game, desc sorted by highest score;
     * @return list of Integers
     */
    @Query("select sum(s.totalRoundScore) from Score s group by s.game.gameId, s.user.username order by sum(s.totalRoundScore) desc")
    List<Integer> getTopPlayersInAGameScore();

    /**
     * Function which returns the teams for a specified game.
     * @param game
     * @return List of teams
     */
    @Query("select Distinct s.team from Score as s where s.game.gameId = :game")
    List<Team> getTeamsForGame(Integer game);

    /**
     * Function which returns the scores as int from a game grouped by a team.
     * @param gameId
     * @param teams
     * @return List of integers
     */
    @Query("select sum(s.totalRoundScore) from Score as s where s.game.gameId = :gameId group by :teams")
    List<Integer> getForTeamsByGameScore(Integer gameId, List<Team> teams);

    /**
     * Function which returns the scores from a game of a team.
     *
     * @param game
     * @return list of scores
     */
    @Query("SELECT new at.qe.skeleton.model.Score (MAX(s.scoreId),SUM(s.totalRoundScore),s.team,s.game) from Score s WHERE s.game = :game GROUP BY s.team.teamId")
    List<Score> getScoresForTeamsByGame(@Param(name = "game") Game game);


    /**
     * Function which returns the participating teams in a game sorted by their total team score, desc.
     *
     * @param game
     * @return List of teams
     */
    @Query("select s.team from Score s WHERE s.game = :game group by s.game.gameId, s.team order by sum(s.totalRoundScore) desc")
    List<Team> getTopTeamInAGame(@Param(name = "game") Game game);
}
