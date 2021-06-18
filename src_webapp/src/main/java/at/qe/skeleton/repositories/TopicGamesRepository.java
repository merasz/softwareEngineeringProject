package at.qe.skeleton.repositories;

import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Team;
import org.omnifaces.cdi.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicGamesRepository extends AbstractRepository<GameTopicCount, Integer>{

    /**
     * Returns the won games with its played topic back
     *
     * @param teams
     * @return list of GameTopicCount
     */
    //@Query("SELECT new at.qe.skeleton.model.GameTopicCount (s.game.topic,count(distinct s.game)) from Score s WHERE s.team in :teams GROUP BY s.game.gameId, s.game.topic.topicName HAVING SUM(s.totalRoundScore) >= s.game.scoreToWin")
    @Query("SELECT new at.qe.skeleton.model.GameTopicCount (s.game.topic,count(distinct s.game)) from Score s WHERE s.team in :teams GROUP BY s.game.gameId, s.game.topic.topicName,s.game.scoreToWin HAVING SUM(s.totalRoundScore) >= s.game.scoreToWin")
    List<GameTopicCount> countWonGamesByUserAndTopic(@Param(name = "team") List<Team> teams);

}
