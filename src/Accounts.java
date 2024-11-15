import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Accounts {
    private Connection connect;
    private Scanner scanner;

    //    create a constructor;
    public Accounts(Connection connect, Scanner scanner) {
        this.connect = connect;
        this.scanner = scanner;
    }

    //    create a function to check account of a user exists or not;
    boolean accountExists(String email) {
        String acc_ExistCheckQuery = "SELECT * FROM accounts WHERE email = ?;";
        try {
            PreparedStatement preStatement = connect.prepareStatement(acc_ExistCheckQuery);
            preStatement.setString(1, email);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //    create a function to generate account num
    long getAccountNum(String email) {
        String getAccNumQuery = "SELECT acc_num FROM accounts WHERE email = ?;";
        try {
            PreparedStatement prepStatement = connect.prepareStatement(getAccNumQuery);
            prepStatement.setString(1, email);
            ResultSet result = prepStatement.executeQuery();
            if (result.next()) {
                return result.getLong("acc_num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }

    //    create openAccount function ;
    long openAccount(String email) {
        if(!accountExists(email)){
            scanner.nextLine();
            System.out.print("Enter full name: ");
            String name = scanner.nextLine();
            System.out.print("Deposit initial Amount: ");
            int balance = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Security pin: ");
            String securityPin = scanner.nextLine();
            String openAccQuery = "INSERT INTO accounts (acc_num,full_name, email, balance,security_pin) VALUES(?,?,?,?,?);";

            try{
                long account_Number = generateAccountNum();
                PreparedStatement prepStatement = connect.prepareStatement(openAccQuery);
                prepStatement.setLong(1,account_Number);
                prepStatement.setString(2,name);
                prepStatement.setString(3,email);
                prepStatement.setInt(4,balance);
                prepStatement.setString(5,securityPin);
                int rowsAffected = prepStatement.executeUpdate();
                if(rowsAffected > 0){
                    return account_Number;
                }else{
                    throw new RuntimeException("Account Creation Failed");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account already exits");
    }

//    create a function to generate account number;
    long generateAccountNum(){
        long number = 1000000 + (long)Math.floor((Math.random() * 9000000) + 1);
        return number;
    }
}
