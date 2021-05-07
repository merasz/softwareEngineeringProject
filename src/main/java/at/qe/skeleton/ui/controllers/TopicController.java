package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collection;
import java.util.List;

@Component
@Scope("view")
public class TopicController extends Controller implements Serializable {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TermsService termsService;

    public Topic topic;

    public GameTopicCount gameTopicCount;

    private Collection<Topic> topicList;

    @PostConstruct
    public void init() {
        doCreateNewTopic();
    }

    public void doCreateNewTopic() {
        setTopicList();
        topic = new Topic();
        gameTopicCount = new GameTopicCount();
    }

    public void setTopicList() {
        topicList = topicService.getAllTopics();
    }

    public void setTopic(Topic topic) {
        if(gameTopicCount != null)
            this.topic = gameTopicCount.getTopic();
        else
            this.topic = topic;
    }

    public GameTopicCount getGameTopicCount() {
        return gameTopicCount;
    }

    public void setGameTopicCount(GameTopicCount gameTopicCount) {
        this.gameTopicCount = gameTopicCount;
    }

    private void doReloadTopic() {
        topic = topicService.loadTopic(topic.getTopicName());
    }

    public void doDeleteTopic() {
        try {
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
            topic = new Topic();
        } catch (IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }
    }

    public Collection<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    public Topic getTopic() {
        return topic;
    }


}
