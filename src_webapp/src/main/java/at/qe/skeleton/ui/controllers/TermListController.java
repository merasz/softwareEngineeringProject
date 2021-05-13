package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Scope("view")
public class TermListController extends Controller implements Serializable {
    @Autowired
    private TermsService termsService;

    @Autowired
    private TopicService topicService;

    //private Collection<Topic> topics;
    private List<Term> terms;
    private Topic topic;
    private Term term;

    public Collection<Term> getTerms() {
        return termsService.getAllTerms();
    }

    public List<Term> getTermsByTopic() {
        if(topic == null) {
            return new ArrayList<>();
        }
        return termsService.getTermsForTopic(topic);
    }

    public void doSetTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /*
    private void doCreateNewTerm() {
        term = new Term();
    }
     */

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Collection<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    public List<Term> getTermsOfTopic() {
        terms = termsService.getTermsRepository().findAllByTopic(this.topic);
        return terms;
    }

}
