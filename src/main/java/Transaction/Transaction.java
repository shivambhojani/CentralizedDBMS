package Transaction;

import Query.QueryEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transaction {

    public double init() throws IOException, InterruptedException {
        System.out.println("Starting Transaction......");

        boolean isTrue = true;
        List<String> queries = new ArrayList<String>();

        while (isTrue) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Write Query: ");
            String input = sc.nextLine();
            if (input.length() > 0) {
                if (input.equalsIgnoreCase("commit;") || input.equalsIgnoreCase("commit")) {
                    isTrue = false;
                } else if (input.equalsIgnoreCase("rollback;") || input.equalsIgnoreCase("rollback")) {
                    isTrue = false;
                    queries.clear();
                } else {
                    queries.add(input);
                }
            }
        }
        QueryEngine queryEngine = new QueryEngine();

        double startTime = System.nanoTime();
        for (String item : queries) {

            queryEngine.execute(item);
        }
        double endTime = System.nanoTime();

        return endTime - startTime;
    }
}
