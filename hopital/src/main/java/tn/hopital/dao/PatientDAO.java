package tn.hopital.dao;

import tn.hopital.model.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // ➤ Ajouter un patient
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

            // récupérer l'id généré
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }
        }
    }

    // ➤ Voir tous les patients
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

    // ➤ Trouver un patient par id
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

    // ➤ Modifier un patient
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

    // ➤ Supprimer un patient
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM patient WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ➤ Méthode utilitaire
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
