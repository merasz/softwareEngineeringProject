package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guessingTopicId;

    private String guessingTopicName;

    @OneToMany(mappedBy = "topic")
    private List<Game> games;

    @OneToMany(mappedBy = "topic")
    private List<Term> terms;

    public Topic() {
    }

    public Topic(int guessingTopicId, String guessingTopicName) {
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