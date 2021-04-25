package at.qe.skeleton.utils;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JsonImport {

    public static JSONArray readJson(String filename) throws FileNotFoundException, ParseException {
        String file = "src\\main\\resources\\" + filename + ".json";
        JSONParser jsonParser = new JSONParser(new FileReader(file));

        return (JSONArray) jsonParser.parse();
    }

    private Connection connect = null;
    PreparedStatement preparedStatement = null;

    public int insertJSONtoDB() throws Exception {
        int status = 0;
        try {
            Class.forName("org.h2.Driver");
            connect = DriverManager.getConnection("jdbc:h2:mem:skel", "sa", "");
            PreparedStatement preparedStatement = connect.prepareStatement("insert into term values ( ?, ?, ? )");
            //JSONParser parser = new JSONParser();
            //Object obj = parser.parse(new FileReader("c.\\terms.json"));
            //readJson("terms");
            JSONObject jsonObject = new JSONObject(readJson("terms"));
            JSONArray jsonArray = readJson("terms");

            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                String term_name = (String) record.get("Term_Name");    // from JSON tag
                String topic_name = (String) record.get("Topic_Topic_Name");
                preparedStatement.setString(1, term_name);  // to the Database table
                preparedStatement.setString(2, topic_name);
            }

            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return status;
    }
}