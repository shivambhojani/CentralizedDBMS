package DataModeller;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import Query.DBEngine;
import Query.ValidationEngine;

public class UMLGenerator {

    private String databaseName;

    public UMLGenerator()
    {
        databaseName = "";
    }

    public void saveUML()
    {
        List<String> tableList = DBEngine.getTables(databaseName);

        try {
            BufferedWriter fileHandle = new BufferedWriter(new FileWriter(databaseName + "/uml.txt"));
            fileHandle.write("\n----------" + databaseName + " UML MODEL----------\n\n");

            for(int cnt=0; cnt<tableList.size(); cnt++)
            {
                List<HashMap<String, String>> tableMetadata = DBEngine.getTableMetadata(databaseName, tableList.get(cnt));

                fileHandle.write("==========Table Name: " + tableList.get(cnt) + " ==========\n");

                for(int colCnt = 0; colCnt < tableMetadata.size(); colCnt++)
                {
                    HashMap<String, String> currentColumn = tableMetadata.get(colCnt);
                    String data = "==> Column Name: " + currentColumn.get("name") + " [" +
                            currentColumn.get("type");
                    if(currentColumn.get("type").contains("char"))
                    {
                        data+= "(" + currentColumn.get("size") + ")]\n";
                    }
                    else
                    {
                        data+= "]\n";
                    }
                    fileHandle.write(data);
                }
                fileHandle.write("\n\n");

            }


            fileHandle.close();
            System.out.println("UML Model saved successfully!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startEngine()
    {
        String userInput = "";
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            System.out.print("Enter database name: ");
            userInput = sc.nextLine();
            if(userInput.toLowerCase().contains("exit"))
            {
                System.out.println("Exiting UML engine!");
                break;
            }
            else
            {
                databaseName = userInput.strip();
                if(ValidationEngine.checkIfDbExists(databaseName))
                {
                    saveUML();
                }
                else
                {
                    System.out.println("Invalid database name. Please try again.");
                }
            }
        }
    }

}
