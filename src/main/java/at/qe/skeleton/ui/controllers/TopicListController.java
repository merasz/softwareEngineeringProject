package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Scope("view")
public class TopicListController {

    @Autowired
    private TopicService topicService;

    public Collection<Topic> getTopics() {
        return topicService.getAllTopics();
    }
}
