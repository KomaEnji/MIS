import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    Connection con;
    public static Connection getConnect(){
        try {
            System.out.println("Start... ");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Driver successfully connected");
        try {
            String url = "jdbc:postgresql://localhost:5431/postgres";
            String login = "postgres";
            String password = "root";
            Connection con = DriverManager.getConnection(url, login, password);
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
