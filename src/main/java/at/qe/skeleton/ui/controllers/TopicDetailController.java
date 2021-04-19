package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Component
@Scope("view")
public class TopicDetailController {

    @Autowired
    private TopicService topicService;

    private Topic topic;

    public void setTopic(Topic topic){
        this.topic = topic;
    }
    public Topic getTopic(){
        return topic;
    }

    public void doSaveTopic(){

    }


}
