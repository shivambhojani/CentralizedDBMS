package CreateSession;
import Configs.StaticData;
import Exporter.ExportDump;
import Logger.LogGenerator;

import java.io.IOException;
import java.util.Scanner;

public class SessionCreator {

    private String userID = "";
    LogGenerator logGenerator = new LogGenerator();

    public SessionCreator(String userID){
        this.userID = userID;
    }

    private String userInput1 = "";

    public void createSession() throws IOException {

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
                    //<Write Query Integration here>
                    break;
                case "2":
                    ExportDump e = new ExportDump();
                    e.createSQLDump();
                    break;
                case "3":
                    //Integrate Data Model
                    System.exit(0);
                    break;
                case "4":
                    //Integrate Analytics
                    System.exit(0);
                    break;

                case "5":
                    logGenerator.addToLoginHistory(userID, StaticData.logout);
                    System.out.println("Bye!!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect option, Try Again");
                    break;
            }


        }while (userInput1 != "5");
    }

}
