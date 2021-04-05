package at.qe.skeleton.services;

import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TermsRepository;
import at.qe.skeleton.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class TermsService {
    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private TopicRepository topicRepository;

    public void saveTopic(String name, Topic topic) throws IllegalArgumentException {
        validateTopic(name);
        topic.setName(name);
        topicRepository.save(topic);
    }

    public void saveTerm(String name, Topic topic, Term term) throws IllegalArgumentException {
        validateTerm(name);
        term.setName(name);
        term.setTopic(topic);
        termsRepository.save(term);
    }

    public void deleteTopic(Topic topic) throws IllegalArgumentException {
        if (!termsRepository.findAllByTopic(topic).isEmpty()) {
            throw new IllegalArgumentException("Topic contains terms and therefore cannot be deleted.");
        } else {
            topicRepository.delete(topic);
        }
    }

    private void validateTerm(String name) throws IllegalArgumentException {
        Term t = termsRepository.findFirstByName(name);
        if (t != null) {
            throw new IllegalArgumentException("Term already exists.");
        }
    }

    private void validateTopic(String name) throws IllegalArgumentException {
        Topic t = topicRepository.findFirstByName(name);
        if (t != null) {
            throw new IllegalArgumentException("Topic already exists.");
        }
    }

    public TermsRepository getTermsRepository() {
        return termsRepository;
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }
}
