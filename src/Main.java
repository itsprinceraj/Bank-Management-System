import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    //     Declare private
    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String userName = "root";
    private static final String pass = "5466";

    public static void main(String[] args) {

        // load drivers;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        db connection;
        try {
            Connection connect = DriverManager.getConnection(url, userName, pass);
            Scanner scanner = new Scanner(System.in);

            // take instance of each classes over there;
            Users users = new Users(connect, scanner);
            Accounts accounts = new Accounts(connect, scanner);
            ManageAccounts manageAcc = new ManageAccounts(connect, scanner);
            BankMenu bankMenu = new BankMenu(connect, scanner);

            String email;
            long accountNum;

//             show a menu of choices ;
            while (true) {
                System.out.println("Banking Management System");
                System.out.println("1. Register.");
                System.out.println("2. Login.");
                System.out.println("3. Exit.");
                System.out.print("Enter you choice: ");
                int choice1 = scanner.nextInt();
                System.out.println();

//                match the choice and apply functionality;
                switch (choice1) {
                    case 1:
                        users.register();
                        break;

                    case 2:
                        email = users.login();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User Logged In");
                            if (!accounts.accountExists(email)) {
                                System.out.println();
                                System.out.println("1. Open a new Bank Account.");
                                System.out.println("2. Exit.");
                                if (scanner.nextInt() == 1) {
                                    accountNum = accounts.openAccount(email);
                                    System.out.println("Account created successfully");
                                    System.out.println("Your Account number is: " + accountNum);
                                } else {
                                    break;
                                }
                            }

// if bank account already exists then show internal features of bank;
                            accountNum = accounts.getAccountNum(email);
                            int choice2 = 0;
                            while (choice2 != 5) {
                                System.out.println();
                                System.out.println("1. Make a withdrawal.");
                                System.out.println("2. Deposit Money.");
                                System.out.println("3. Transfer Money.");
                                System.out.println("4. Check Balance.");
                                System.out.println("5. Logout.");
                                System.out.print("Enter you choice: ");
                                choice2 = scanner.nextInt();

//            check choice and give various options;
                                switch (choice2) {
                                    case 1:
                                        manageAcc.withdrawMoney(accountNum);
                                        break;

                                    case 2:
                                        manageAcc.depositMoney(accountNum);
                                        break;

                                    case 3:
                                        manageAcc.transferMoney(accountNum);
                                        break;

                                    case 4:
                                        manageAcc.checkBalance(accountNum);
                                        break;

                                    case 5:
                                        break;

                                    default:
                                        System.out.println("Enter a valid choice");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Invalid Email Password!");
                        }
                        break;

                    case 3:
                        bankMenu.exit();
                        return;

                    default:
                        System.out.println("Enter a valid choice.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}