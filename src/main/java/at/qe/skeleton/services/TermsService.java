package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.repositories.TopicRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("application")
public class TermsService {
    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private TopicRepository topicRepository;

    private final int MIN_NUMBER_TERMS = 10;
    //private Term currentTerm;
    //private List<Term> termsInGame;
    //private Iterator<Term> iterateTerms;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Topic loadTopic(String topicName) {
        return topicRepository.findFirstByTopicName(topicName);
    }

    public void saveTopic(String name, Topic topic) throws IllegalArgumentException {
        validateTopic(name);
        topic.setTopicName(name);
        topicRepository.save(topic);
    }

    /*
    public void saveTerm(String name, Topic topic, Term term) throws IllegalArgumentException {
        validateTerm(name);
        term.setTermName(name);
        term.setTopic(topic);
        termsRepository.save(term);
    }
    */

    public Term saveTerm(Term term) throws IllegalArgumentException {
        if (termsRepository.findAll().stream().anyMatch(t -> t.getTermName().equals(term.getTermName()))) {
            throw new IllegalArgumentException("Term already exists.");
        }
        return termsRepository.save(term);
    }

    public void deleteTopic(Topic topic) throws IllegalArgumentException {
        if (!termsRepository.findAllByTopic(topic).isEmpty()) {
            throw new IllegalArgumentException("Topic contains terms and therefore cannot be deleted.");
        } else {
            topicRepository.delete(topic);
        }
    }

    public Topic setTopic(Topic topic) throws IllegalArgumentException {
        List<Term> terms = termsRepository.findAllByTopic(topic);
        if (terms.size() < MIN_NUMBER_TERMS) {
            throw new IllegalArgumentException("Topic has less than " + MIN_NUMBER_TERMS + " terms. Please choose another topic.");
        } else {
            //termsInGame = terms;
            //Collections.shuffle(termsInGame);
            //iterateTerms = termsInGame.iterator();
            return topic;
        }
    }

    /*
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
    */

    private void validateTopic(String name) throws IllegalArgumentException {
        Topic t = topicRepository.findFirstByTopicName(name);
        if (t != null) {
            throw new IllegalArgumentException("Topic already exists.");
        }
    }

    public void importTerms(JSONObject jsonObject, Topic topic) {
        String allTermsAsStringJson = jsonObject.get("terms").toString();

        List<String> allTerms = new ArrayList<>();
        allTerms.addAll(Arrays.asList(allTermsAsStringJson.split("[\"]")));
        //Set<String> newTerms = new HashSet<>();
        for (String v: allTerms) {
            if(v.contains("[") || v.contains(",") || v.contains("]"))
                continue;
            Term tmp = new Term(v, topic);
            if(!termsRepository.doesTermExits(tmp.getTermName(), topic.getTopicName()))
                termsRepository.save(tmp);
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
