package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.utils.*;
import org.apache.tomcat.util.json.*;
import org.primefaces.event.*;
import org.primefaces.model.file.*;
import org.primefaces.shaded.json.*;
import org.slf4j.*;
import org.springframework.stereotype.Controller;

import java.io.*;

@Controller
public class TopicParserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadedFile file;

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
    }

    public void upload() {

        if (file != null) {
            parseAndSave();
        }
    }

    public void parseAndSave() {
        //TODO Datei wurde hochgeladen, du kannst hier den Content von der Datei einlesen;

        String jsonString = new String(file.getContent());

        JSONParser jsonParser = new JSONParser(jsonString);

        System.out.println(jsonString);

    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

}
