package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Scope("view")
public class TermsController extends Controller implements Serializable {
    @Autowired
    private TermsService termsService;

    /*
    @Autowired
    private TopicService topicService;

    private List<Topic> topics;
    private List<Term> terms;
    private String name;
    */

    private Term term;

    @PostConstruct
    public void init() {
        doCreateNewTerm();
    }

    public void doCreateNewTerm() {
        term = new Term();
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void doSaveTerm(Topic topic) {
        if(topic == null) {
            displayInfo("Term not created", "No term was entered.");
            return;
        }
        term.setTopic(topic);
        try {
            term = termsService.saveTerm(term);
        } catch (IllegalArgumentException e){
            displayError("Term not created.", e.getMessage());
        }
    }

    /*
    public List<Topic> getAllTopics() {
        topics = termsService.getTopicRepository().findAll();
        return topics;
    }


    public List<Term> getTermsOfTopic() {
        terms = termsService.getTermsRepository().findAllByTopic(term.getTopic());
        return terms;
    }

    public void editTerm(String name, Topic topic) {
       try {
          termsService.saveTerm(term);
          displayInfo("Term updated", "Term successfully changed.");
      } catch (IllegalArgumentException e) {
          displayError("Term not updated", e.getMessage());
       }
    }
    */

    public void deleteTerm() {
        termsService.getTermsRepository().delete(term);
        displayInfo("Term deleted", "Term deleted successfully.");
    }
}
