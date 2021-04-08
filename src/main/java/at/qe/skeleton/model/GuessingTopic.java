package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class GuessingTopic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guessingTopicId;

    private String guessingTopicName;

    @ManyToOne
    private Entry entry;

    //@OneToMany(mappedBy = "guessingTopic")
    //private List<Game> games;

    public GuessingTopic() {
    }

    public GuessingTopic(int guessingTopicId, String guessingTopicName) {
        this.guessingTopicId = guessingTopicId;
        this.guessingTopicName = guessingTopicName;
    }

    public int getGuessingTopicId() {
        return guessingTopicId;
    }

    public void setGuessingTopicId(int guessingTopicId) {
        this.guessingTopicId = guessingTopicId;
    }

    public String getGuessingTopicName() {
        return guessingTopicName;
    }

    public void setGuessingTopicName(String guessingTopicName) {
        this.guessingTopicName = guessingTopicName;
    }
}