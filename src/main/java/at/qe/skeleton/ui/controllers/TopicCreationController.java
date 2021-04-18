package at.qe.skeleton.ui.controllers;


import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;

@Component
@Scope("view")
public class TopicCreationController extends Controller implements Serializable {

    @Autowired
    private TopicService topicService;

    private Topic topic = new Topic();

    public void setTopic(Topic topic){
        this.topic = topic;
        doReloadTopic();
    }

    public Topic getTopic(){
        return topic;
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
            displayError("Error", e.getMessage());
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


}
