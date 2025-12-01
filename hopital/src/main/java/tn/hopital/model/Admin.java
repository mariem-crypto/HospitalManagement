package tn.hopital.model;

public class Admin {

    private int id;
    private String username;
    // ⚠ ici on stocke le mot de passe HASHÉ, pas en clair
    private String password;

    public Admin() {
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters / Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Renvoie le mot de passe HASHÉ (BCrypt),
     * pas le mot de passe en clair.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Doit recevoir un mot de passe DÉJÀ hashé
     * (par PasswordUtil.hashPassword()).
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
