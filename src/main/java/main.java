import Authentication.Authenticate;
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


    }

}
