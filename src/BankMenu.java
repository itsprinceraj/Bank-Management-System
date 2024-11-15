import java.sql.Connection;
import java.util.Scanner;

public class BankMenu {
    private Connection connect;
    private Scanner scanner;

    //    create a constructor;
    public BankMenu(Connection connect, Scanner scanner) {
        this.connect = connect;
        this.scanner = scanner;
    }

    //     create a exit method;
    public void exit() {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
        }
    }
}
