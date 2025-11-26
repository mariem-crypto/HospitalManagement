package tn.hopital.dao;

import tn.hopital.model.Medecin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO {

    // ➤ Ajouter médecin
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

    // ➤ Voir tous les médecins
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

    // ➤ Trouver médecin par id
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

    // ➤ Modifier
    public void update(Medecin m) throws SQLException {
        String sql = "UPDATE medecin SET nom=?, prenom=?, specialite=?, telephone=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.setString(4, m.getTelephone());
            ps.setInt(5, m.getId());

            ps.executeUpdate();
        }
    }

    // ➤ Supprimer
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM medecin WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ➤ Méthode utilitaire
    private Medecin map(ResultSet rs) throws SQLException {
        return new Medecin(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("specialite"),
                rs.getString("telephone")
        );
    }
}
