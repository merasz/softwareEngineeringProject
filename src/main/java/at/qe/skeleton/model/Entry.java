package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guessingEntryId;
    private int guessingTopicId;
    private String guessingEntryQuestion;

    @OneToMany(mappedBy = "entry")
    private List<GuessingTopic> guessingTopics;

    public Entry() {
    }

    public Entry(int guessingEntryId, int guessingTopicId, String guessingEntryQuestion) {
        this.guessingEntryId = guessingEntryId;
        this.guessingTopicId = guessingTopicId;
        this.guessingEntryQuestion = guessingEntryQuestion;
    }

    public int getGuessingEntryId() {
        return guessingEntryId;
    }

    public void setGuessingEntryId(int guessingEntryId) {
        this.guessingEntryId = guessingEntryId;
    }

    public int getGuessingTopicId() {
        return guessingTopicId;
    }

    public void setGuessingTopicId(int guessingTopicId) {
        this.guessingTopicId = guessingTopicId;
    }

    public String getGuessingEntryQuestion() {
        return guessingEntryQuestion;
    }

    public void setGuessingEntryQuestion(String guessingEntryQuestion) {
        this.guessingEntryQuestion = guessingEntryQuestion;
    }
}
