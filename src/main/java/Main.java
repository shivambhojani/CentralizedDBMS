import QueryEngine.QueryEngine;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Bijitashya on 04, 2022
 */
public class Main {
    public static void main(String[] args) throws IOException {

        startDb();
    }

    public static void startDb() throws IOException {
        int choice;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1. Write Queries");
            System.out.println("Enter a valid choice:");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    QueryEngine queryProcessor = new QueryEngine();
                    queryProcessor.readQueries();
                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }

        }

    }
}
