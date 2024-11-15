import javax.xml.transform.Source;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Users {
    private Connection connect;
    private Scanner scanner;

    //    create a constructor;
    public Users(Connection connect, Scanner scanner) {
        this.connect = connect;
        this.scanner = scanner;
    }


    //   create user Registration method;
    public void register() {
        scanner.nextLine();
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter password: ");
        String pass = scanner.nextLine();

//         check if the email is already registered;
        if (userExists(email)) {
            System.out.println("This email is already registered with us!");
            return;
        }

// if email is not registered with us then save the user Details into the database;
        String registerQuery = "INSERT INTO users (full_name, email, password) values(? ,? ,?)";
        try {
            PreparedStatement prepStatement = connect.prepareStatement(registerQuery);
            prepStatement.setString(1, name);
            prepStatement.setString(2, email);
            prepStatement.setString(3, pass);
            int rowAffected = prepStatement.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Registration Successful");
            } else {
                System.out.println("Registration Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //create a function to check if user already registered or not  ;
    public boolean userExists(String email) {
        String userExistQuery = "SELECT * FROM users WHERE email = ? ;";
        try {
            PreparedStatement prepStatement = connect.prepareStatement(userExistQuery);
            prepStatement.setString(1, email);
            ResultSet result = prepStatement.executeQuery();
            if (result.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //    create a function for user Login ;
    public String login() {
        ResultSet result;
        scanner.nextLine();
        System.out.print("Enter email id: ");
        String email = scanner.nextLine();
        System.out.println("Enter Password: ");
        String pass = scanner.nextLine();
        if (userExists(email)) {
//            if user exist then match the password;
            String loginQuery = "SELECT * FROM users WHERE email = ? AND password = ?;";
            try {
                PreparedStatement prepStatement = connect.prepareStatement(loginQuery);
                prepStatement.setString(1, email);
                prepStatement.setString(2, pass);
                result = prepStatement.executeQuery();
                if (result.next()) {
                    return email;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
