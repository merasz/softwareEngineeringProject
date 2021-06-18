package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.primefaces.PrimeFaces;
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
    public String topicName;

    public GameTopicCount gameTopicCount;

    @PostConstruct
    public void init() {
        doCreateNewTopic();
    }

    public void doCreateNewTopic() {
        topic = new Topic();
        gameTopicCount = new GameTopicCount();
        topicName = null;
    }

    public GameTopicCount getGameTopicCount() {
        return gameTopicCount;
    }

    public void setGameTopicCount(GameTopicCount gameTopicCount) {
        this.gameTopicCount = gameTopicCount;
    }

    public void doDeleteTopic() {
        try {
            topicService.deleteTopic(topic);
            topic = null;
            displayInfo("Topic deleted", "Topic successfully deleted");
        } catch (IllegalArgumentException e) {
            displayError("Topic not empty", e.getMessage());
        } catch (Exception e) {
            displayError("Error", "Topic could not be deleted");
        }
    }

    public void doSaveTopic(){
        if (saveTopic()) {
            displayInfo("Topic saved", "");
            PrimeFaces.current().executeScript("PF('topicCreationDialog').hide()");
        }
    }

    public void doEditTopic(){
        if (saveTopic()) {
            displayInfo("Topic edited", "");
            PrimeFaces.current().executeScript("PF('topicEditDialog').hide()");
        }
    }

    public boolean saveTopic() {
        try{
            topic.setTopicName(topicName);
            topicService.saveTopic(topic);
            doCreateNewTopic();
            return true;
        } catch (IllegalArgumentException e) {
            displayError(e.getMessage(), e.getCause().getMessage());
            return false;
        }
    }

    //region getter & setter
    public Collection<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    //endregion
}
