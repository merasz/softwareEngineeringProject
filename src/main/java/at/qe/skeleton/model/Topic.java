package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String topicName;

    @OneToMany(mappedBy = "topic")
    private List<Game> games;

    @OneToMany(mappedBy = "topic")
    private List<Term> terms;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @ManyToOne(optional = true)
    private Topic updateTopic;



    public Topic() {
    }

    //TODO: add new parameters to constructor
    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isNew(){
        return (null == createDate);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Topic getUpdateTopic() {
        return updateTopic;
    }

    public void setUpdateTopic(Topic updateTopic) {
        this.updateTopic = updateTopic;
    }

    @Override
    public String toString() {
        return topicName;
    }
}