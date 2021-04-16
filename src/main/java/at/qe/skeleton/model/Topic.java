package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String topicName;

    @OneToMany(mappedBy = "topic")
    private List<Game> games;

    @OneToMany(mappedBy = "topic")
    private List<Term> terms;

    public Topic() {
    }

    //TODO: add new parameters to constructor
    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String guessingTopicName) {
        this.topicName = guessingTopicName;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}