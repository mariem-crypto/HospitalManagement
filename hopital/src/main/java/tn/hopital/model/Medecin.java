package tn.hopital.model;

public class Medecin {

    private int id;
    private String nom;
    private String prenom;
    private Specialite specialite;
    private String telephone;
    private String email; // 👉 Nouveau champ email

    public Medecin() {}

    // Constructeur sans ID (pour insertion)
    public Medecin(String nom, String prenom, Specialite specialite, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.telephone = telephone;
        this.email = email;
    }

    // Constructeur complet (pour lecture depuis BD)
    public Medecin(int id, String nom, String prenom, Specialite specialite, String telephone, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
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

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() { // 👉 Getter email
        return email;
    }

    public void setEmail(String email) { // 👉 Setter email
        this.email = email;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + specialite + ", " + email + ")";
    }
}
