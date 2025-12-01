package tn.hopital.dao;

import tn.hopital.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {

    //  Ajouter RDV
    public void save(RendezVous rdv) throws SQLException {
        String sql = "INSERT INTO rendezvous(patient_id, medecin_id, date_rdv) VALUES (?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, rdv.getPatient().getId());
            ps.setInt(2, rdv.getMedecin().getId());
            ps.setTimestamp(3, Timestamp.valueOf(rdv.getDateRdv()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rdv.setId(rs.getInt(1));
            }
        }
    }

    //  Voir tous les RDV (JOIN patient + medecin)
    public List<RendezVous> findAll() throws SQLException {
        List<RendezVous> list = new ArrayList<>();

        String sql = """
                SELECT r.id, r.date_rdv,
                       p.id AS p_id, p.nom AS p_nom, p.prenom AS p_prenom,
                       m.id AS m_id, m.nom AS m_nom, m.prenom AS m_prenom, m.specialite
                FROM rendezvous r
                JOIN patient p ON r.patient_id = p.id
                JOIN medecin m ON r.medecin_id = m.id
                ORDER BY date_rdv
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    //  Supprimer RDV
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM rendezvous WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //  Mapping ligne → RendezVous
    private RendezVous map(ResultSet rs) throws SQLException {

        // Patient
        Patient p = new Patient();
        p.setId(rs.getInt("p_id"));
        p.setNom(rs.getString("p_nom"));
        p.setPrenom(rs.getString("p_prenom"));

        // Medecin
        Medecin m = new Medecin();
        m.setId(rs.getInt("m_id"));
        m.setNom(rs.getString("m_nom"));
        m.setPrenom(rs.getString("m_prenom"));
        // 🔴 AVANT: m.setSpecialite(rs.getString("specialite"));
        // ✅ MAINTENANT: conversion String -> Enum
        m.setSpecialite(Specialite.valueOf(rs.getString("specialite")));

        // RDV
        LocalDateTime date = rs.getTimestamp("date_rdv").toLocalDateTime();

        return new RendezVous(
                rs.getInt("id"),
                p, m, date
        );
    }
}

