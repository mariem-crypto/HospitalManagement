package tn.hopital.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour gérer la connexion unique à la base MySQL.
 */
public class DBConnection {

    // URL de connexion à MySQL (XAMPP)
    private static final String URL =
            "jdbc:mysql://localhost:3306/hopital?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // adapte si tu as un mot de passe

    // Connexion unique (singleton simple)
    private static Connection connection;

    private DBConnection() {
        // constructeur privé : on ne crée pas d'instance
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connexion MySQL établie");
        }
        return connection;
    }
}
