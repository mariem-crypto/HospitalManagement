package tn.hopital.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.hopital.model.Medecin;
import tn.hopital.model.Specialite;
import tn.hopital.service.HopitalService;

public class MedecinController {

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    // ComboBox pour l'énumération Specialite
    @FXML
    private ComboBox<Specialite> cbSpecialite;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TableView<Medecin> tableMedecins;

    @FXML
    private TableColumn<Medecin, Integer> colId;

    @FXML
    private TableColumn<Medecin, String> colNom;

    @FXML
    private TableColumn<Medecin, String> colPrenom;

    @FXML
    private TableColumn<Medecin, String> colSpecialite;

    @FXML
    private TableColumn<Medecin, String> colTelephone;

    private final HopitalService service = new HopitalService();
    private ObservableList<Medecin> medecins = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Remplir la ComboBox avec toutes les valeurs de l'enum
        cbSpecialite.getItems().setAll(Specialite.values());

        // Bind des colonnes
        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        colNom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));

        colPrenom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));

        // afficher le texte de l'énumération (toString())
        colSpecialite.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getSpecialite() != null
                                ? data.getValue().getSpecialite().toString()
                                : ""
                ));

        colTelephone.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));

        // Charger les données
        rafraichirTable();

        // Quand on sélectionne un médecin dans la table → remplir le formulaire
        tableMedecins.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> afficherDetailsMedecin(newSel)
        );
    }

    private void rafraichirTable() {
        medecins.setAll(service.listerMedecins());
        tableMedecins.setItems(medecins);
    }

    private void afficherDetailsMedecin(Medecin m) {
        if (m != null) {
            txtNom.setText(m.getNom());
            txtPrenom.setText(m.getPrenom());
            cbSpecialite.setValue(m.getSpecialite());
            txtTelephone.setText(m.getTelephone());
        }
    }

    @FXML
    private void onAjouter() {
        try {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            Specialite specialite = cbSpecialite.getValue();
            String telephone = txtTelephone.getText();

            if (specialite == null) {
                throw new IllegalArgumentException("La spécialité est obligatoire");
            }

            Medecin m = new Medecin(nom, prenom, specialite, telephone);
            service.ajouterMedecin(m);

            rafraichirTable();
            clearForm();
            showInfo("Succès", "Médecin ajouté avec succès.");
        } catch (IllegalArgumentException e) {
            showError("Données invalides", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur lors de l'ajout du médecin.");
        }
    }

    @FXML
    private void onModifier() {
        Medecin selection = tableMedecins.getSelectionModel().getSelectedItem();
        if (selection == null) {
            showWarning("Aucune sélection", "Veuillez sélectionner un médecin dans la table.");
            return;
        }

        try {
            selection.setNom(txtNom.getText());
            selection.setPrenom(txtPrenom.getText());
            selection.setSpecialite(cbSpecialite.getValue());
            selection.setTelephone(txtTelephone.getText());

            service.modifierMedecin(selection);
            rafraichirTable();
            showInfo("Succès", "Médecin modifié avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur lors de la modification du médecin.");
        }
    }

    @FXML
    private void onSupprimer() {
        Medecin selection = tableMedecins.getSelectionModel().getSelectedItem();
        if (selection == null) {
            showWarning("Aucune sélection", "Veuillez sélectionner un médecin à supprimer.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Suppression du médecin");
        confirm.setContentText("Voulez-vous vraiment supprimer ce médecin ?");
        var result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.supprimerMedecin(selection.getId());
                rafraichirTable();
                clearForm();
                showInfo("Succès", "Médecin supprimé.");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Erreur", "Erreur lors de la suppression du médecin.");
            }
        }
    }

    @FXML
    private void onClear() {
        clearForm();
        tableMedecins.getSelectionModel().clearSelection();
    }

    private void clearForm() {
        txtNom.clear();
        txtPrenom.clear();
        cbSpecialite.getSelectionModel().clearSelection();
        txtTelephone.clear();
    }

    /* ==== Méthodes utilitaires pour les alertes ==== */

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
