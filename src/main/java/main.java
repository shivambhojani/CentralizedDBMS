import Authentication.Authenticate;
import Logger.GetTimer;

import java.io.IOException;


public class main {

    public static void main(String[] args) throws IOException, InterruptedException {

//        LogGenerator q = new LogGenerator();
//        q.logQuery("database1", "Select * from person;", true, "Shivam", "select",
//                10, 10, 50);
//
//        q.logQuery("database1", "Select * from person3;", true, "Shivam", "select",
//                10, 10, 50);
//
//        q.logQuery("database1", "Select * from person2;", true, "Shivam", "select",
//                10, 10, 50);


        Authenticate authenticate = new Authenticate();
        authenticate.init();

//        GetTimer t = new GetTimer();
//        t.getCurrentTime();


//        ExportDump e = new ExportDump();
//        e.createSQLDump();


    }

}
