package tn.hopital.model;

import java.time.LocalDate;

public class Patient {

    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String telephone;
    private String email;  // 👉 Nouveau champ email

    // Constructeur vide (obligatoire pour JavaFX, JDBC, etc.)
    public Patient() {
    }

    // Constructeur sans id (pour insertion)
    public Patient(String nom, String prenom, LocalDate dateNaissance,
                   String adresse, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    // Constructeur complet (lecture depuis BD)
    public Patient(int id, String nom, String prenom, LocalDate dateNaissance,
                   String adresse, String telephone, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters / Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {   // 👉 Getter email
        return email;
    }

    public void setEmail(String email) {  // 👉 Setter email
        this.email = email;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + email + ")";
    }
}
