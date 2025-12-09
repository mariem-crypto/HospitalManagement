package tn.hopital.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.hopital.model.Patient;
import tn.hopital.service.HopitalService;
import tn.hopital.ui.util.AlertUtil;

import java.time.LocalDate;

/**
 * Contrôleur JavaFX pour l'écran de gestion des patients.
 */
public class PatientController {

    // ---------- Champs du formulaire ----------

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private DatePicker dpDateNaissance;

    @FXML
    private TextField txtAdresse;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TextField txtEmail;   // 👉 champ email

    // ---------- Table des patients ----------

    @FXML
    private TableView<Patient> tablePatients;

    @FXML
    private TableColumn<Patient, Integer> colId;

    @FXML
    private TableColumn<Patient, String> colNom;

    @FXML
    private TableColumn<Patient, String> colPrenom;

    @FXML
    private TableColumn<Patient, LocalDate> colDateNaissance;

    @FXML
    private TableColumn<Patient, String> colAdresse;

    @FXML
    private TableColumn<Patient, String> colTelephone;

    @FXML
    private TableColumn<Patient, String> colEmail;  // 👉 colonne email

    // ---------- (optionnel) liste des emails distincts ----------

    // Ajoute ceci dans le FXML si tu veux l'utiliser :
    // <ListView fx:id="listEmailsPatients" ... />
    @FXML
    private ListView<String> listEmailsPatients;

    // ---------- Service & données ----------

    private final HopitalService service = new HopitalService();
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();

    // ============================================================
    //                      INITIALISATION
    // ============================================================
    @FXML
    private void initialize() {
        // Configuration des colonnes
        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        colNom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));

        colPrenom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));

        colDateNaissance.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateNaissance()));

        colAdresse.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getAdresse()));

        colTelephone.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));

        colEmail.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        // Charger les données initiales
        rafraichirTable();
        rafraichirListeEmails();   // pour la partie Set + Stream

        // Quand on clique sur une ligne → remplir le formulaire
        tablePatients.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> afficherDetailsPatient(newSel)
        );
    }

    // Liste des patients dans la TableView
    private void rafraichirTable() {
        patients.setAll(service.listerPatients());
        tablePatients.setItems(patients);
    }

    // (Optionnel) Rafraîchir la ListView des emails distincts
    private void rafraichirListeEmails() {
        if (listEmailsPatients != null) {
            listEmailsPatients.getItems().setAll(service.listerEmailsPatients());
        }
    }

    private void afficherDetailsPatient(Patient p) {
        if (p != null) {
            txtNom.setText(p.getNom());
            txtPrenom.setText(p.getPrenom());
            dpDateNaissance.setValue(p.getDateNaissance());
            txtAdresse.setText(p.getAdresse());
            txtTelephone.setText(p.getTelephone());
            txtEmail.setText(p.getEmail());
        }
    }

    // ============================================================
    //                      ACTIONS BOUTONS
    // ============================================================

    @FXML
    private void onAjouter() {
        try {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            LocalDate dateNaissance = dpDateNaissance.getValue();
            String adresse = txtAdresse.getText();
            String telephone = txtTelephone.getText();
            String email = txtEmail.getText();

            // --- validations simples ---
            if (nom == null || nom.isBlank() ||
                prenom == null || prenom.isBlank() ||
                dateNaissance == null ||
                adresse == null || adresse.isBlank() ||
                telephone == null || telephone.isBlank() ||
                email == null || email.isBlank()) {

                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            if (!email.contains("@")) {
                throw new IllegalArgumentException("Email invalide (il doit contenir '@').");
            }

            // Utilise le constructeur avec email
            Patient p = new Patient(nom, prenom, dateNaissance, adresse, telephone, email);
            service.ajouterPatient(p);

            rafraichirTable();
            rafraichirListeEmails(); // mise à jour des emails distincts
            clearForm();
            AlertUtil.showInfo("Succès", "Patient ajouté avec succès.");
        } catch (IllegalArgumentException e) {
            AlertUtil.showError("Données invalides", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Erreur", "Erreur lors de l'ajout du patient.");
        }
    }

    @FXML
    private void onModifier() {
        Patient selection = tablePatients.getSelectionModel().getSelectedItem();
        if (selection == null) {
            AlertUtil.showWarning("Aucune sélection", "Veuillez sélectionner un patient dans la table.");
            return;
        }

        try {
            String email = txtEmail.getText();

            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("L'email est obligatoire.");
            }
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Email invalide (il doit contenir '@').");
            }

            selection.setNom(txtNom.getText());
            selection.setPrenom(txtPrenom.getText());
            selection.setDateNaissance(dpDateNaissance.getValue());
            selection.setAdresse(txtAdresse.getText());
            selection.setTelephone(txtTelephone.getText());
            selection.setEmail(email);

            service.modifierPatient(selection);
            rafraichirTable();
            rafraichirListeEmails();
            AlertUtil.showInfo("Succès", "Patient modifié avec succès.");
        } catch (IllegalArgumentException e) {
            AlertUtil.showError("Données invalides", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Erreur", "Erreur lors de la modification du patient.");
        }
    }

    @FXML
    private void onSupprimer() {
        Patient selection = tablePatients.getSelectionModel().getSelectedItem();
        if (selection == null) {
            AlertUtil.showWarning("Aucune sélection", "Veuillez sélectionner un patient à supprimer.");
            return;
        }

        boolean ok = AlertUtil.confirm(
                "Confirmation",
                "Voulez-vous vraiment supprimer ce patient ?"
        );

        if (ok) {
            try {
                service.supprimerPatient(selection.getId());
                rafraichirTable();
                rafraichirListeEmails();
                clearForm();
                AlertUtil.showInfo("Succès", "Patient supprimé.");
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.showError("Erreur", "Erreur lors de la suppression du patient.");
            }
        }
    }

    @FXML
    private void onClear() {
        clearForm();
        tablePatients.getSelectionModel().clearSelection();
    }

    // Nettoyage du formulaire
    private void clearForm() {
        txtNom.clear();
        txtPrenom.clear();
        dpDateNaissance.setValue(null);
        txtAdresse.clear();
        txtTelephone.clear();
        txtEmail.clear();
    }
}

