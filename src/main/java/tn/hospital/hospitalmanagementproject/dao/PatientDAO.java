package tn.hospital.hospitalmanagementproject.dao;

import tn.hospital.hospitalmanagementproject.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the {@link Patient} entity.
 * <p>
 * Provides CRUD operations to interact with the "patient" table in the database.
 */
public class PatientDAO {

    /**
     * Saves a new {@link Patient} in the database.
     * <p>
     * After insertion, the generated ID is set in the Patient object.
     *
     * @param p the Patient object to save
     * @throws SQLException if a database access error occurs
     */
    public void save(Patient p) throws SQLException {
        String sql = "INSERT INTO patient(nom, prenom, date_naissance, adresse, telephone) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            ps.setString(4, p.getAdresse());
            ps.setString(5, p.getTelephone());

            ps.executeUpdate();

            // Récupérer l'id généré
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Retrieves all Patient records from the database, ordered by name.
     *
     * @return a list of {@link Patient}
     * @throws SQLException if a database access error occurs
     */
    public List<Patient> findAll() throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient ORDER BY nom";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    /**
     * Finds a Patient by its ID.
     *
     * @param id the Patient ID
     * @return the {@link Patient} if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
    public Patient findById(int id) throws SQLException {
        String sql = "SELECT * FROM patient WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }
        }
        return null;
    }

    /**
     * Updates an existing {@link Patient} in the database.
     *
     * @param p the Patient object to update
     * @throws SQLException if a database access error occurs
     */
    public void update(Patient p) throws SQLException {
        String sql = "UPDATE patient SET nom=?, prenom=?, date_naissance=?, adresse=?, telephone=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            ps.setString(4, p.getAdresse());
            ps.setString(5, p.getTelephone());
            ps.setInt(6, p.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Deletes a Patient from the database by ID.
     *
     * @param id the ID of the Patient to delete
     * @throws SQLException if a database access error occurs
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM patient WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Maps a {@link ResultSet} row to a {@link Patient} object.
     *
     * @param rs the ResultSet pointing to the current row
     * @return a {@link Patient} object
     * @throws SQLException if a database access error occurs
     */
    private Patient map(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getDate("date_naissance") != null ? rs.getDate("date_naissance").toLocalDate() : null,
                rs.getString("adresse"),
                rs.getString("telephone")
        );
    }
}
