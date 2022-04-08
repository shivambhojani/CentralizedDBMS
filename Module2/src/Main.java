import java.util.Scanner;

public class Main {

    public static void main(String [] args)
    {
        QueryEngine engine = new QueryEngine("mydatabase");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a query: ");
        String query = "";
        query = sc.nextLine();

        boolean res = engine.execute(query);
    }

}
