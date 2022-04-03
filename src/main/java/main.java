import Authentication.Authenticate;
import FileManager.DirectoryCreator;
import Logger.QueryLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.management.remote.JMXServerErrorException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class main {

    public static void main(String[] args) throws IOException, ParseException {
        Authenticate authenticate = new Authenticate();
        authenticate.init();
        DirectoryCreator dc = new DirectoryCreator();

        //dc.createDirectory("a");

        QueryLogger q = new QueryLogger();
        q.logQuery("a", "B", "a" , "a");

//        FileReader f = new FileReader("./Logs/a/QueryLogs.json");
//        JSONParser parser = new JSONParser();
//        Object object = parser.parse(f);
//        JSONObject jsonObject = (JSONObject) object;
//
//        System.out.println(jsonObject.size());

    }

}
