package at.qe.skeleton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GameTopicCount {

    @Id
    private int id;

    @ManyToOne
    private Topic topic;
    private int gameCount;

    public GameTopicCount() {
    }

    public GameTopicCount(Topic topic, Long gameCount) {
        this.topic = topic;
        this.gameCount = gameCount.intValue();
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
}
