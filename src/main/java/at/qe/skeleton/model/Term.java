package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Term implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String termName;

    @ManyToOne
    private Topic topic;

    @ManyToMany
    private List<Score> scoresGuessedTerms;

    @ManyToMany
    private List<Score> scoresNotGuessedTerms;

    public Term() {
    }

    public Term(String termName, Topic topic, List<Score> scoresGuessedTerms, List<Score> scoresNotGuessedTerms) {
        this.termName = termName;
        this.topic = topic;
        this.scoresGuessedTerms = scoresGuessedTerms;
        this.scoresNotGuessedTerms = scoresNotGuessedTerms;
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

    public List<Score> getScoresGuessedTerms() {
        return scoresGuessedTerms;
    }

    public void setScoresGuessedTerms(List<Score> scoresGuessedTerms) {
        this.scoresGuessedTerms = scoresGuessedTerms;
    }

    public List<Score> getScoresNotGuessedTerms() {
        return scoresNotGuessedTerms;
    }

    public void setScoresNotGuessedTerms(List<Score> scoresNotGuessedTerms) {
        this.scoresNotGuessedTerms = scoresNotGuessedTerms;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
