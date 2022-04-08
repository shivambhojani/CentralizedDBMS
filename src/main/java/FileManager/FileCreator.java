package FileManager;

import Configs.StaticData;

import java.io.File;
import java.io.IOException;


public class FileCreator {

    public void checkOrCreateFile(String database, String fileType) throws IOException {

        if (fileType.equalsIgnoreCase(StaticData.queryLogType)) {
            String path = StaticData.logPath + "/" + database + "/" + StaticData.queryLogsFileName;
            File file = new File(path);
            if (file.exists()) {
                return;
            } else {
                file.createNewFile();
                System.out.println("Query Log file created under Directory: " + database);
                return;
            }
        }
    }

    public void checkOrCreateFile(String fileType) throws IOException {
        if (fileType.equalsIgnoreCase(StaticData.generalLogType)){
            String path = StaticData.logPath  + "/" + StaticData.generalLogsFileName;
            File file = new File(path);
            if (file.exists()) {
                return;
            } else {
                file.createNewFile();
                System.out.println("General Log file created under Directory: " + StaticData.logPath);
                return;
            }
        }

    }

}
