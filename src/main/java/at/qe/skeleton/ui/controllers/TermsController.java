package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class TermsController extends Controller implements Serializable {
    @Autowired
    private TermsService termsService;

    @Autowired
    private TopicService topicService;

    private List<Topic> topics;
    private List<Term> terms;
    private Topic topic;
    private Term term;

    @PostConstruct
    public void init() {
        System.out.println("init Terms");
        doCreateNewTerm();
    }

    public void doCreateNewTerm() {
        System.out.println("doCreateNewTerm:");
        term = new Term();
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        System.out.println("current topic: " + topic.getTopicName());
        this.topic = topic;
        this.term.setTopic(topic);
        System.out.println("current topic from term : " + this.term.getTopic().getTopicName());
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void doSaveTerm(Topic topic) {
        System.out.println("doSave Terms");
        if(topic == null)
            System.out.println("topic null");
        else
            System.out.println("topicname " + topic.getTopicName());
        System.out.println("term string " + term.getTermName());
        term.setTopic(topic);
        try {
            term = termsService.saveTerm(term);
//            term = termsService.saveTerm(term);
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        }
    }

    public List<Topic> getAllTopics() {
        topics = termsService.getTopicRepository().findAll();
        return topics;
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

//    public void addTerm(String name) {
//        try {
//            termsService.saveTerm(name, this.topic, new Term());
//            displayInfo("Term created", "New term successfully created.");
//        } catch (IllegalArgumentException e) {
//            displayError("Term not created", e.getMessage());
//        }
//    }

    public void editTopic(String name) {
        try {
            termsService.saveTopic(name, topic);
            displayInfo("Topic updated", "Topic successfully changed.");
        } catch (IllegalArgumentException e) {
            displayError("Topic not updated", e.getMessage());
        }
    }

//    public void editTerm(String name, Topic topic) {
//        try {
//            termsService.saveTerm(name, topic, term);
//            displayInfo("Term updated", "Term successfully changed.");
//        } catch (IllegalArgumentException e) {
//            displayError("Term not updated", e.getMessage());
//        }
//    }

    public void deleteTopic() {
        try {
            termsService.deleteTopic(topic);
            displayInfo("Topic deleted", "Topic successfully deleted.");
        } catch (IllegalArgumentException e) {
            displayError("Topic not deleted", e.getMessage());
        }
    }

//    public void deleteTerm() {
//        termsService.getTermsRepository().delete(term);
//        displayInfo("Term deleted", "Term successfully deleted.");
//    }

    public void importTerms() {
        try {
            termsService.importTerms();
        } catch (FileNotFoundException e) {
            displayError("File not found", "Error: File not found.");
        } catch (ParseException e) {
            displayError("Parse error", "Error: File could not be read.");
        }
    }

}
