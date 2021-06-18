package at.qe.skeleton.ui.controllers;


import at.qe.skeleton.model.*;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import at.qe.skeleton.services.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.test.web.servlet.MockMvc;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)

class TopicParserControllerTest {

    @Mock
    Topic topic;

    @Mock
    UploadedFile uploadedFile;

    @Mock
    private TermsService mockTermsService;
    @Mock
    private TopicService mockTopicService;

    //private final Logger log = LoggerFactory.getLogger(this.getClass());


    @InjectMocks
    private TopicParserController topicParserController;


   /* public void upload() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            assertThat(uploadedFile).isEqualTo(uploadedFile);
            //if (uploadedFile != null) {
            try {
                String isItJson = uploadedFile.getFileName().substring(uploadedFile.getFileName().length() - 4);
                assertTrue(isItJson.equals("json"));
                //if(isItJson.equals("json")) {
                topicParserController.parseAndSave();
                FacesMessage message = new FacesMessage("Successfull", uploadedFile.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                //}
                //else {
                assertFalse(isItJson.equals("json"));
                FacesMessage message1 = new FacesMessage("Unsuccessful", uploadedFile.getFileName() + " please use a json file.");
                FacesContext.getCurrentInstance().addMessage(null, message1);
                //}

            } catch (Exception e) {
                e.printStackTrace();
            }
            topicParserController.getFile();
        });
        }*/

   @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testUpload(){
            Assertions.assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {

                TopicParserController topicParserController = new TopicParserController();
                when(uploadedFile.getFileName()).thenReturn("");

                try {
                    String isItJson = uploadedFile.getFileName().substring(uploadedFile.getFileName().length() - 4);
                    when(isItJson.equals("json"));
                    topicParserController.parseAndSave();
                    FacesMessage message = new FacesMessage("Successfull", uploadedFile.getFileName() + " is uploaded.");
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    when(isItJson.isEmpty());
                    FacesMessage message1 = new FacesMessage("Unsuccessful", uploadedFile.getFileName() + " please use a json file.");
                    FacesContext.getCurrentInstance().addMessage(null, message1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

/*
@Test
void testParseAndSave() {
    try {
        InputStream inputStream = uploadedFile.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
        String topicName = jsonObject.get("topic").toString();


        Topic topic;
        if (mockTopicService.topicExists(new Topic(topicName))) {
            topic = mockTopicService.loadTopic(topicName);
        }
        else {
            topic = new Topic(topicName);
            mockTopicService.saveTopic(topic);
        }
        mockTermsService.importTerms(jsonObject, topic);
    } catch (Exception e) {
        e.printStackTrace();
        String errorMessage = uploadedFile.getFileName() + " has a invalid JSON-Format.";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!", errorMessage);
        FacesContext.getCurrentInstance().addMessage(null, message);
        throw new JsonParseException();

    }
    topicParserController.getFile();
}*/



    @Test
    void testSetFile(){
        UploadedFile file = new UploadedFile() {
            @Override
            public String getFileName() {
                return null;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public byte[] getContent() {
                return new byte[0];
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public void write(String filePath) throws Exception {

            }
        };
        topicParserController.setFile(file);
        assertTrue(topicParserController.getFile() == file);


    }
    @Test
    void testGetFile(){
        UploadedFile file = new UploadedFile() {
            @Override
            public String getFileName() {
                return null;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public byte[] getContent() {
                return new byte[0];
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public void write(String filePath) throws Exception {

            }
        };
        topicParserController.setFile(file);
        topicParserController.getFile();
        assertTrue(topicParserController.getFile() == file);


    }




}