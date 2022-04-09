package CreateSession;
import Analytics.Analytics;
import Authentication.Authenticate;
import Configs.StaticData;
import Exporter.ExportDump;
import Logger.LogGenerator;
import Query.EngineController;
import DataModeller.UMLGenerator;
import java.io.IOException;
import java.util.Scanner;

public class SessionCreator {

    public static String userID = "";
    LogGenerator logGenerator = new LogGenerator();

    public SessionCreator(String userID){
        this.userID = userID;
    }

    private String userInput1 = "";

    public void createSession() throws IOException, InterruptedException {

        System.out.println("<<<<<<<<<<<<<<<<<<<< New Session Created >>>>>>>>>>>>>>>>>>>>");
        System.out.println();
        do {
            System.out.println();
            System.out.println("1. Write Queries");
            System.out.println("2. Export");
            System.out.println("3. Data Model");
            System.out.println("4. Analytics");
            System.out.println("5. Logout");
            System.out.print("Enter Your Choice: ");
            Scanner sc = new Scanner(System.in);
            String  input = sc.nextLine();
            userInput1 = input;
            switch (input) {
                case "1":
                    EngineController.startEngine();
                    break;
                case "2":
                    ExportDump e = new ExportDump();
                    e.createSQLDump();
                    break;
                case "3":
                    UMLGenerator umlEngine = new UMLGenerator();
                    umlEngine.startEngine();
                    break;
                case "4":
                    //Integrate Analytics
                    Analytics.main(new String[] {});
                    System.exit(0);
                    break;

                case "5":
                    logGenerator.addToLoginHistory(userID, StaticData.logout);
                    System.out.println("Bye!!!");
                    Authenticate authenticate = new Authenticate();
                    authenticate.init();
                default:
                    System.out.println("Incorrect option, Try Again");
                    break;
            }


        }while (userInput1 != "5");
    }

}
