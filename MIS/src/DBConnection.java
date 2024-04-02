import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//ВЫУЧИТЬ DBConnect
public class DBConnection {
    Connection con;
    public static Connection getConnect(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            String url = "jdbc:postgresql://localhost:5431/postgres";
            String login = "postgres";
            String password = "root";
            return DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
