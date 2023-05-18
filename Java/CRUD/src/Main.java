import java.util.Scanner;
import java.io.IOException;
import CRUD.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner userInput = new Scanner(System.in);
        String choice;
        boolean isContinue = true;
        while (isContinue) {
            Utility.clearScreen();
            System.out.print("""
                    Database
                    
                    1.\tRead all data
                    2.\tCreate new data
                    3.\tSearch
                    4.\tUpdate the data
                    5.\tDelete the data

                    Your choice :\s""");
            choice = userInput.next();

            switch (choice) {
                case "1" -> {
                    Utility.title("LIST DATA");
                    Operation.read();
                }
                case "2" -> {
                    Utility.title("CREATE DATA");
                    Operation.create();
                }
                case "3" -> {
                    Utility.title("SEARCH DATA");
                    Operation.search();
                }
                case "4" -> {
                    Utility.title("UPDATE DATA");
                    Operation.update();
                }
                case "5" -> {
                    Utility.title("DELETE DATA");
                    Operation.delete();
                }
                default -> System.err.println("INVALID INPUT\nValid Input : [1-5]");
            }
            System.out.println();
            isContinue = Utility.getYorN("Do you want to redo?");
        }
    }





}
