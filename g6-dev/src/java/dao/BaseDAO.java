
package dao;

/**
 *
 * @author macbook
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {

    protected static String dbURL;
    protected static String dbUsername;
    protected static String dbPassword;

    protected static Connection connection;

    static {
        dbURL = "jdbc:mysql://34.124.242.5:3306/lms_admin?useSSL=false";
        dbUsername = "root";
        dbPassword = "lms@123456";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtain a connection to the database
     *
     * @return Connection instance
     * @throws SQLException on any SQL error
     */
    protected Connection getConnection() throws SQLException {
        // if connection was not created or connection was closed we create a new one
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        }
        return connection;
    }
}
