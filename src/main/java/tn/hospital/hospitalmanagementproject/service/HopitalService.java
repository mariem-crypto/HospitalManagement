package tn.hospital.hospitalmanagementproject.service;

import tn.hospital.hospitalmanagementproject.dao.MedecinDAO;
import tn.hospital.hospitalmanagementproject.dao.PatientDAO;
import tn.hospital.hospitalmanagementproject.dao.RendezVousDAO;
import tn.hospital.hospitalmanagementproject.model.Medecin;
import tn.hospital.hospitalmanagementproject.model.Patient;
import tn.hospital.hospitalmanagementproject.model.RendezVous;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service métier de l'application Hôpital.
 * Gère les opérations sur les patients, médecins et rendez-vous.
 */
public class HopitalService {

    private final PatientDAO patientDAO;
    private final MedecinDAO medecinDAO;
    private final RendezVousDAO rendezVousDAO;

    /**
     * Constructeur : initialise les DAO.
     */
    public HopitalService() {
        this.patientDAO = new PatientDAO();
        this.medecinDAO = new MedecinDAO();
        this.rendezVousDAO = new RendezVousDAO();
    }

    // -------------------- PATIENTS --------------------

    /**
     * Retourne la liste de tous les patients.
     *
     * @return liste des patients
     */
    public List<Patient> listerPatients() {
        try {
            return patientDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des patients", e);
        }
    }

    /**
     * Ajoute un patient à la base.
     *
     * @param p le patient à ajouter
     * @throws IllegalArgumentException si nom ou prénom sont vides
     */
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

    /**
     * Modifie un patient existant.
     *
     * @param p le patient à modifier
     * @throws IllegalArgumentException si le patient n'a pas d'identifiant
     */
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

    /**
     * Supprime un patient par son ID.
     *
     * @param idPatient l'identifiant du patient
     */
    public void supprimerPatient(int idPatient) {
        try {
            patientDAO.delete(idPatient);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du patient", e);
        }
    }

    // -------------------- MEDECINS --------------------

    /**
     * Retourne la liste de tous les médecins.
     *
     * @return liste des médecins
     */
    public List<Medecin> listerMedecins() {
        try {
            return medecinDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des médecins", e);
        }
    }

    /**
     * Ajoute un médecin à la base.
     *
     * @param m le médecin à ajouter
     * @throws IllegalArgumentException si nom ou spécialité sont vides
     */
    public void ajouterMedecin(Medecin m) {
        if (m.getNom() == null || m.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du médecin est obligatoire");
        }
        if (m.getSpecialite() == null || m.getSpecialite().isBlank()) {
            throw new IllegalArgumentException("La spécialité du médecin est obligatoire");
        }

        try {
            medecinDAO.save(m);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du médecin", e);
        }
    }

    /**
     * Modifie un médecin existant.
     *
     * @param m le médecin à modifier
     * @throws IllegalArgumentException si le médecin n'a pas d'identifiant
     */
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

    /**
     * Supprime un médecin par son ID.
     *
     * @param idMedecin l'identifiant du médecin
     */
    public void supprimerMedecin(int idMedecin) {
        try {
            medecinDAO.delete(idMedecin);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du médecin", e);
        }
    }

    // -------------------- RENDEZ-VOUS --------------------

    /**
     * Retourne la liste de tous les rendez-vous.
     *
     * @return liste des rendez-vous
     */
    public List<RendezVous> listerRendezVous() {
        try {
            return rendezVousDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des rendez-vous", e);
        }
    }

    /**
     * Planifie un rendez-vous pour un patient avec un médecin à une date donnée.
     *
     * @param patient le patient concerné
     * @param medecin le médecin concerné
     * @param dateRdv la date et heure du rendez-vous
     * @throws IllegalArgumentException si le patient, le médecin ou la date sont invalides
     */
    public void planifierRendezVous(Patient patient, Medecin medecin, LocalDateTime dateRdv) {
        if (patient == null || patient.getId() <= 0) {
            throw new IllegalArgumentException("Patient invalide pour le rendez-vous");
        }
        if (medecin == null || medecin.getId() <= 0) {
            throw new IllegalArgumentException("Médecin invalide pour le rendez-vous");
        }
        if (dateRdv.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("On ne peut pas planifier un rendez-vous dans le passé");
        }

        RendezVous rdv = new RendezVous(patient, medecin, dateRdv);

        try {
            rendezVousDAO.save(rdv);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du rendez-vous", e);
        }
    }

    /**
     * Supprime un rendez-vous par son ID.
     *
     * @param idRdv l'identifiant du rendez-vous
     */
    public void supprimerRendezVous(int idRdv) {
        try {
            rendezVousDAO.delete(idRdv);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du rendez-vous", e);
        }
    }
}
