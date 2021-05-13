package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component
@Scope("view")
public class TermsController extends Controller implements Serializable {

    @Autowired
    private TermsService termsService;

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

    /**
     * saves a term to the database
     * @param topic
     */
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

    public void deleteTerm() {
        termsService.getTermsRepository().delete(term);
        displayInfo("Term deleted", "Term deleted successfully.");
    }
}
