package tn.hospital.hospitalmanagementproject.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Initializes the database by creating required tables if they do not exist.
 * <p>
 * Tables created:
 * <ul>
 *     <li>patient</li>
 *     <li>medecin</li>
 *     <li>rendezvous</li>
 * </ul>
 * Foreign key constraints are applied where necessary.
 */
public class DatabaseInitializer {

    /**
     * Creates the tables patient, medecin, and rendezvous if they do not already exist.
     * <p>
     * The method connects to the database using {@link DBConnection#getConnection()},
     * executes the SQL statements, and ensures that foreign key constraints are applied.
     * If the tables already exist, no changes are made.
     */
    public static void createTablesIfNotExist() {
        String createPatientTable = """
            CREATE TABLE IF NOT EXISTS patient (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nom VARCHAR(100) NOT NULL,
                prenom VARCHAR(100) NOT NULL,
                date_naissance DATE,
                adresse VARCHAR(255),
                telephone VARCHAR(20),
                email VARCHAR(100) NOT NULL
            );
            """;

        String createMedecinTable = """
            CREATE TABLE IF NOT EXISTS medecin (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nom VARCHAR(100) NOT NULL,
                prenom VARCHAR(100) NOT NULL,
                specialite VARCHAR(100) NOT NULL,
                telephone VARCHAR(20),
                email VARCHAR(100) NOT NULL
            );
            """;

        String createRendezVousTable = """
            CREATE TABLE IF NOT EXISTS rendezvous (
                id INT AUTO_INCREMENT PRIMARY KEY,
                patient_id INT NOT NULL,
                medecin_id INT NOT NULL,
                date_rdv DATETIME NOT NULL,
                FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
                FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE CASCADE
            );
            """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createPatientTable);
            stmt.execute(createMedecinTable);
            stmt.execute(createRendezVousTable);

            System.out.println("Tables created or already exist.");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
