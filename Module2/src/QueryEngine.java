import java.util.*;

public class QueryEngine {

    private String record;
    private String dbName;

    public QueryEngine(String database_name)
    {
        dbName = database_name;
    }

    //This method checks for the table in the database
    //If the table exist, return false else true.
    //The purpose of this method is to verify if table exist or not
    //before creating a new table
    private boolean isUniqueTable(String tableName)
    {
        return true;
    }

    //This method returns the metadata for a table
    //The return type depends upon data structures being used
    //in module 1
    private HashMap<String, String> getMetadata(String tableName)
    {
        HashMap<String, String> tmp = new HashMap<>();
        return tmp;
    }

    //This method checks for the database
    //If the database exist, return false else true.
    //The purpose of this method is to verify if database exist or not
    //before creating a new database
    private boolean isUniqueDB(String databaseName)
    {
        return true;
    }

    /**
     * This method creates the database in the application.
     * The method validates the query for all necessary parameters
     * passed as argument and calls method from module 1 to create
     * the database
     * @param query array of keywords passed in query as string type
     * @return true if database created successfully, else false
     */
    private boolean createDB(String[] query)
    {
        int total_keywords = 3;
        boolean result = false;

        //If query has exactly 3 keywords Ex. CREATE DATABASE sample
        if(query.length == 3)
        {
            //If database does not exist with same name
            // try to create db, else display error
            if(isUniqueDB(query[2]))
            {
                //Call method from module 1 to create a new database
                //The method must return true if success, else false
                //If true, print success message
                //Example objModule1.createDB(database_name);
                result = true;
                System.out.println("Database created successfully!");
            }
            else
            {
                System.out.println("Error: Database already exist with name " + query[2]);
            }
        }
        else
        {
            System.out.println("Error: Invalid Command Parameters! Please try again.");
        }

        return result;
    }

    /**
     * This method sets the current the database in the application.
     * The method validates the query for all necessary parameters
     * passed as argument and calls method from module 1 to select
     * the database
     * @param query array of keywords passed in query as string type
     * @return true if database selected successfully, else false
     */
    private boolean useDB(String[] query)
    {
        int total_keywords = 3;
        boolean result = false;

        //If query has exactly 3 keywords Ex. USE DATABASE sample
        if(query.length == 3)
        {
            //If database exist with same name
            // execute query, else display error
            if(!isUniqueDB(query[2]))
            {
                //Call method from module 1 to select database as existing
                //The method must return true if success, else false
                //If true, print success message
                //Example objModule1.useDB(database_name);
                result = true;
                System.out.println("Database selected successfully!");
            }
            else
            {
                System.out.println("Error: Database does not exist with name " + query[2]);
            }
        }
        else
        {
            System.out.println("Error: Invalid Command Parameters! Please try again.");
        }

        return result;
    }

    /**
     * This method creates a new table in the database application.
     * The method validates the query, checks if table is unique or not
     * and creates a new table if it does not exist.
     * It calls a method from module 1 to create a table. The required
     * method must accept a hashmap<String, String> as param containing all
     * table details. The method will create the table. Refer to this method
     * for more details on the structure of hashmap data.
     * @param table_name Name of the table to be created
     * @param query CREATE TABLE query
     * @return true if table created, else false
     */
    private boolean createTable(String table_name, String query)
    {
        boolean result = true;

        if(isUniqueTable(table_name))
        {
            String [] queryData = query.split("\\(");
            String table_params = queryData[1].replace("\\(\\)", "");
            queryData = table_params.split(",");

            HashMap<String, String> tableMap = new HashMap<>();
            int total_cols = 0;
            tableMap.put("name", table_name);

            for(int cnt=0;cnt< queryData.length; cnt++)
            {
                String [] colData = queryData[cnt].split(" ");

                if(colData.length == 2)
                {
                    total_cols+= 1;
                    tableMap.put("col_" + total_cols + "_name", colData[0]);
                    tableMap.put("col_" + total_cols + "_datatype", colData[1]);
                    tableMap.put("col_" + total_cols + "_is_null", "false");
                }
                else if(colData.length == 4 && colData[2].matches("NOT") && colData[3].matches("NULL"))
                {
                    total_cols+= 1;
                    tableMap.put("col_" + total_cols + "_name", colData[0]);
                    tableMap.put("col_" + total_cols + "_datatype", colData[1]);
                    tableMap.put("col_" + total_cols + "_is_null", "true");
                }
                else if(colData.length == 3 && colData[0].matches("PRIMARY"))
                {
                    boolean validPrimaryKey = false;
                    for(int colCount = 1; colCount <= total_cols; colCount++)
                    {
                        String colName = tableMap.get("col_" + colCount + "_name");

                        if(colName.matches(colData[2]))
                        {
                            tableMap.put("primary_key", colData[2]);
                            colCount = total_cols + 1; //To exit for loop
                            validPrimaryKey = true;
                        }
                    }

                    if(!validPrimaryKey)
                    {
                        System.out.println("Error! Invalid Primary Key Constraint.");
                        result = false;
                        break;
                    }
                }
                else if(colData.length == 6 && colData[0].matches("FOREIGN") && colData[1].matches("KEY"))
                {
                    tableMap.put("foreign_key", colData[2]);
                    tableMap.put("foreign_key_table", colData[4]);
                    tableMap.put("foreign_key_reference", colData[5]);
                }
            }

            if(result)
            {
                tableMap.put("total_columns", Integer.toString(total_cols));
                //require a method from module 1 to be called
                //hashmap will be passed to that method to create the table
                //hashmap will contain following keys and associated values:
                //name: (name of the table as value)
                //total_columns: (no. of columns as string ex. 5 or 10 or 3)
                //col_[columnCount]_name: name of column 1 till total columns (each key has a name associated)
                //col_[columnCount]_datatype: datatype for a particular column
                //primary_key: primary_key if any
                //foreign_key: foreign key if any
                //foreign_key_table: table associated in foreign key column
                //foreign_key_reference: foreign key column name in the associated table
                System.out.println("Table created successfully!");
            }

        }
        else
        {
            System.out.println("Error: Table already exists with name " + table_name);
            result = false;
        }

        return result;
    }

    private boolean insertRecord(String table_name, String query)
    {
        boolean result = false;

        String [] data = query.split("\\(");

        String newRecord = data[1].replace(")", " ").strip();
        String [] queryData = newRecord.split(",");

        if(ValidationEngine.validateInsertQuery(dbName, query))
        {
            result = DBEngine.insertRecord(dbName, table_name, queryData);
        }

        return result;
    }

    private boolean selectRecord(String table_name, String query)
    {
        boolean res = false;

        if(ValidationEngine.validateSelectQuery(dbName, query))
        {
            String [] splits = query.split("SELECT | FROM | WHERE");

            HashMap<String, String> criteria = new HashMap<>();
            criteria.put("name", splits[3].replaceAll("[><=]", " ").strip().split(" ")[0]);
            String val = splits[3].replaceAll("[><=]", " ").strip().split(" ")[1];
            val = val.replaceAll("\'", "").strip();
            criteria.put("value", val);

            if(splits[3].contains("="))
            {
                criteria.put("criteria", "=");
            }
            else if(splits[3].contains(">"))
            {
                criteria.put("criteria", ">");
            }
            else if(splits[3].contains("<"))
            {
                criteria.put("criteria", "<");
            }

            res = DBEngine.selectRecord(dbName, table_name, criteria);
        }



        return res;
    }

    private boolean updateRecord(String table_name, String query)
    {
        boolean result = false;

        if(ValidationEngine.validateUpdateQuery(dbName, query))
        {
            String [] splits = query.split("SET | WHERE");

            HashMap<String, String> criteria = new HashMap<>();
            criteria.put("name", splits[2].replaceAll("[><=]", " ").strip().split(" ")[0]);
            criteria.put("value", splits[2].replaceAll("[><=]", " ").strip().split(" ")[1]);

            if(splits[2].contains("="))
            {
                criteria.put("criteria", "=");
            }
            else if(splits[2].contains(">"))
            {
                criteria.put("criteria", ">");
            }
            else if(splits[2].contains("<"))
            {
                criteria.put("criteria", "<");
            }

            HashMap<String, String> updateColumn = new HashMap<>();
            updateColumn.put("name", splits[1].replaceAll("[=]", " ").strip().split(" ")[0]);
            updateColumn.put("value", splits[1].replaceAll("[=]", " ").strip().split(" ")[1]);

            result = DBEngine.updateRecord(dbName, table_name, criteria, updateColumn);
        }

        return result;
    }

    private boolean deleteRecord(String table_name, String query)
    {
        boolean res = true;

        if(ValidationEngine.validateDeleteQuery(dbName, query))
        {
            String [] splits = query.split("FROM | WHERE");

            HashMap<String, String> criteria = new HashMap<>();
            criteria.put("name", splits[2].replaceAll("[><=]", " ").strip().split(" ")[0]);
            criteria.put("value", splits[2].replaceAll("[><=]", " ").strip().split(" ")[1]);

            if(splits[2].contains("="))
            {
                criteria.put("criteria", "=");
            }
            else if(splits[2].contains(">"))
            {
                criteria.put("criteria", ">");
            }
            else if(splits[2].contains("<"))
            {
                criteria.put("criteria", "<");
            }

            res = DBEngine.deleteRecord(dbName, table_name, criteria);
        }

        return res;
    }

    public boolean execute(String query)
    {
        boolean result = false;

        String [] queryData = query.strip().split(" ");

        switch(queryData[0])
        {

            case "CREATE":

                if(queryData[1].matches("DATABASE"))
                {
                    result = createDB(queryData);
                }
                else if(queryData[1].matches("TABLE"))
                {
                    result = createTable(queryData[2], query);
                }
                else
                {
                    System.out.println("Invalid Query! Please try again.");
                }
                break;

            case "USE":

                result = useDB(queryData);
                break;

            case "INSERT": //working
                result = insertRecord(queryData[2], query);
                break;

            case "SELECT":
                result = selectRecord(queryData[3], query);
                break;

            case "UPDATE": //working
                result = updateRecord(queryData[1], query);
                break;

            case "DELETE":
                result = deleteRecord(queryData[2], query);
                break;

            default:
                System.out.println("Invalid Query! Please try again.");
                break;
        }

        return result;
    }

}
