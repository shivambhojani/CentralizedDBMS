import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ValidationEngine {

    public ValidationEngine() {};

    public static boolean checkIfTableExists(String dbName, String tableName)
    {
        return Files.exists(Path.of(dbName + "/" + tableName + ".txt"));
    }

    private static boolean isValidNumber(String data)
    {
        boolean result = true;

        for(int cnt=0; cnt<data.length(); cnt++)
        {
            char ch = data.charAt(cnt);

            if(!((ch == '-') || (ch >= '0' && ch <= '9')))
            {
                result = false;
                break;
            }
        }

        return result;
    }

    public static boolean validateInsertQuery(String dbName, String query)
    {
        boolean res = true;

        //Separate Input data and query initials
        //Example: Seperating INSERT INTO <tablename> from (value1, value2, ....)
        String [] data = query.split("\\(");

        //Extract string containing all values to be inserted as new record
        String newRecord = data[1].replace("\\)", " ").strip();
        //Separate value for each column
        String [] queryData = newRecord.split(",");

        //Sepearate all keywords in query initials
        data = data[0].split(" ");

        if(checkIfTableExists(dbName, data[2])) //If table exists
        {
            List<HashMap<String, String>> tableMetaData = DBEngine.getTableMetadata(dbName, data[2]);

            if(tableMetaData.size() == queryData.length)
            {
                for(int cnt=0; cnt < queryData.length; cnt++)
                {
                    HashMap<String, String> columnMap = tableMetaData.get(cnt);
                    String columnDatatype =  columnMap.get("type");
                    String columnData = queryData[cnt].replace("'", "");

                    if(columnDatatype.contains("int"))
                    {
                        if(columnDatatype.contains("tiny")) //tiny int
                        {
                            if(columnData.length() > 4 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                        else if(columnDatatype.contains("medium")) //medium int
                        {
                            if(columnData.length() > 8 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                        else if(columnDatatype.contains("big")) //big int
                        {
                            if(columnData.length() > 19 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                        else //int
                        {
                            if(columnData.length() > 11 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                    }
                    else if(columnDatatype.matches("varchar"))
                    {
                        int columnLength = Integer.parseInt(columnMap.get("size"));
                        if(columnData.length() > columnLength)
                        {
                            System.out.println("Input length exceed for column " + columnMap.get("name"));
                            res = false;
                            break;
                        }
                    }
                }

                if(res)
                {
                    int inputID = Integer.parseInt(queryData[0]);
                    if((DBEngine.getMaxRecords(dbName, data[2]) + 1) != inputID)
                    {
                        System.out.println("Invalid Primary key");
                        res = false;
                    }
                }

            }
            else
            {
                System.out.println("Too many or few arguments provided in query.");
                res = false;
            }
        }
        else
        {
            System.out.println("Table does not exist.");
            res = false;
        }

        return res;
    }

    public static boolean validateUpdateQuery(String dbName, String query)
    {
        boolean res = true;
        String [] splits = query.split("SET | WHERE");
        String tableName = splits[0].strip().split(" ")[1].strip();
        //String [] inputData = splits[1].split(",");
        List<String> inputDataList = new ArrayList<>();
        inputDataList.add(splits[1].strip());
        inputDataList.add(splits[2].strip());


        if(checkIfTableExists(dbName, tableName))
        {
            List<HashMap<String, String>> tableMetaData = DBEngine.getTableMetadata(dbName, tableName);
            List<String> tableHeadings = DBEngine.getTableHeadings(dbName, tableName);

            for(int cnt=0; cnt < inputDataList.size(); cnt++)
            {
                String colmnName;
                String columnData;

                if(cnt < inputDataList.size()-1)
                {
                    colmnName = inputDataList.get(cnt).split("=")[0].strip();
                    columnData = inputDataList.get(cnt).split("=")[1].replace("'","").strip();
                }
                else
                {
                    colmnName = inputDataList.get(cnt).replaceAll("[><=]", " ").strip().split(" ")[0];;
                    columnData = inputDataList.get(cnt).replaceAll("[><=]", " ").strip().split(" ")[1];;
                }


                if(tableHeadings.contains(colmnName))
                {
                   int columnIndex = tableHeadings.indexOf(colmnName);
                   //System.out.println(columnIndex);
                   HashMap<String, String> columnMap = tableMetaData.get(columnIndex);
                   String columnDatatype = columnMap.get("type");

                    if(columnDatatype.contains("int"))
                    {
                        if(columnDatatype.contains("tiny")) //tiny int
                        {
                            if(columnData.length() > 4 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                        else if(columnDatatype.contains("medium")) //medium int
                        {
                            if(columnData.length() > 8 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                        else if(columnDatatype.contains("big")) //big int
                        {
                            if(columnData.length() > 19 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                        else //int
                        {
                            if(columnData.length() > 11 || !isValidNumber(columnData))
                            {
                                System.out.println("Datatype mismatch for query input");
                                res = false;
                                break;
                            }
                        }
                    }
                    else if(columnDatatype.matches("varchar"))
                    {
                        int columnLength = Integer.parseInt(columnMap.get("size"));
                        if(columnData.length() > columnLength)
                        {
                            System.out.println("Input length exceed for column " + columnMap.get("name"));
                            res = false;
                            break;
                        }
                    }
                }
                else
                {
                    System.out.println("Invalid column name in the query");
                    res = false;
                    break;
                }

            }

        }
        else
        {
            System.out.println("Table does not exist!");
            res = false;
        }

        return res;
    }

    public static boolean validateDeleteQuery(String dbName, String query)
    {
        boolean res = true;

        String [] splits = query.split("FROM | WHERE");
        String tableName = splits[1].strip();

        if(checkIfTableExists(dbName, tableName))
        {
            List<HashMap<String, String>> tableMetaData = DBEngine.getTableMetadata(dbName, tableName);
            List<String> tableHeadings = DBEngine.getTableHeadings(dbName, tableName);

            String criteriaColName = splits[2].replaceAll("[><=]", " ").strip().split(" ")[0];
            String criteriaColData = splits[2].replaceAll("[><=]", " ").strip().split(" ")[1];

            if(tableHeadings.contains(criteriaColName))
            {
                int columnIndex = tableHeadings.indexOf(criteriaColName);

                HashMap<String, String> columnMap = tableMetaData.get(columnIndex);
                String columnDatatype = columnMap.get("type");

                if(columnDatatype.contains("int"))
                {
                    if(columnDatatype.contains("tiny")) //tiny int
                    {
                        if(criteriaColData.length() > 4 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                    else if(columnDatatype.contains("medium")) //medium int
                    {
                        if(criteriaColData.length() > 8 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                    else if(columnDatatype.contains("big")) //big int
                    {
                        if(criteriaColData.length() > 19 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                    else //int
                    {
                        if(criteriaColData.length() > 11 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                }
                else if(columnDatatype.matches("varchar"))
                {
                    int columnLength = Integer.parseInt(columnMap.get("size"));
                    if(criteriaColData.length() > columnLength)
                    {
                        System.out.println("Input length exceed for column " + columnMap.get("name"));
                        res = false;
                    }
                }
            }
            else
            {
                System.out.println("Invalid column name in the query");
                res = false;
            }

        }
        else
        {
            System.out.println("Table does not exist!");
            res = false;
        }

        return res;
    }

    public static boolean validateSelectQuery(String dbName, String query)
    {
        boolean res = true;

        String [] splits = query.split("SELECT | FROM | WHERE");
        String tableName = splits[2].strip();

        if(checkIfTableExists(dbName, tableName))
        {
            List<HashMap<String, String>> tableMetaData = DBEngine.getTableMetadata(dbName, tableName);
            List<String> tableHeadings = DBEngine.getTableHeadings(dbName, tableName);

            String criteriaColName = splits[3].replaceAll("[><=]", " ").strip().split(" ")[0];
            String criteriaColData = splits[3].replaceAll("[><=]", " ").strip().split(" ")[1];

            if(tableHeadings.contains(criteriaColName))
            {
                int columnIndex = tableHeadings.indexOf(criteriaColName);

                HashMap<String, String> columnMap = tableMetaData.get(columnIndex);
                String columnDatatype = columnMap.get("type");

                if(columnDatatype.contains("int"))
                {
                    if(columnDatatype.contains("tiny")) //tiny int
                    {
                        if(criteriaColData.length() > 4 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                    else if(columnDatatype.contains("medium")) //medium int
                    {
                        if(criteriaColData.length() > 8 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                    else if(columnDatatype.contains("big")) //big int
                    {
                        if(criteriaColData.length() > 19 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                    else //int
                    {
                        if(criteriaColData.length() > 11 || !isValidNumber(criteriaColData))
                        {
                            System.out.println("Datatype mismatch for query input");
                            res = false;
                        }
                    }
                }
                else if(columnDatatype.matches("varchar"))
                {
                    int columnLength = Integer.parseInt(columnMap.get("size"));
                    if(criteriaColData.length() > columnLength)
                    {
                        System.out.println("Input length exceed for column " + columnMap.get("name"));
                        res = false;
                    }
                }
            }
            else
            {
                System.out.println("Invalid column name in the query");
                res = false;
            }

        }
        else
        {
            System.out.println("Table does not exist!");
            res = false;
        }

        return res;
    }

}
