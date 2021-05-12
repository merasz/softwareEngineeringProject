package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import org.json.simple.JSONObject;
import org.primefaces.event.*;
import org.primefaces.model.file.*;
import org.primefaces.shaded.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.json.simple.parser.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.util.FileCopyUtils;

import javax.faces.application.*;
import javax.faces.context.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Controller
public class TopicParserController {

    @Autowired
    private TermsService termsService;

    @Autowired
    private TopicService topicService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadedFile file;

    public void upload() throws IOException, ParseException {
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

    /*
    public void handleFileUpload(FileUploadEvent event) {
        if(event.getFile() != null) {
            // use with json simple
            JSONParser parser = new JSONParser();
            try {
                String data = "";
                byte[] bdata = FileCopyUtils.copyToByteArray(event.getFile().getInputStream());
                data = new String(bdata, StandardCharsets.UTF_8);

                Object object = parser.parse(data);
                JSONObject jsonObject = (JSONObject) object;

                String name = (String) jsonObject.get("topic");

                JSONArray terms = (JSONArray) jsonObject.get("termName");

                Topic newTopic = new Topic();
                newTopic.setTopicName(name);
                newTopic = topicService.saveTopic(newTopic);

                for(Object term : terms) {
                    Term newTerm = new Term();
                    newTerm.setTermName(term.toString());
                    newTerm.setTopic(newTopic);
                    termsService.saveTerm(newTerm);
                }
            }
            catch(FileNotFoundException fe) {
                fe.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
}




