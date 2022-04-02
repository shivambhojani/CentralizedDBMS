package FileManager;

import Configs.StaticData;

import java.io.File;
import java.io.IOException;

public class FileCreator {

    public void checkOrCreateFile(String database, String fileType) throws IOException {

        if (fileType.equalsIgnoreCase(StaticData.queryLogType)){
            String path = StaticData.logPath + "/" + database + "/" + StaticData.queryLogsFileName;
            File file = new File(path);
            if (file.exists()) {
                return;
            } else {
                file.createNewFile();
                System.out.println("QueryLog.json file created under Directory: " + database);
                return;
            }
        }

    }

}
