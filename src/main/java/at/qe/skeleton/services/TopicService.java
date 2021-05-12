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

    //@Autowired
    //private AuditLogRepository auditLogRepository;

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

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GAME_MANAGER')")
    public Topic saveTopic(Topic topic) {
//        AuditLog auditLog = new AuditLog();
//        auditLog.setTime(new Date());
        if (topicExists(topic)) {
            messageBean.alertError("Topic already exists", "Topics need to have unique names.");
            return topicRepository.findFirstByTopicName(topic.getTopicName());
        }
        else if (topic.isNew()) {
            topic.setCreateDate(new Date());
//            auditLog.setMessage("Topic" + topic.getTopicName() + "was created.");
        } else {
            topic.setUpdateDate(new Date());
            topic.setUpdateTopic(getAuthenticatedTopic());
            topic.setCreateDate(new Date());
//            auditLog.setMessage("Topic" + topic.getTopicName() + "was updated.");
        }
//        auditLogRepository.save(auditLog);
        if(topic.getUpdateDate() == null)
            messageBean.alertInformation("Success", "Topic was created!");
        else
            messageBean.alertInformation("Success", "Topic was updated!");

        return topicRepository.save(topic);
    }

    public boolean topicExists(Topic topic) {
        return !topicRepository.findByTopicNameContaining(topic.getTopicName()).isEmpty();
    }

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

    /*
    private void validateTopic(String name) throws IllegalArgumentException {
        Topic t = topicRepository.findFirstByTopicName(name);
        if (t != null) {
            throw new IllegalArgumentException("Topic already exists.");
        }
    }
    */

    private Topic getAuthenticatedTopic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return topicRepository.findFirstByTopicName(auth.getName());
    }

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
