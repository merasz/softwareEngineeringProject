package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class TermsController extends Controller implements Serializable {
    @Autowired
    private TermsService termsService;

    private List<Topic> topics;
    private List<Term> terms;
    private Topic topic = new Topic();
    private Term term;

    public boolean setNewTopic(){
        this.topic.setTopicName("");
        this.topic.setTerms(terms);

        return true;
    }

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
        try {
            termsService.saveTopic(name, new Topic());
            displayInfo("Topic created", "New topic successfully created.");
        } catch (IllegalArgumentException e) {
            displayError("Topic not created", e.getMessage());
        }
    }

    public void addTerm(String name) {
        try {
            termsService.saveTerm(name, this.topic, new Term());
            displayInfo("Term created", "New term successfully created.");
        } catch (IllegalArgumentException e) {
            displayError("Term not created", e.getMessage());
        }
    }

    public void editTopic(String name) {
        try {
            termsService.saveTopic(name, topic);
            displayInfo("Topic updated", "Topic successfully changed.");
        } catch (IllegalArgumentException e) {
            displayError("Topic not updated", e.getMessage());
        }
    }

    public void editTerm(String name, Topic topic) {
        try {
            termsService.saveTerm(name, topic, term);
            displayInfo("Term updated", "Term successfully changed.");
        } catch (IllegalArgumentException e) {
            displayError("Term not updated", e.getMessage());
        }
    }

    public void deleteTopic() {
        try {
            termsService.deleteTopic(topic);
            displayInfo("Topic deleted", "Topic successfully deleted.");
        } catch (IllegalArgumentException e) {
            displayError("Topic not deleted", e.getMessage());
        }
    }

    public void deleteTerm() {
        termsService.getTermsRepository().delete(term);
        displayInfo("Term deleted", "Term successfully deleted.");
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void importTerms() {
        try {
            termsService.importTerms();
        } catch (FileNotFoundException e) {
            displayError("File not found", "Error: File not found.");
        } catch (ParseException e) {
            displayError("Parse error", "Error: File could not be read.");
        }
    }

    public TermsService getTermsService() {
        return termsService;
    }
}
