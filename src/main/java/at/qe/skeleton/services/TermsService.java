package at.qe.skeleton.services;

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

    public void saveTopic(String name) {
        topicRepository.save(new Topic(name));
    }

    public void saveTerm(String name, Topic topic) {
        termsRepository.save(new Term(name, topic));
    }

    public void updateTopic(String name, Topic topic) {
        topic.setName(name);
        termsRepository.save(topic);
    }

    public void updateTerm(String name, Topic topic, Term term) {
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

    public TermsRepository getTermsRepository() {
        return termsRepository;
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }
}
