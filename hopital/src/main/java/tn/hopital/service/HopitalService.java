package tn.hopital.service;

import tn.hopital.dao.AdminDAO;
import tn.hopital.dao.MedecinDAO;
import tn.hopital.dao.PatientDAO;
import tn.hopital.dao.RendezVousDAO;
import tn.hopital.model.Admin;
import tn.hopital.model.Medecin;
import tn.hopital.model.Patient;
import tn.hopital.model.RendezVous;
import tn.hopital.util.PasswordUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class HopitalService {

    private final PatientDAO patientDAO;
    private final MedecinDAO medecinDAO;
    private final RendezVousDAO rendezVousDAO;
    private final AdminDAO adminDAO;   // 🔹 nouveau

    public HopitalService() {
        this.patientDAO = new PatientDAO();
        this.medecinDAO = new MedecinDAO();
        this.rendezVousDAO = new RendezVousDAO();
        this.adminDAO = new AdminDAO();   // 🔹 initialisation
    }

    // ================== LOGIN ADMIN ================== //

    /**
     * Vérifie le login d'un administrateur.
     * @param username login saisi
     * @param password mot de passe en clair (saisi dans l'UI)
     * @return l'objet Admin si OK
     * @throws IllegalArgumentException si login/mot de passe incorrect
     */
    public Admin login(String username, String password) {
        try {
            Admin admin = adminDAO.findByUsername(username);
            if (admin == null) {
                throw new IllegalArgumentException("Utilisateur inconnu");
            }

            // Vérification du mot de passe avec BCrypt
            boolean ok = PasswordUtil.checkPassword(password, admin.getPassword());
            if (!ok) {
                throw new IllegalArgumentException("Mot de passe incorrect");
            }

            return admin;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la tentative de connexion", e);
        }
    }

    // ================== PATIENTS ================== //

    public List<Patient> listerPatients() {
        try {
            return patientDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des patients", e);
        }
    }

    public void ajouterPatient(Patient p) {
        if (p.getNom() == null || p.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du patient est obligatoire");
        }
        if (p.getPrenom() == null || p.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom du patient est obligatoire");
        }

        try {
            patientDAO.save(p);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du patient", e);
        }
    }

    public void modifierPatient(Patient p) {
        if (p.getId() <= 0) {
            throw new IllegalArgumentException("Patient sans identifiant");
        }
        try {
            patientDAO.update(p);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du patient", e);
        }
    }

    public void supprimerPatient(int idPatient) {
        try {
            patientDAO.delete(idPatient);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du patient", e);
        }
    }

    // ================== MEDECINS ================== //

    public List<Medecin> listerMedecins() {
        try {
            return medecinDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des médecins", e);
        }
    }

    public void ajouterMedecin(Medecin m) {
        if (m.getNom() == null || m.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du médecin est obligatoire");
        }
        if (m.getSpecialite() == null) {
            throw new IllegalArgumentException("La spécialité du médecin est obligatoire");
        }

        try {
            medecinDAO.save(m);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du médecin", e);
        }
    }

    public void modifierMedecin(Medecin m) {
        if (m.getId() <= 0) {
            throw new IllegalArgumentException("Médecin sans identifiant");
        }
        try {
            medecinDAO.update(m);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du médecin", e);
        }
    }

    public void supprimerMedecin(int idMedecin) {
        try {
            medecinDAO.delete(idMedecin);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du médecin", e);
        }
    }

    // ================== RENDEZ-VOUS ================== //

    public List<RendezVous> listerRendezVous() {
        try {
            return rendezVousDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des rendez-vous", e);
        }
    }

    public void planifierRendezVous(Patient patient, Medecin medecin, LocalDateTime dateRdv) {
        if (patient == null || patient.getId() <= 0) {
            throw new IllegalArgumentException("Patient invalide pour le rendez-vous");
        }
        if (medecin == null || medecin.getId() <= 0) {
            throw new IllegalArgumentException("Médecin invalide pour le rendez-vous");
        }
        if (dateRdv.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("On ne peut pas planifier un rendez-vous dans le passé");
        }

        RendezVous rdv = new RendezVous(patient, medecin, dateRdv);

        try {
            rendezVousDAO.save(rdv);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du rendez-vous", e);
        }
    }

    public void supprimerRendezVous(int idRdv) {
        try {
            rendezVousDAO.delete(idRdv);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du rendez-vous", e);
        }
    }
}

