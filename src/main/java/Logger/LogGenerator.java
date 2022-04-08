package Logger;
import Configs.StaticData;
import FileManager.DirectoryCreator;
import FileManager.FileCreator;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;


public class LogGenerator {

    public void logQuery(String database, String query, Boolean isValid,  int userId, String queryType, int queryTime, int numOfTables, int numOfRecords) throws IOException {

        dbFolderChecker(database);
        querylogFileChecker(database);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String path = StaticData.logPath + "/" + database + "/" + StaticData.queryLogsFileName;
        FileWriter file = new FileWriter(path, true);

        simpleDateFormat.format(timestamp);

        String queryLog = isValid + StaticData.delimiter +  queryType + StaticData.delimiter + userId + StaticData.delimiter + query + StaticData.delimiter
                + timestamp + StaticData.delimiter + queryTime + StaticData.delimiter + numOfTables + StaticData.delimiter + numOfRecords;
//        JSONParser jsonParser = new JSONParser();
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("queryType", queryType);
//            jsonObject.put("userId", userId);
//            jsonObject.put("query", query);
//            jsonObject.put("timeStamp", String.valueOf(timestamp));
//            if (new File(path).length() != 0) {
//                Object obj = jsonParser.parse(new FileReader(path));
//                JSONArray jsonArray = (JSONArray) obj;
//                jsonArray.add(jsonObject);
//                file.write(jsonArray.toJSONString());
//            }
//            else {
//                file.write(jsonObject.toJSONString());
//            }


        file.write(queryLog);
        file.write("\n");
        file.flush();
        file.close();
    }

    public void addToLoginHistory(String userID, String logType) throws IOException {
        generallogFileChecker(logType);
        String path = StaticData.logPath  + "/" + StaticData.generalLogsFileName;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (Objects.equals(logType, StaticData.login)){
            FileWriter file = new FileWriter(path, true);
            String log = userID + " logged in at " + timestamp;
            file.write(log);
            file.write("\n");
            file.flush();
            file.close();
        }
        else if (Objects.equals(logType, StaticData.logout)){
            FileWriter file = new FileWriter(path, true);
            String log = userID + " logged out at " + timestamp;
            file.write(log);
            file.write("\n");
            file.flush();
            file.close();
        }
        else {
            System.out.println("Unable to generate log for the login/logout");
        }
    }



    public void dbFolderChecker(String database) {

        DirectoryCreator directoryCreator = new DirectoryCreator();

        directoryCreator.checkOrCreateDirectory(database);

//        String path = StaticData.logPath + "/" + database;
//        File directory = new File(path);
//        if (directory.exists()) {
//            return true;
//        } else {
//            directory.mkdir();
//            System.out.println("Directory created: " + database);
//            return true;
//        }
    }

    public void querylogFileChecker(String database) throws IOException {

        FileCreator fileCreator = new FileCreator();
        fileCreator.checkOrCreateFile(database, StaticData.queryLogType);

    }


    public void generallogFileChecker(String database) throws IOException {

        FileCreator fileCreator = new FileCreator();
        fileCreator.checkOrCreateFile(StaticData.generalLogType);

    }

}

