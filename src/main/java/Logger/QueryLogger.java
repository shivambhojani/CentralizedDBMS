package Logger;

import Configs.StaticData;
import FileManager.DirectoryCreator;
import FileManager.FileCreator;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class QueryLogger {

    public void logQuery(String database, String query, String userId, String queryType) throws IOException {

        dbFileChecker(database);
        logFileChecker(database);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String path = StaticData.logPath + "/" + database + "/" + StaticData.queryLogsFileName;
            FileWriter file = new FileWriter(path,true);

            simpleDateFormat.format(timestamp);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("queryType", queryType);
            jsonObject.put("userId", userId);
            jsonObject.put("query", query);
            jsonObject.put("timeStamp", String.valueOf(timestamp));


            file.write(jsonObject.toJSONString());
            file.write("\n");
            file.flush();
            file.close();

    }

    public void dbFileChecker(String database) {

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

    public void logFileChecker(String database) throws IOException {

        FileCreator fileCreator = new FileCreator();
        fileCreator.checkOrCreateFile(database, StaticData.queryLogType);

        //        String path = StaticData.logPath + "/" + database + "/" + StaticData.queryLogsFileName;
//        File file = new File(path);
//        if (file.exists()) {
//            return;
//        } else {
//            file.createNewFile();
//            System.out.println("QueryLog.json file created under Directory: " + database);
//            return;
//        }
    }

}
