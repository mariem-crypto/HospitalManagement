package tn.hospital.hospitalmanagementproject.model;

/**
 * Représente un médecin dans le système hospitalier.
 */
public class Medecin {

    private int id;
    private String nom;
    private String prenom;
    private String specialite;
    private String telephone;
    private String email;

    /**
     * Constructeur par défaut.
     */
    public Medecin() {
    }

    /**
     * Constructeur pour créer un médecin sans ID (utilisé avant insertion en base).
     *
     * @param nom        le nom du médecin
     * @param prenom     le prénom du médecin
     * @param specialite la spécialité du médecin
     * @param telephone  le numéro de téléphone du médecin
     */
    public Medecin(String nom, String prenom, String specialite, String telephone,String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.telephone = telephone;
        this.email = email;
    }

    /**
     * Constructeur complet avec ID.
     *
     * @param id         l'identifiant unique du médecin
     * @param nom        le nom du médecin
     * @param prenom     le prénom du médecin
     * @param specialite la spécialité du médecin
     * @param telephone  le numéro de téléphone du médecin
     */
    public Medecin(int id, String nom, String prenom, String specialite, String telephone,String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.telephone = telephone;
        this.email = email;
    }

    // -------------------- Getters / Setters --------------------

    /**
     * @return l'ID du médecin
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'ID du médecin
     * @param id l'identifiant à définir
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return le nom du médecin
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du médecin
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return le prénom du médecin
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom du médecin
     * @param prenom le prénom à définir
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return la spécialité du médecin
     */
    public String getSpecialite() {
        return specialite;
    }

    /**
     * Définit la spécialité du médecin
     * @param specialite la spécialité à définir
     */
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    /**
     * @return le numéro de téléphone du médecin
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone du médecin
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
        return nom + " " + prenom + " (" + specialite + ")";
    }
}
