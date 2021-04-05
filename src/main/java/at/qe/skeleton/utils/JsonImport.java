package at.qe.skeleton.utils;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.primefaces.shaded.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonImport {

    public static JSONArray readJson(String filename) throws FileNotFoundException, ParseException {
        String file = "src\\main\\resources\\" + filename + ".json";
        JSONParser jsonParser = new JSONParser(new FileReader(file));

        return (JSONArray) jsonParser.parse();
    }
}
