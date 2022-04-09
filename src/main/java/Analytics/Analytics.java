package Analytics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analytics {

    private final static String generalLogsFilePath = "src/Logs/QueryLog.txt";
    public static List<QueriesCounter> queriesCounters = new ArrayList<>();
    public static List<String> logs = new ArrayList<>();
    public static List<UpdateQueries> updateQueries = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        System.out.println("Analytics");
        Analytics analytics = new Analytics();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter analytics query string");
        String queryString = scanner.nextLine();
        System.out.println("query string is "+queryString);

        readLogFile();
        generateUserQueries();
        generateUpdateQueries();
//        printCreateLogs(queriesCounters);

        if(queryString.contains("count queries")) {
            String [] entries = queryString.split(" ");
            if(!(entries.length==2)) {
                System.out.println("Invalid count query");
                return;
            }
            if(entries[1].contains(";")) {
                analytics.printCreateLogs(queriesCounters);
            }
            else{
                System.out.println("Invalid query");
            }
        }else if(queryString.contains("count update DB1;")){
            String [] entries = queryString.split(" ");
            if(!(entries.length==3)) {
                System.out.println("Invalid count query");
                return;
            }
            if(entries[2].contains(";")) {
                analytics.printUpdateQueries(updateQueries);
            }
            else{
                System.out.println("Invalid query");
            }
        }
        else{
            System.out.println("Invalid Query");
        }
        return;
    }

    public static void printUpdateQueries(List<UpdateQueries> updateQueries) {
        if(updateQueries.size()==0){
            System.out.println("No update queries performed");
            return;
        }
        for (UpdateQueries update : updateQueries) {
            System.out.printf(update.getNoOfQueries()+" update queries performed on "+update.getTableName()+ "\n");
        }
    }

    public static void generateUpdateQueries() {
        if (logs.size() == 0) {
            System.out.println("No operations performed");
            return;
        }
        for (String log : logs) {
            if (log.contains("|")) {
                List<String> logValues = List.of(log.split("\\|"));
                if (logValues.contains("Update")) {
                    String dbQuery = logValues.get(3);
                    String tableName = dbQuery.split(" ")[1];
                    if (log.contains(tableName)) {
                        if (updateQueries.size() == 0) {
                            UpdateQueries up = new UpdateQueries();
                            up.setTableName(tableName);
                            up.setNoOfQueries(1);
                            updateQueries.add(up);
                        } else {
                            if (updateQueries.size() > 0) {
                                boolean tableInUse = false;
                                for (UpdateQueries query : updateQueries) {
                                    if (query.getTableName().equalsIgnoreCase(tableName)) {
                                        tableInUse = true;
                                    }
                                }
                                if (tableInUse == true) {
                                    for (UpdateQueries query : updateQueries) {
                                        if (query.getTableName().equalsIgnoreCase(tableName)) {
                                            int noQueries = query.getNoOfQueries();
                                            query.setNoOfQueries(noQueries + 1);
                                        }
                                    }
                                } else {
                                    UpdateQueries up = new UpdateQueries();
                                    up.setTableName(tableName);
                                    up.setNoOfQueries(1);
                                    updateQueries.add(up);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void printCreateLogs(List<QueriesCounter> counters) {
        if (queriesCounters.size() == 0) {
            System.out.println("No operations on the DB");
            return;
        }
        for (QueriesCounter count : counters) {
            System.out.printf("User " + count.getUserName() + " has executed " + count.getNumberOfQueries() + "\n");
        }
    }

    public static void generateUserQueries() {
        for (String log : logs) {
            if (log.contains("|")) {
                List<String> logValues = List.of(log.split("\\|"));
                String username = logValues.get(2);
                if (logValues.contains(username)) {
                    if (queriesCounters.size() == 0) {
                        QueriesCounter qc = new QueriesCounter();
                        qc.setUserName(username);
                        qc.setNumberOfQueries(1);
                        queriesCounters.add(qc);
                    } else {
                        if (queriesCounters.size() > 0) {
                            boolean userInUse = false;
                            for (QueriesCounter obj : queriesCounters) {
                                if (obj.getUserName().equalsIgnoreCase(username)) {
                                    userInUse = true;
                                }
                            }
                            if (userInUse == true) {
                                for (QueriesCounter obj : queriesCounters) {
                                    if (obj.getUserName().equalsIgnoreCase(username)) {
                                        int noQueries = obj.getNumberOfQueries();
                                        obj.setNumberOfQueries(noQueries + 1);
                                    }
                                }
                            } else {
                                QueriesCounter qc = new QueriesCounter();
                                qc.setUserName(username);
                                qc.setNumberOfQueries(1);
                                queriesCounters.add(qc);
                            }
                        }
                    }
                }
            } else {
                System.out.println("No queries performed");
            }
        }
    }


    public static void readLogFile() throws IOException {
        boolean fileExits = Files.exists(Path.of("src/Logs/QueryLogs.txt"));
        if (fileExits) {
            File generalLogs = new File(generalLogsFilePath);
            FileReader logReader = new FileReader(generalLogs);
            BufferedReader reader = new BufferedReader(logReader);
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
//                System.out.println(nextLine);
                logs.add(nextLine);
            }
        }
    }

}
