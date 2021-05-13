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

    public Topic topic;

    public GameTopicCount gameTopicCount;

    @PostConstruct
    public void init() {
        doCreateNewTopic();
    }

    public void doCreateNewTopic() {
        topic = new Topic();
        gameTopicCount = new GameTopicCount();
    }

    public GameTopicCount getGameTopicCount() {
        return gameTopicCount;
    }

    public void setGameTopicCount(GameTopicCount gameTopicCount) {
        this.gameTopicCount = gameTopicCount;
    }

    public void doDeleteTopic() {
        try {
            this.topicService.deleteTopic(topic);
            this.topic = null;
            displayInfo("Topic deleted", "Topic successfully deleted");
        } catch (IllegalArgumentException e) {
            displayError("Topic not empty", e.getMessage());
        } catch (Exception e) {
            displayError("Error", "Topic could not be deleted");
        }
    }

    public void doSaveTopic(){
        try{
            topic = topicService.saveTopic(topic);
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

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
