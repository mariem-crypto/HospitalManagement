package tn.hopital.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.hopital.model.Patient;
import tn.hopital.service.HopitalService;

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
    private TableColumn<Patient, String> colTelephone;

    private final HopitalService service = new HopitalService();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configuration des colonnes
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        colDateNaissance.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateNaissance()));
        colTelephone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));

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

            Patient p = new Patient(nom, prenom, dateNaissance, adresse, telephone);
            service.ajouterPatient(p);

            rafraichirTable();
            clearForm();
            showInfo("Succès", "Patient ajouté avec succès.");
        } catch (IllegalArgumentException e) {
            showError("Données invalides", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur lors de l'ajout du patient.");
        }
    }

    @FXML
    private void onModifier() {
        Patient selection = tablePatients.getSelectionModel().getSelectedItem();
        if (selection == null) {
            showWarning("Aucune sélection", "Veuillez sélectionner un patient dans la table.");
            return;
        }

        try {
            selection.setNom(txtNom.getText());
            selection.setPrenom(txtPrenom.getText());
            selection.setDateNaissance(dpDateNaissance.getValue());
            selection.setAdresse(txtAdresse.getText());
            selection.setTelephone(txtTelephone.getText());

            service.modifierPatient(selection);
            rafraichirTable();
            showInfo("Succès", "Patient modifié avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur lors de la modification du patient.");
        }
    }

    @FXML
    private void onSupprimer() {
        Patient selection = tablePatients.getSelectionModel().getSelectedItem();
        if (selection == null) {
            showWarning("Aucune sélection", "Veuillez sélectionner un patient à supprimer.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Suppression du patient");
        confirm.setContentText("Voulez-vous vraiment supprimer ce patient ?");
        var result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.supprimerPatient(selection.getId());
                rafraichirTable();
                clearForm();
                showInfo("Succès", "Patient supprimé.");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Erreur", "Erreur lors de la suppression du patient.");
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
    }

    /* ==== Petites méthodes pour afficher des alertes ==== */

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
