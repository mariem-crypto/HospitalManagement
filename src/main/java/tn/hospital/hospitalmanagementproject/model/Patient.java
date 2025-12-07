package tn.hospital.hospitalmanagementproject.model;

import java.time.LocalDate;

/**
 * Représente un patient dans le système hospitalier.
 */
public class Patient {

    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private String email;

    /**
     * Constructeur par défaut.
     * Nécessaire pour JavaFX, JDBC et certaines bibliothèques de sérialisation.
     */
    public Patient() {
    }

    /**
     * Constructeur pour créer un patient avant insertion en base (sans ID).
     *
     * @param nom           le nom du patient
     * @param prenom        le prénom du patient
     * @param dateNaissance la date de naissance
     * @param adresse       l'adresse
     * @param telephone     le numéro de téléphone
     */
    public Patient(String nom, String prenom, LocalDate dateNaissance,
                   String adresse, String telephone,String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    /**
     * Constructeur complet pour créer un patient avec ID (lecture depuis la BD).
     *
     * @param id            l'identifiant unique
     * @param nom           le nom
     * @param prenom        le prénom
     * @param dateNaissance la date de naissance
     * @param adresse       l'adresse
     * @param telephone     le numéro de téléphone
     */
    public Patient(int id, String nom, String prenom, LocalDate dateNaissance,
                   String adresse, String telephone,String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    // -------------------- Getters / Setters --------------------

    /**
     * @return l'ID du patient
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'ID du patient
     *
     * @param id l'identifiant à définir
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return le nom du patient
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du patient
     *
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return le prénom du patient
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom du patient
     *
     * @param prenom le prénom à définir
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return la date de naissance du patient
     */
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Définit la date de naissance du patient
     *
     * @param dateNaissance la date à définir
     */
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * @return l'adresse du patient
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse du patient
     *
     * @param adresse l'adresse à définir
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return le numéro de téléphone du patient
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone du patient
     *
     * @param telephone le numéro à définir
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
