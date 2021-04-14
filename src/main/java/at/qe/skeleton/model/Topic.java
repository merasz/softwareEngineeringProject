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
}