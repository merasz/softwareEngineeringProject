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

    /**
     * returns a topic from a given string
     * @param topicName
     * @return topic object
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Topic loadTopic(String topicName) {
        return topicRepository.findFirstByTopicName(topicName);
    }

    /**
     * saves a topic with a given name
     * @param name
     * @param topic
     * @throws IllegalArgumentException
     */
    public void saveTopic(String name, Topic topic) throws IllegalArgumentException {
        validateTopic(name);
        topic.setTopicName(name);
        topicRepository.save(topic);
    }

    /**
     * returns and saves a given term
     * @param term
     * @return term object
     * @throws IllegalArgumentException
     */
    public Term saveTerm(Term term) throws IllegalArgumentException {
        if (termsRepository.findAll().stream().anyMatch(t -> t.getTermName().equals(term.getTermName()))) {
            throw new IllegalArgumentException("Term already exists.");
        }
        return termsRepository.save(term);
    }

    /**
     * deletes a given topic
     * @param topic
     * @throws IllegalArgumentException
     */
    public void deleteTopic(Topic topic) throws IllegalArgumentException {
        if (!termsRepository.findAllByTopic(topic).isEmpty()) {
            throw new IllegalArgumentException("Topic contains terms and therefore cannot be deleted.");
        } else {
            topicRepository.delete(topic);
        }
    }

    /**
     * sets a topic
     * used for json fileupload
     * @param topic
     * @return
     * @throws IllegalArgumentException
     */
    public Topic setTopic(Topic topic) throws IllegalArgumentException {
        List<Term> terms = termsRepository.findAllByTopic(topic);
        if (terms.size() < MIN_NUMBER_TERMS) {
            throw new IllegalArgumentException("Topic has less than " + MIN_NUMBER_TERMS + " terms. Please choose another topic.");
        } else {
            return topic;
        }
    }

    /**
     * validate a topics name
     * @param name
     * @throws IllegalArgumentException
     */
    private void validateTopic(String name) throws IllegalArgumentException {
        Topic t = topicRepository.findFirstByTopicName(name);
        if (t != null) {
            throw new IllegalArgumentException("Topic already exists.");
        }
    }

    /**
     * imports for a given topic its terms
     * used for json fileupload
     * @param jsonObject
     * @param topic
     */
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
