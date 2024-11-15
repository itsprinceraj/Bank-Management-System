import javax.xml.transform.Source;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageAccounts {
    private Connection connect;
    private Scanner scanner;

    //    create a constructor;
    public ManageAccounts(Connection connect, Scanner scanner) {
        this.connect = connect;
        this.scanner = scanner;
    }


    //     create a function for withdrawing money;
    void withdrawMoney(long accNum) throws SQLException {
        System.out.println();
        scanner.nextLine();
        System.out.print("Enter amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String pin = scanner.nextLine();
        String currentBalQuery = "SELECT * FROM accounts WHERE acc_num = ? AND security_pin = ?;";
        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE acc_num = ? AND security_pin = ?;";

        try {
            connect.setAutoCommit(false);
            if (accNum != 0) {
                PreparedStatement prepStatement = connect.prepareStatement(currentBalQuery);
                prepStatement.setLong(1, accNum);
                prepStatement.setString(2, pin);
                ResultSet result = prepStatement.executeQuery();
                int currentBalance;
                if (result.next()) {
                    currentBalance = result.getInt("balance");
                    if (amount <= currentBalance) {
                        PreparedStatement preStatement = connect.prepareStatement(debitQuery);
                        preStatement.setInt(1, amount);
                        preStatement.setLong(2, accNum);
                        preStatement.setString(3, pin);
                        int rowAffect = preStatement.executeUpdate();
                        if (rowAffect > 0) {
                            System.out.println("Withdrawal of amount " + amount + " successful.");
                            connect.commit();
                        } else {
                            System.out.println("Withdrawal failed");
                            connect.rollback();
                            connect.commit();
                        }

                    } else {
                        System.out.println("Insufficient Balance.");
                    }
                } else {
                    System.out.println("Invalid Pin.");
                }
            }
        } catch (SQLException e) {
            connect.rollback();
            connect.setAutoCommit(true);
            e.printStackTrace();
        }
        connect.setAutoCommit(true);

    }

    //    create function for Deposit money
    void depositMoney(long accNum) throws SQLException {
        System.out.println();
        System.out.print("Enter Amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String securityPin = scanner.nextLine();
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE acc_num = ? AND security_pin = ?";
        if (accNum != 0) {
            try {
                connect.setAutoCommit(false);
                PreparedStatement preStmt = connect.prepareStatement(depositQuery);
                preStmt.setInt(1, amount);
                preStmt.setLong(2, accNum);
                preStmt.setString(3, securityPin);
                int rowAffect = preStmt.executeUpdate();
                if (rowAffect > 0) {
                    System.out.println("Rupees " + amount + " Deposited successfully.");
                    connect.commit();

                } else {
                    System.out.println("Invalid Security Pin");
                    connect.rollback();
                    connect.commit();
                }
            } catch (SQLException e) {
                connect.rollback();
                connect.setAutoCommit(true);
                e.printStackTrace();
            }
            connect.setAutoCommit(true);

        } else {
            System.out.println("Invalid account number");
        }
    }

    //    create function for Transfer money;
    void transferMoney(long senderAccNum) throws SQLException {
        System.out.println();
        scanner.nextLine();
        System.out.print("Enter Receiver's Account number: ");
        long receiverAccNum = scanner.nextLong();
        System.out.print("Enter Amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Security Pin: ");
        String security_pin = scanner.nextLine();
        String currentBalQuery = "SELECT balance FROM accounts WHERE acc_num =? AND security_pin = ?;";
        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE acc_num = ? AND security_pin = ?;";
        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE acc_num = ?;";

//        fetch current balance;
        try {
            connect.setAutoCommit(false);
            if (senderAccNum != 0 && receiverAccNum != 0) {
                PreparedStatement preStatement = connect.prepareStatement(currentBalQuery);
                preStatement.setLong(1, senderAccNum);
                preStatement.setString(2, security_pin);
                ResultSet result = preStatement.executeQuery();
                if (result.next()) {
                    int currBal = result.getInt("balance");
                    if (amount <= currBal) {
                        PreparedStatement debitStatement = connect.prepareStatement(debitQuery);
                        PreparedStatement creditStatement = connect.prepareStatement(creditQuery);

//                       // debit from senders account ;
                        debitStatement.setInt(1, amount);
                        debitStatement.setLong(2, senderAccNum);
                        debitStatement.setString(3, security_pin);
                        int debitRowAffect = debitStatement.executeUpdate();

                        // credit into receivers account;
                        creditStatement.setInt(1, amount);
                        creditStatement.setLong(2, receiverAccNum);
                        int creditRowAffect = creditStatement.executeUpdate();

                        if (debitRowAffect > 0 && creditRowAffect > 0) {
                            System.out.println("Transaction of amount: " + amount + " successfull");
                            connect.commit();

                        } else {
                            System.out.println("Transaction Failed ");
                            connect.rollback();
                            connect.commit();

                        }
                    } else {
                        System.out.println("Insufficient Balance");
                    }
                } else {
                    System.out.println("Invalid Pin");
                }
            } else {
                System.out.println("Invalid Account Number");
            }
        } catch (SQLException e) {
            connect.rollback();
            connect.setAutoCommit(true);
            e.printStackTrace();
        }
        connect.setAutoCommit(true);

    }

    //    create function to check balance;
    void checkBalance(long accNum) {
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String securityPin = scanner.nextLine();
        String checkBalQuery = "SELECT balance FROM accounts WHERE acc_num = ? AND security_pin = ?;";
        try {
            PreparedStatement prepStatement = connect.prepareStatement(checkBalQuery);
            prepStatement.setLong(1, accNum);
            prepStatement.setString(2, securityPin);
            ResultSet result = prepStatement.executeQuery();
            if (result.next()) {
                int balance = result.getInt("balance");
                System.out.println("Your Account Balance is: " + balance);
            } else {
                System.out.println("Invalid pin");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
