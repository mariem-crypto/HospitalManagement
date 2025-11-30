package tn.hospital.hospitalmanagementproject.model;

import java.time.LocalDateTime;

/**
 * Représente un rendez-vous entre un patient et un médecin.
 */
public class RendezVous {

    private int id;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateRdv;

    /**
     * Constructeur par défaut.
     * Nécessaire pour JavaFX, JDBC et certaines bibliothèques de sérialisation.
     */
    public RendezVous() {
    }

    /**
     * Constructeur pour créer un rendez-vous avant insertion en base (sans ID).
     *
     * @param patient  le patient concerné
     * @param medecin  le médecin concerné
     * @param dateRdv  la date et l'heure du rendez-vous
     */
    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateRdv) {
        this.patient = patient;
        this.medecin = medecin;
        this.dateRdv = dateRdv;
    }

    /**
     * Constructeur complet pour créer un rendez-vous avec ID (lecture depuis la BD).
     *
     * @param id       l'identifiant unique
     * @param patient  le patient concerné
     * @param medecin  le médecin concerné
     * @param dateRdv  la date et l'heure du rendez-vous
     */
    public RendezVous(int id, Patient patient, Medecin medecin, LocalDateTime dateRdv) {
        this.id = id;
        this.patient = patient;
        this.medecin = medecin;
        this.dateRdv = dateRdv;
    }

    // -------------------- Getters / Setters --------------------

    /**
     * @return l'ID du rendez-vous
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'ID du rendez-vous
     *
     * @param id l'identifiant à définir
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return le patient associé au rendez-vous
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Définit le patient du rendez-vous
     *
     * @param patient le patient à associer
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return le médecin associé au rendez-vous
     */
    public Medecin getMedecin() {
        return medecin;
    }

    /**
     * Définit le médecin du rendez-vous
     *
     * @param medecin le médecin à associer
     */
    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    /**
     * @return la date et l'heure du rendez-vous
     */
    public LocalDateTime getDateRdv() {
        return dateRdv;
    }

    /**
     * Définit la date et l'heure du rendez-vous
     *
     * @param dateRdv la date à définir
     */
    public void setDateRdv(LocalDateTime dateRdv) {
        this.dateRdv = dateRdv;
    }

    /**
     * Retourne une représentation textuelle du rendez-vous.
     *
     * @return patient, médecin et date du rendez-vous
     */
    @Override
    public String toString() {
        return "RDV{" +
                "patient=" + patient +
                ", medecin=" + medecin +
                ", dateRdv=" + dateRdv +
                '}';
    }
}
