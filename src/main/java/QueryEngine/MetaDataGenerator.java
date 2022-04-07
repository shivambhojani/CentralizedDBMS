package QueryEngine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Bijitashya on 04, 2022
 */
public class MetaDataGenerator {

    public static HashMap<String, List<HashMap<String, String>>> tablesMetaData ;

    public MetaDataGenerator() {
        this.tablesMetaData = new HashMap<>();
    }

    public HashMap<String, List<HashMap<String, String>>> getTablesMetaData() {
        return tablesMetaData;
    }

    public void setTablesMetaData(HashMap<String, List<HashMap<String, String>>> tablesMetaData) {
        this.tablesMetaData = tablesMetaData;
    }

    public static void saveTableDataToMetaFile ( String currentDatabase) {
        try {
            // write to file
            String path = Path.of(currentDatabase, "meta.txt").toString();
            FileWriter fw = new FileWriter(path);
            PrintWriter writer = new PrintWriter(fw);

            for (Map.Entry<String, List<HashMap<String, String>>> table: MetaDataGenerator.tablesMetaData.entrySet()) {
                String tableName = table.getKey();
                List<HashMap<String, String>> tableColumns = table.getValue();
                String output = tableName;

                for (HashMap<String, String> column : tableColumns) {
                    String colName = column.get("name");
                    String colType = column.get("type");
                    String colSize = column.get("size");
                    output = output.concat("|").concat(colName);
                    output = output.concat("|").concat(colType);
                    if (colType.equals("varchar")) output = output.concat("|").concat(colSize);

                    writer.println(output);
                    output = tableName;
                }
            }

            writer.close();

            System.out.println("Table created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
