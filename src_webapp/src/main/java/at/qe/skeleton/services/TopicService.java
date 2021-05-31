package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.*;
import at.qe.skeleton.ui.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.*;

@Component
@Scope("application")
public class TopicService implements Serializable {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private MessageBean messageBean;


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GAME_MANAGER')")
    public Collection<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GAME_MANAGER')")
    public Collection<Topic> getAllTopicsAsc() {
        return topicRepository.findAllAsc();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Topic loadTopic(String topicName) {
        return topicRepository.findFirstByTopicName(topicName);
    }

    /**
     * returns and saves a given topic into the repo
     * @param topic
     * @return topic object
     */
    public Topic saveTopic(Topic topic) throws IllegalArgumentException {
        if (topic.getTopicName().isEmpty()) {
            throw new IllegalArgumentException("Topic has no name", new Throwable("Please enter a name for your Topic."));
        }
        if (topicExists(topic)) {
            throw new IllegalArgumentException("Topic already exists", new Throwable("Topics need to have unique names."));
        }
        else if (topic.isNew()) {
            topic.setCreateDate(new Date());
        } else {
            topic.setUpdateDate(new Date());
            topic.setUpdateTopic(getAuthenticatedTopic());
            topic.setCreateDate(new Date());
        }
        return topicRepository.save(topic);
    }

    /**
     * returns true if topic is already in the reop
     * @param topic
     * @return boolean
     */
    public boolean topicExists(Topic topic) {
        return !topicRepository.findByTopicNameContaining(topic.getTopicName()).isEmpty();
    }

    /**
     * deletes a given topic if it exists
     * @param topic
     * @throws IllegalArgumentException
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GAME_MANAGER')")
    public void deleteTopic(Topic topic) throws IllegalArgumentException {
        if (!termsRepository.findAllByTopic(topic).isEmpty()) {
            throw new IllegalArgumentException("Topic contains terms and therefore cannot be deleted.");
        } else {
            topicRepository.delete(topic);
        }
    }

    public Topic getTopicByName(Topic topic) {
        return this.loadTopic(topic.getTopicName());
    }

    private Topic getAuthenticatedTopic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return topicRepository.findFirstByTopicName(auth.getName());
    }

    /**
     * returns for each topic its containing terms
     * @return list of gameTopicCounts
     */
    public List<GameTopicCount> getTopicTermsAmount() {
        List<GameTopicCount> in = termsRepository.getAmountOfTerms();

        Set<Topic> all = new HashSet<>(topicRepository.findAll());
        Set<Topic> terms = new HashSet<>(termsRepository.getAmountOfTermsJustTerms());
        all.removeAll(terms);

        List<GameTopicCount> notIncluded = new ArrayList<>();
        for (Topic v: all) {
            notIncluded.add(new GameTopicCount(v, 0L));
        }

        in.addAll(notIncluded);
        return in;
    }
}
