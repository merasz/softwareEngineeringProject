package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Term implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String termName;

    @ManyToOne
    private Topic topic;

    @ManyToMany
    private List<Score> scoresGuessedTerms;

    @ManyToMany
    private List<Score> scoresNotGuessedTerms;

    public Term() {
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Term(String termName, Topic topic) {
        this.termName = termName;
        this.topic = topic;
    }
}
