package tn.hospital.hospitalmanagementproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage a single (singleton) connection to the MySQL database.
 * <p>
 * Uses a simple lazy-loaded singleton pattern to ensure that only one connection
 * is established and reused throughout the application.
 */
public class DBConnection {

    /** MySQL connection URL (XAMPP setup example) */
    private static final String URL =
            "jdbc:mysql://localhost:3306/hopital?useSSL=false&serverTimezone=UTC";

    /** Database username */
    private static final String USER = "root";

    /** Database password (empty by default, adapt if necessary) */
    private static final String PASSWORD = "";

    /** Single instance of the connection */
    private static Connection connection;

    /**
     * Private constructor to prevent instantiation.
     */
    private DBConnection() {
        // No instance allowed
    }

    /**
     * Returns the singleton {@link Connection} to the database.
     * <p>
     * If the connection does not exist or is closed, a new connection is created.
     *
     * @return the {@link Connection} object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connexion MySQL établie");
        }
        return connection;
    }
}
