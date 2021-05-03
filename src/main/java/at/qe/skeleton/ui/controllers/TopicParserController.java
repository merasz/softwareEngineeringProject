package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TermsService;
import at.qe.skeleton.services.TopicService;
import at.qe.skeleton.utils.*;
import com.fasterxml.jackson.core.JsonParser;
//import javax.json.stream;
//import com.sun.faces.util.Json;
//import org.apache.tomcat.util.json.*;
import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
import org.primefaces.event.*;
import org.primefaces.model.file.*;
import org.primefaces.shaded.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.json.simple.parser.JSONParser;
//import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
//import org.primefaces.shaded.json.JSONArray;
import org.springframework.util.FileCopyUtils;

import javax.faces.application.*;
import javax.faces.context.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Controller
public class TopicParserController {

    @Autowired
    private TermsService termsService;

    @Autowired
    private TopicService topicService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadedFile file;

//    public void handleFileUpload(FileUploadEvent event) {
//        file = event.getFile();
//    }

    public void upload() throws IOException, ParseException {
      if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            //parseAndSave();
      }

        System.out.println("Schauen obs funktioniert --> es funktioniert!!!");
    }

    public void parseAndSave() throws ParseException {
        //TODO Datei wurde hochgeladen, du kannst hier den Content von der Datei einlesen;

        String jsonString = new String(file.getContent());

        // use with json tomcat

        //JSONParser jsonParser = new JSONParser(jsonString);

        //System.out.println(jsonParser.parse());

    }

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

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
}

/*
Json examples

{
  "term": [
    {
      "term_name": "Chicken",
      "topic_name": "Animals"
    }
  ]
}
----------------
{
  "Animals": "chicken",
  "Plants": "cactus",
  "Activities": "lawn-mowing"
}
----------------
{
  "topic":{
    "Animals":[
      {
        "termName": "Chicken"
      },
      {
        "termName": "Whale"
      }
    ]
  }
}
 */




