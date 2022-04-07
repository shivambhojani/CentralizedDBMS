import Authentication.Authenticate;
import Logger.QueryLogger;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class main {

    public static void main(String[] args) throws IOException, ParseException {

//        QueryLogger q = new QueryLogger();
//        q.logQuery("database1", "Select * from person;", true, 1, "select",
//                10, 10, 50);
//
//        q.logQuery("database1", "Select * from person3;", true, 1, "select",
//                10, 10, 50);
//
//        q.logQuery("database1", "Select * from person2;", true, 1, "select",
//                10, 10, 50);


        Authenticate authenticate = new Authenticate();
        authenticate.init();
        //       DirectoryCreator dc = new DirectoryCreator();

        //dc.createDirectory("a");

//        QueryLogger q = new QueryLogger();
//        q.logQuery("a", "B", "a" , "a");

//        FileReader f = new FileReader("./Logs/a/QueryLogs.json");
//        JSONParser parser = new JSONParser();
//        Object object = parser.parse(f);
//        JSONObject jsonObject = (JSONObject) object;
//
//        System.out.println(jsonObject.size());

    }

}
