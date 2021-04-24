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

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void doSaveTerm(Topic topic) {
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
        terms = termsService.getTermsRepository().findAllByTopic(term.getTopic());
        return terms;
    }

//    public void addTerm(String name) {
//        try {
//            termsService.saveTerm(name, this.topic, new Term());
//            displayInfo("Term created", "New term successfully created.");
//        } catch (IllegalArgumentException e) {
//            displayError("Term not created", e.getMessage());
//        }
//    }

//    public void editTerm(String name, Topic topic) {
//        try {
//            termsService.saveTerm(name, topic, term);
//            displayInfo("Term updated", "Term successfully changed.");
//        } catch (IllegalArgumentException e) {
//            displayError("Term not updated", e.getMessage());
//        }
//    }

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
