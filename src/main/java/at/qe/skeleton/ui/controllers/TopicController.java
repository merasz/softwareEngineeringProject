package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collection;

@Component
@Scope("view")
public class TopicController extends Controller implements Serializable {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TermsService termsService;

    public Topic topic;

    private Collection<Topic> topicList;

    @PostConstruct
    public void init() {
        doCreateNewTopic();
    }

    public void doCreateNewTopic() {
        setTopicList();
        topic = new Topic();
    }

    public void setTopicList() {
        topicList = topicService.getAllTopics();
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    private void doReloadTopic() {
        topic = topicService.loadTopic(topic.getTopicName());
    }

    public void doDeleteTopic() {
        try {
            System.out.println("inside dodeletetopic");
            this.topicService.deleteTopic(topic);
            topic = null;
            displayInfo("Topic deleted", "Topic successfully deleted");
        } catch (IllegalArgumentException e) {
            displayError("Error Topic not empty", e.getMessage());
        } catch (Exception e) {
            displayError("Error", "Topic could not be deleted");
        }
    }

    public void doSaveTopic(){
        try{
            topic = topicService.saveTopic(topic);
            displayInfo("Topic created", "Successfully created");
        } catch (IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }
    }

    public Collection<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    /*update fx to get the num size of a specific topic
    * */
    public int getNumTermsATopic() {
        return 1;
    }

    public Topic getTopic() {
        return topic;
    }


    public Collection<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(Collection<Topic> topicList) {
        this.topicList = topicList;
    }
}
