package QueryEngine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bijitashya on 04, 2022
 */
public class CreateQuery {


    static String currentDatabase= "";
    public HashMap<String, List<HashMap<String, String>>> tablesMetaData ;
    public List<String> userInput;

    public CreateQuery(String currentDB, HashMap metaData) {
        this.currentDatabase = currentDB;
        this.tablesMetaData = new HashMap<>(metaData);
    }

    public CreateQuery( List userInput, HashMap metaData) {
        this.userInput = userInput;
        this.tablesMetaData = new HashMap<>(metaData);
    }

    void processCreateDatabaseQuery(String dbName) throws IOException {
        boolean checkDbExists = Files.exists(Path.of(dbName));
        if(checkDbExists){
            System.out.println("Database already exists");
        }else{
            Files.createDirectory(Path.of(dbName));
            Files.createFile(Path.of(dbName, "meta.txt"));
            System.out.println("Database created successfully");
        }
    }

    public  void processCreateTable(List<String> inputChunks) throws IOException {
        int varcharMaxLength = 8000;
        List<String> queryList = new ArrayList<>(inputChunks);
        // removes the 'create' token
        queryList.remove(0);
        // removes the 'table' token
        queryList.remove(0);

        // stores and removes the 'table name' token
        String tableName = queryList.get(0);
        queryList.remove(0);



        if (queryList.get(0).equals("(") && queryList.get(queryList.size() - 1).equals(")")) {
            // remove '(' from query
            queryList.remove(0);
            // remove last ')' from  query
            queryList.remove(queryList.size() - 1);

            List<String> columns = List.of(String.join(" ", queryList).split(","));

            List<HashMap<String, String>> parsedColumnsMap = new ArrayList<>();
            List<String> columnNameList = new ArrayList<>();

            for (String column : columns) {
                List<String> columnData = new ArrayList<>(List.of(column.trim().split(" ")));
                if (columnData.size() < 2) {
                    System.out.println("Invalid query.");
                    return;
                }

                HashMap<String, String> columnMap = new HashMap<>();

                String columnName = columnData.get(0);
                columnMap.put("name", columnName);
                columnNameList.add(columnName);
                columnData.remove(0);

                String columnType = columnData.get(0);
                columnMap.put("type", columnType);
                columnData.remove(0);

                int columnLength;

                // get varchar field max size if the type is varchar
                if (columnType.equals("varchar")) {
                    if (columnData.size() >= 3 && columnData.get(0).equals("(") && columnData.get(1).matches("\\d+") && columnData.get(2).equals(")")) {
                        columnLength = Integer.parseInt(columnData.get(1));
                    } else {
                        columnLength = varcharMaxLength;
                    }

                    columnMap.put("size", Integer.toString(columnLength));
                } else if (!columnType.equals("int")) {
                    System.out.println("Invalid query.");
                }

                if (columnData.size() == 0) {
                    // no more column data other than field name and type.
                    parsedColumnsMap.add(columnMap);
                    continue;
                }
                parsedColumnsMap.add(columnMap);
            }

            MetaDataGenerator.tablesMetaData.put(tableName, parsedColumnsMap);
            MetaDataGenerator.saveTableDataToMetaFile(currentDatabase);
            Files.createFile(Path.of(currentDatabase, tableName+ ".txt"));

            String columnNamesAsString = String.join("|", columnNameList);
            Files.write(Path.of(currentDatabase, tableName+ ".txt"), columnNamesAsString.getBytes());

        } else {
            System.out.println("Invalid query.");
        }
    }

}
