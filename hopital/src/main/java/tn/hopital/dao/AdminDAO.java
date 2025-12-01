package tn.hopital.dao;

import tn.hopital.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    /**
     * Récupérer un admin par username.
     * Sert pour le login : on récupère le hash, puis on compare avec BCrypt.
     */
    public Admin findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password") // hash stocké en base
                );
            }
        }
        return null; // pas trouvé
    }

    /**
     * Enregistrer un nouvel admin.
     * ⚠ ATTENTION : le mot de passe doit être DÉJÀ hashé avant l'appel.
     */
    public void save(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin(username, password) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword()); // hash BCrypt
            ps.executeUpdate();
        }
    }
}
