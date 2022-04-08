package Exporter;

import Authentication.User;
import Configs.StaticData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ExportDump {

    List<String> queryDump = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

//    public void createSQLDump(String database) {
    public void createSQLDump() {

        System.out.println("");
        System.out.print("Enter Database Name: ");
        String dbName  = sc.nextLine();

        Boolean dbExists = checkDatabase(dbName);
        if (dbExists) {
            try {
                String path = StaticData.logPath + "/" + dbName + "/" + StaticData.queryLogsFileName;
                File myObj = new File(path);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String log = myReader.nextLine();
                    String[] temp = log.split("\\|");
                    queryDump.add(temp[3]);
                    // System.out.println(data);
                }
                myReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String s : queryDump) {
                System.out.println(s);
            }
        }
        else {
            System.out.println("Database does not exists");
        }
    }

    public boolean checkDatabase(String databseName){
        String path = StaticData.logPath + "/" + databseName + "/" + StaticData.queryLogsFileName;
        File f = new File(path);
        return f.exists();
    }
}
