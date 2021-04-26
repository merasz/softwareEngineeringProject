package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.Collection;

@Component
@Scope("view")
public class TopicListController implements Serializable {

    @Autowired
    private TopicService topicService;

    public Collection<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    public List<String> getTopicNames() {
        return getTopics().stream().map(Topic::getTopicName).collect(Collectors.toList());
    }
}
