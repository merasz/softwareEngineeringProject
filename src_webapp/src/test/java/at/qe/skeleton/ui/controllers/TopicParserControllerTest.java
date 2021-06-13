package at.qe.skeleton.ui.controllers;


import at.qe.skeleton.model.*;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import at.qe.skeleton.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)

class TopicParserControllerTest {

    @Mock
    UploadedFile uploadedFile;

    @Mock
    private TermsService mockTermsService;
    @Mock
    private TopicService mockTopicService;


    @InjectMocks
    private TopicParserController topicParserController;


    /*@Test
    void testGetTopics() {
        final Collection<Topic> expectedResult = Arrays.asList(new Topic("topicName"));
        when(mockTopicService.getAllTopics()).thenReturn(Arrays.asList(new Topic("topicName")));
        final Collection<Topic> result = topicParserController.getFile();
        assertThat(result).isEqualTo(expectedResult);
    }*/
    @Test
    void testSetFile(){
        UploadedFile file = null;
        topicParserController.setFile(file);
        assertTrue(topicParserController.getFile() == file);


    }
    @Test
    void testGetFile(){
        UploadedFile file = null;
        topicParserController.setFile(file);
        assertTrue(topicParserController.getFile() == file);


    }




}