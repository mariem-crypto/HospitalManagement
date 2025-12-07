package tn.hospital.hospitalmanagementproject.dao;

import tn.hospital.hospitalmanagementproject.model.Medecin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the {@link Medecin} entity.
 * <p>
 * Provides CRUD operations to interact with the "medecin" table in the database.
 */
public class MedecinDAO {

    /**
     * Saves a new {@link Medecin} in the database.
     * <p>
     * After insertion, the generated ID is set in the Medecin object.
     *
     * @param m the Medecin object to save
     * @throws SQLException if a database access error occurs
     */
    public void save(Medecin m) throws SQLException {
        String sql = "INSERT INTO medecin(nom, prenom, specialite, telephone) VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.setString(4, m.getTelephone());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                m.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Retrieves all Medecin records from the database, ordered by name.
     *
     * @return a list of {@link Medecin}
     * @throws SQLException if a database access error occurs
     */
    public List<Medecin> findAll() throws SQLException {
        List<Medecin> list = new ArrayList<>();

        String sql = "SELECT * FROM medecin ORDER BY nom";

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
     * Finds a Medecin by its ID.
     *
     * @param id the Medecin ID
     * @return the {@link Medecin} if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
    public Medecin findById(int id) throws SQLException {
        String sql = "SELECT * FROM medecin WHERE id=?";

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
     * Updates an existing {@link Medecin} in the database.
     *
     * @param m the Medecin object to update
     * @throws SQLException if a database access error occurs
     */
    public void update(Medecin m) throws SQLException {
        String sql = "UPDATE medecin SET nom=?, prenom=?, specialite=?, telephone=?,email=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.setString(4, m.getTelephone());
            ps.setInt(5, m.getId());
            ps.setString(6, m.getEmail());

            ps.executeUpdate();
        }
    }

    /**
     * Deletes a Medecin from the database by ID.
     *
     * @param id the ID of the Medecin to delete
     * @throws SQLException if a database access error occurs
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM medecin WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Maps a {@link ResultSet} row to a {@link Medecin} object.
     *
     * @param rs the ResultSet pointing to the current row
     * @return a {@link Medecin} object
     * @throws SQLException if a database access error occurs
     */
    private Medecin map(ResultSet rs) throws SQLException {
        return new Medecin(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("specialite"),
                rs.getString("telephone"),
                rs.getString("email")
        );
    }
}
