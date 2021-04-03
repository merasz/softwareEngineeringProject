package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.services.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class TermsController extends Controller implements Serializable {
    @Autowired
    private TermsService termsService;

    private List<Topic> topics;
    private List<Term> terms;
    private Topic topic;
    private Term term;

    public List<Topic> getAllTopics() {
        topics = termsService.getTopicRepository().findAll();
        return topics;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<Term> getTermsOfTopic() {
        terms = termsService.getTermsRepository().findAllByTopic(this.topic);
        return terms;
    }

    public void addTopic(String name) {
        if (topics.stream().map(Topic::getName).collect(Collectors.toList()).contains(name)) {
            displayError("Topic not created", "Topic already exists.");
        } else {
            termsService.saveTopic(name);
            displayInfo("Topic created", "New topic successfully created.");
        }
    }

    public void addTerm(String name) {
        if (terms.stream().map(Term::getName).collect(Collectors.toList()).contains(name)) {
            displayError("Term not created", "Term already exists.");
        } else {
            termsService.saveTerm(name, this.topic);
            displayInfo("Term created", "New term successfully created.");
        }
    }

    public void editTopic(String name) {
        if (topics.stream().map(Topic::getName).collect(Collectors.toList()).contains(name)) {
            displayError("Topic not changed", "Topic already exists.");
        } else {
            termsService.updateTopic(name, topic);
            displayInfo("Topic updated", "Topic successfully changed.");
        }
    }

    public void editTerm(String name, Topic topic) {
        if (terms.stream().map(Term::getName).collect(Collectors.toList()).contains(name)) {
            displayError("Term not changed", "Term already exists.");
        } else {
            termsService.updateTerm(name, topic, term);
            displayInfo("Term updated", "Term successfully changed.");
        }
    }

    public void deleteTopic() {
        try {
            termsService.deleteTopic(topic);
        } catch (IllegalArgumentException e) {
            displayError("Topic not deleted", e.getMessage());;
        }
    }

    public void deleteTerm() {
        termsService.getTermsRepository().delete(term);
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public TermsService getTermsService() {
        return termsService;
    }
}
