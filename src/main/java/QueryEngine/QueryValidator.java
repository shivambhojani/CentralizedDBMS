package QueryEngine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Bijitashya on 04, 2022
 */
public class QueryValidator {
    // checks if the 'create database <dbname>' query is valid
    public static boolean validateCreateDatabaseQuery (List<String> queryChunks) {
        if (queryChunks.size() != 3) {
            System.out.println("Too many or few arguments provided in query.");
            return false;
        } else if (checkIfDBExists(queryChunks.get(2))) {
            System.out.println("Database already exists.");
            return false;
        }

        return true;
    }

    // checks if the database directory exists
    public static boolean checkIfDBExists (String dbName) {
        return Files.exists(Path.of(dbName));
    }

    // checks if the 'use <dbname>' query is valid
    public static boolean validateUseDatabaseQuery (List<String> queryChunks) {
        if (queryChunks.size() != 2) {
            System.out.println("Too many or few arguments provided in query.");
            return false;
        } else if (!checkIfDBExists(queryChunks.get(1))) {
            System.out.println("No such database exists.");
            return false;
        }

        return true;
    }
}
