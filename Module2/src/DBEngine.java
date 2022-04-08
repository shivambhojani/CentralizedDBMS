import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DBEngine {

    public DBEngine(){};

    public static List<HashMap<String, String>> getTableMetadata(String dbName, String tableName)
    {
        List<HashMap<String, String>> metadata = new ArrayList<>();

        try {
            String filePath = Path.of(dbName + "/" + "meta.txt").toString();
            BufferedReader fileHandle = new BufferedReader(new FileReader(filePath));

            String line;
            // Condition holds true till
            // there is character in a string
            while ((line = fileHandle.readLine()) != null && (line.strip().split("\\|")[0].matches(tableName)))
            {
                System.out.println(line + " ");
                String [] data = line.strip().split("\\|");

                if(data[2].toLowerCase().matches("varchar"))
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", data[1]);
                    map.put("type", data[2]);
                    map.put("size", data[3]);
                    metadata.add(map);
                }
                else
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", data[1]);
                    map.put("type", data[2]);
                    metadata.add(map);
                }

            }

            fileHandle.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return metadata;
    }

    public static List<String> getTableHeadings(String dbName, String tableName)
    {
        List<String> tableHeader = new ArrayList<>();

        try {
            String filePath = Path.of(dbName + "/" + tableName + ".txt").toString();
            BufferedReader fileHandle = new BufferedReader(new FileReader(filePath));

            String line = fileHandle.readLine();
            String [] data = line.strip().split("\\|");

            for(int cnt=0; cnt<data.length; cnt++)
            {
                tableHeader.add(data[cnt].strip());
            }

            fileHandle.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableHeader;
    }

    public static int getMaxRecords(String dbName, String tableName)
    {
        int len = 0;

        try {
            BufferedReader fileHandle = new BufferedReader(new FileReader(dbName + "/" + tableName + ".txt"));

            String line;
            // Condition holds true till
            // there is character in a string
            while ((line = fileHandle.readLine()) != null)
            {
                len+= 1;
            }

            fileHandle.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        len -= 1;
        return len;
    }

    public static List<String> getTableData(String dbName, String tableName)
    {
        List<String> data = new ArrayList<>();
        try {
            BufferedReader fileHandle = new BufferedReader(new FileReader(dbName + "/" + tableName + ".txt"));

            String line;
            // Condition holds true till
            // there is character in a string
            while ((line = fileHandle.readLine()) != null)
            {
                data.add(line.strip());
            }

            fileHandle.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static boolean insertRecord(String dbName, String tableName, String [] values)
    {
        boolean res = false;
        String record = "";

        for(int cnt=0; cnt < values.length; cnt++)
        {
            record += values[cnt].replace("'","").strip();

            if(cnt < values.length - 1)
            {
                record+= "|";
            }
        }

        record += "\n";


        try {
            BufferedWriter fileHandle = new BufferedWriter(new FileWriter(dbName + "/" + tableName + ".txt", true));

            fileHandle.write(record);
            fileHandle.close();
            res = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static boolean overwriteFile(String dbName, String tableName, List<String> data)
    {
        boolean res = false;

        try {
            BufferedWriter fileHandle = new BufferedWriter(new FileWriter(dbName + "/" + tableName + ".txt"));

            for(int cnt=0; cnt<data.size(); cnt++)
            {
                fileHandle.write(data.get(cnt) + "\n");
            }

            fileHandle.close();
            res = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static boolean updateRecord(String dbName, String tableName, HashMap<String, String> criteria, HashMap<String, String> column)
    {
        boolean res = true;

        List<String> tableData = getTableData(dbName, tableName);
        List<String> tableHeadings = Arrays.asList(tableData.get(0).split("\\|"));

        int criteriaIndex = tableHeadings.indexOf(criteria.get("name"));
        int updateIndex = tableHeadings.indexOf(column.get("name"));

        for(int cnt=0; cnt<tableData.size(); cnt++)
        {
            String[] values = tableData.get(cnt).split("\\|");

            if(criteria.get("criteria").matches("=") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) == 0))
            {
                values[updateIndex] = column.get("value");

                String record = "";
                for(int colCnt=0; colCnt < values.length; colCnt++)
                {
                    record += values[colCnt].replace("'","").strip();

                    if(colCnt < values.length - 1)
                    {
                        record+= "|";
                    }
                }

                record += "\n";
                tableData.set(cnt, record);
            }
            else if(criteria.get("criteria").matches(">") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) > 0))
            {
                values[updateIndex] = column.get("value");

                String record = "";
                for(int colCnt=0; colCnt < values.length; colCnt++)
                {
                    record += values[colCnt].replace("'","").strip();

                    if(colCnt < values.length - 1)
                    {
                        record+= "|";
                    }
                }

                record += "\n";
                tableData.set(cnt, record);
            }
            else if(criteria.get("criteria").matches("<") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) < 0))
            {
                values[updateIndex] = column.get("value");

                String record = "";
                for(int colCnt=0; colCnt < values.length; colCnt++)
                {
                    record += values[colCnt].replace("'","").strip();

                    if(colCnt < values.length - 1)
                    {
                        record+= "|";
                    }
                }

                record += "\n";
                tableData.set(cnt, record);
            }

        }

        overwriteFile(dbName, tableName, tableData);

        return res;
    }

    public static boolean deleteRecord(String dbName, String tableName, HashMap<String, String> criteria)
    {
        boolean res = true;

        List<String> tableData = getTableData(dbName, tableName);
        List<String> tableHeadings = Arrays.asList(tableData.get(0).split("\\|"));

        int criteriaIndex = tableHeadings.indexOf(criteria.get("name"));

        for(int cnt=1; cnt<tableData.size(); cnt++)
        {
            String[] values = tableData.get(cnt).split("\\|");

            if(criteria.get("criteria").matches("=") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) == 0))
            {
                tableData.remove(cnt);
            }
            else if(criteria.get("criteria").matches(">") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) > 0))
            {
                tableData.remove(cnt);
            }
            else if(criteria.get("criteria").matches("<") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) < 0))
            {
                tableData.remove(cnt);
            }

        }

        res = overwriteFile(dbName, tableName, tableData);
        return res;
    }

    public static boolean selectRecord(String dbName, String tableName, HashMap<String, String> criteria)
    {
        boolean res = true;

        List<String> tableData = getTableData(dbName, tableName);
        List<String> tableHeadings = Arrays.asList(tableData.get(0).split("\\|"));

        int criteriaIndex = tableHeadings.indexOf(criteria.get("name"));

        System.out.println("\n" + tableData.get(0));

        for(int cnt=1; cnt<tableData.size(); cnt++)
        {
            String[] values = tableData.get(cnt).split("\\|");

            if(criteria.get("criteria").matches("=") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) == 0))
            {
                System.out.println(tableData.get(cnt));
            }
            else if(criteria.get("criteria").matches(">") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) > 0))
            {
                System.out.println(tableData.get(cnt));
            }
            else if(criteria.get("criteria").matches("<") &&
                    (values[criteriaIndex].compareTo(criteria.get("value")) < 0))
            {
                System.out.println(tableData.get(cnt));
            }

        }

        return res;
    }

}
