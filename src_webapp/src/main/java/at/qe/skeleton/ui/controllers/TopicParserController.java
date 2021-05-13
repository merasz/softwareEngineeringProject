package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.json.simple.JSONObject;
import org.primefaces.model.file.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.json.simple.parser.JSONParser;

import javax.faces.application.*;
import javax.faces.context.*;
import java.io.*;

@Controller
public class TopicParserController {

    @Autowired
    private TermsService termsService;

    @Autowired
    private TopicService topicService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadedFile file;

    public void upload() {
      if (file != null) {
          parseAndSave();
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
      }
    }

    public void parseAndSave() {
        try {
            InputStream inputStream = file.getInputStream();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
            String topicName = jsonObject.get("topic").toString();

            Topic topic = new Topic(topicName);
            if (!topicService.topicExists(topic)) topicService.saveTopic(topic);
            termsService.importTerms(jsonObject, topic);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
}




