package Query;
import java.io.IOException;
import java.util.Scanner;

public class EngineController {

    public static void printMenu()
    {
        System.out.print("sql > ");
    }

    public static void startEngine() throws IOException, InterruptedException {
        String userInput = "";
        QueryEngine engine = new QueryEngine();
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            printMenu();
            userInput = sc.nextLine();
            if(userInput.toLowerCase().contains("exit"))
            {
                System.out.println("Exiting query engine!");
                break;
            }
            else
            {
                engine.execute(userInput);
            }
        }

    }

    public static void main(String [] args) throws IOException, InterruptedException {
        EngineController.startEngine();
    }

}
