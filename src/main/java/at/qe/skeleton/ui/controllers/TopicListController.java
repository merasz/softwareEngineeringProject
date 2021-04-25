package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Component
@Scope("view")
public class TopicListController implements Serializable {

    @Autowired
    private TopicService topicService;

    public Collection<Topic> getTopics () { return topicService.getAllTopics(); }

}
