package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.repositories.TopicRepository;
import at.qe.skeleton.utils.JsonImport;
import org.apache.tomcat.util.json.ParseException;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.*;

@Component
@Scope("application")
public class TermsService {
    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private TopicRepository topicRepository;

    private final int MIN_NUMBER_TERMS = 10;
    private Term currentTerm;
    private List<Term> termsInGame;
    private Iterator<Term> iterateTerms;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Topic loadTopic(String topicName) {
        return (Topic) topicRepository.findFirstByTopicName(topicName);
    }



    public void saveTopic(String name, Topic topic) throws IllegalArgumentException {
        validateTopic(name);
        topic.setTopicName(name);
        topicRepository.save(topic);
    }

    public Term saveTerm(Term term) throws IllegalArgumentException {
        return termsRepository.save(term);
    }

    public Term demoSaveTerm(Term term) throws IllegalArgumentException {
        Topic curr = topicRepository.findFirstByTopicName("Geo");
        term.setTopic(curr);
        return termsRepository.save(term);
    }

//    public void saveTerm(String name, Topic topic, Term term) throws IllegalArgumentException {
//        validateTerm(name);
//        term.setTermName(name);
//        term.setTopic(topic);
//        termsRepository.save(term);
//    }

    public void deleteTopic(Topic topic) throws IllegalArgumentException {
        if (!termsRepository.findAllByTopic(topic).isEmpty()) {
            throw new IllegalArgumentException("Topic contains terms and therefore cannot be deleted.");
        } else {
            topicRepository.delete(topic);
        }
    }

    public Topic setGameTopic(Topic topic) throws IllegalArgumentException {
        List<Term> terms = termsRepository.findAllByTopic(topic);
        if (terms.size() < MIN_NUMBER_TERMS) {
            throw new IllegalArgumentException("Topic has less than " + MIN_NUMBER_TERMS + " terms in this topi. Please choose another topic.");
        } else {
            termsInGame = terms;
            Collections.shuffle(termsInGame);
            iterateTerms = termsInGame.iterator();
            return topic;
        }
    }

    public Term getNextTerm(Game game) {
        if (!iterateTerms.hasNext()) {
            Collections.shuffle(termsInGame);
            iterateTerms = termsInGame.iterator();
        }
        currentTerm = iterateTerms.next();
        return currentTerm;
    }

    private void validateTerm(String name) throws IllegalArgumentException {
        Term t = termsRepository.findFirstByTermName(name);
        if (t != null) {
            throw new IllegalArgumentException("Term already exists.");
        }
    }

    private void validateTopic(String name) throws IllegalArgumentException {
        Topic t = topicRepository.findFirstByTopicName(name);
        if (t != null) {
            throw new IllegalArgumentException("Topic already exists.");
        }
    }

    public void importTerms() throws FileNotFoundException, ParseException {
        JSONArray json = JsonImport.readJson("terms");

        for (Object o : json) {
            JSONObject jsonObject = (JSONObject) o;

            String topicName = jsonObject.keys().next();
//            Topic topic = topicRepository.findFirstByName(topicName);
//            if (topic == null) {
//                topic = new Topic(topicName);
//            }

            String termName = (String) jsonObject.names().get(0);
//            Term term = termsRepository.findFirstByName(termName);
//            if (term == null || term.getTopic().getName() != topicName) {
//                term = new Term(termName, topic);
//            }
//
//            topicRepository.save(topic);
//            termsRepository.save(term);

            System.out.println(topicName);
            System.out.println(termName);
            System.out.println("-----");
        }
    }

    public List<Term> getTermsForTopic(Topic topic) {
        return termsRepository.findAllByTopic(topic);
    }

    public TermsRepository getTermsRepository() {
        return termsRepository;
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }

    public Collection<Term> getAllTerms() {
        return termsRepository.findAll();
    }
}
