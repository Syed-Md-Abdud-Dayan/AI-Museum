import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/museum_manage"; // Replace with your database URL
    private static final String USER = "root";  // Replace with your database username

    public static Connection getConnection() throws SQLException {
        // Create an empty Properties object to pass to the connection
        Properties properties = new Properties();
        properties.put("user", USER); // Add user informat
        // Use the Properties object in the getConnection method
        return DriverManager.getConnection(URL, properties);
    }
}
