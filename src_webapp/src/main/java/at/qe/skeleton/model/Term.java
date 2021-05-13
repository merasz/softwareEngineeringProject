package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Term implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String termName;

    @ManyToOne(fetch = FetchType.EAGER)
    private Topic topic;

    public Term() {
    }

    public Term(String termName, Topic topic) {
        this.termName = termName;
        this.topic = topic;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
