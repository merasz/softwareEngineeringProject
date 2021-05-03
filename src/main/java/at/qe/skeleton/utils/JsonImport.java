package at.qe.skeleton.utils;

import org.apache.tomcat.util.json.JSONParser;
//import org.json.simple.parser.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.primefaces.shaded.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonImport {

    public static JSONArray readJson(String filename) throws FileNotFoundException, ParseException {
        String file = "src\\main\\resources\\" + filename + ".json";
        JSONParser jsonParser = new JSONParser(new FileReader(file));

        //System.out.println(jsonParser);

        return (JSONArray) jsonParser.parse();
    }

    /*
        JSONObject object = new JSONObject(file);
        JSONArray terms = object.getJSONArray("terms");
        for(int i = 0; i < terms.length(); ++i) {
            JSONObject term = terms.getJSONObject(i);
            //System.out.println(term.getString("termName"));
            //System.out.println(term.getString("topic"));
        }
     */
}