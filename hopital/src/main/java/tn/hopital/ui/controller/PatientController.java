package tn.hopital.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.hopital.model.Patient;
import tn.hopital.service.HopitalService;
import tn.hopital.ui.util.AlertUtil;

import java.time.LocalDate;

public class PatientController {

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
    private TextField txtEmail;   // 👉 nouveau champ

    @FXML
    private TableView<Patient> tablePatients;

    @FXML
    private TableColumn<Patient, Integer> colId;

    @FXML
    private TableColumn<Patient, String> colNom;

    @FXML
    private TableColumn<Patient, String> colPrenom;

    @FXML
    private TableColumn<Patient, String> colAdresse;

    @FXML
    private TableColumn<Patient, LocalDate> colDateNaissance;

    @FXML
    private TableColumn<Patient, String> colTelephone;

    @FXML
    private TableColumn<Patient, String> colEmail;  // 👉 nouvelle colonne

    private final HopitalService service = new HopitalService();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();

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
        colTelephone.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));
        colAdresse.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getAdresse()));
        colEmail.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        // Charger les données
        rafraichirTable();

        // Quand on clique sur une ligne → remplir le formulaire
        tablePatients.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> afficherDetailsPatient(newSel)
        );
    }

    private void rafraichirTable() {
        patients.setAll(service.listerPatients());
        tablePatients.setItems(patients);
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

    @FXML
    private void onAjouter() {
        try {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            LocalDate dateNaissance = dpDateNaissance.getValue();
            String adresse = txtAdresse.getText();
            String telephone = txtTelephone.getText();
            String email = txtEmail.getText();

            // Ici j’utilise le constructeur avec email
            Patient p = new Patient(nom, prenom, dateNaissance, adresse, telephone, email);
            service.ajouterPatient(p);

            rafraichirTable();
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
            selection.setNom(txtNom.getText());
            selection.setPrenom(txtPrenom.getText());
            selection.setDateNaissance(dpDateNaissance.getValue());
            selection.setAdresse(txtAdresse.getText());
            selection.setTelephone(txtTelephone.getText());
            selection.setEmail(txtEmail.getText());

            service.modifierPatient(selection);
            rafraichirTable();
            AlertUtil.showInfo("Succès", "Patient modifié avec succès.");
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

    private void clearForm() {
        txtNom.clear();
        txtPrenom.clear();
        dpDateNaissance.setValue(null);
        txtAdresse.clear();
        txtTelephone.clear();
        txtEmail.clear();
    }
}
