package QueryEngine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Bijitashya on 04, 2022
 */
public class QueryEngine {

    public String currentDatabase = "";

    public static MetaDataGenerator metaDataGenerator = new MetaDataGenerator();
    public static HashMap<String, List<HashMap<String, String>>> tablesMetaData = new HashMap<>(metaDataGenerator.getTablesMetaData());

    public void readQueries() throws IOException {
        Scanner sc = new Scanner(System.in);
        String input = "";
        while (true) {
            String currentInput;
            if (input.length() == 0) {
                System.out.printf("%s> ", currentDatabase.length() > 0 ? currentDatabase : "sql");
            }

            // take the input from user
            // trim removes extra spaces leading or following the input
            currentInput = sc.nextLine().trim();

            // if user enters exit, leave the loop
            if (input.length() == 0 && currentInput.equals("exit")) {
                System.out.println("Query processor closed.");
                break;
            } else if (currentInput.equals("")) {
                // if the user entered empty string, continue again
                continue;
            } else if (currentInput.equals("print data")) {
//                System.out.println(tableRows);
                continue;
            } else if (currentInput.equals("print meta")) {
//                System.out.println(tablesMetaData);
                continue;
            }

            String lastCharacter = currentInput.substring(currentInput.length() - 1);
            //For multiline query input
            if (!lastCharacter.equals(";")) {
                input = input.concat(" " + currentInput);
                continue;
            } else if (input.length() > 0) {
                input = input.concat(currentInput.substring(0, currentInput.length() - 1));
            } else {
                input = currentInput.substring(0, currentInput.length() - 1);
            }

            // create a list from input string list split by ' ' (space)
            input = input.replaceAll("[(]", " ( ");
            input = input.replaceAll("[)]", " ) ");

            List<String> userInput = new ArrayList<>(Arrays.asList(input.trim().split(" ")));

            // remove extra elements created due to multiple spaces
            userInput.removeAll(Arrays.asList("", null));

            if (userInput.size() == 1) {
                System.out.println("Too few arguments. Invalid query.");
            }

            String queryType = userInput.get(0).toLowerCase();

            switch (queryType) {
                case "create":
                    String createQueryType = userInput.get(1).toLowerCase();
                    if (createQueryType.equals("database") && QueryValidator.validateCreateDatabaseQuery(userInput)){
                        CreateQuery create  = new CreateQuery(userInput,tablesMetaData);
                        create.processCreateDatabaseQuery(userInput.get(2));
                    }else{
                        CreateQuery create  = new CreateQuery(currentDatabase,tablesMetaData);
                        create.processCreateTable(userInput);
                    }
                    break;
                case "use":
                    if (QueryValidator.validateUseDatabaseQuery(userInput)) {
                        currentDatabase = userInput.get(1);

                    }
                    break;
            }
            input = "";
        }

    }
}
