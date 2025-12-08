package tn.hopital.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import tn.hopital.model.Medecin;
import tn.hopital.model.Specialite;
import tn.hopital.service.HopitalService;
import tn.hopital.ui.util.AlertUtil;

public class MedecinController {

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private ComboBox<Specialite> cbSpecialite;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TextField txtEmail;   // ✅ nouveau champ

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

    @FXML
    private TableColumn<Medecin, String> colEmail;  // ✅ nouvelle colonne

    private final HopitalService service = new HopitalService();
    private final ObservableList<Medecin> medecins = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Remplir la ComboBox avec toutes les valeurs de l'enum
        cbSpecialite.getItems().setAll(Specialite.values());

        // Bind des colonnes
        colId.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()).asObject());

        colNom.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNom()));

        colPrenom.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getPrenom()));

        colSpecialite.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getSpecialite() != null
                                ? data.getValue().getSpecialite().toString()
                                : ""
                ));

        colTelephone.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTelephone()));

        // ✅ nouvelle colonne email
        colEmail.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEmail()));

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
            txtEmail.setText(m.getEmail()); // ✅ remplissage email
        }
    }

    @FXML
    private void onAjouter() {
        try {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            Specialite specialite = cbSpecialite.getValue();
            String telephone = txtTelephone.getText();
            String email = txtEmail.getText();

            // ✅ Quelques validations simples
            if (nom == null || nom.isBlank()
                    || prenom == null || prenom.isBlank()
                    || telephone == null || telephone.isBlank()
                    || email == null || email.isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            if (specialite == null) {
                throw new IllegalArgumentException("La spécialité est obligatoire.");
            }

            // Petite validation très basique d'email
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Email invalide.");
            }

            // ⚠️ Adapte ce constructeur si ta classe Medecin est différente
            Medecin m = new Medecin(nom, prenom, specialite, telephone, email);

            service.ajouterMedecin(m);

            rafraichirTable();
            clearForm();
            AlertUtil.showInfo("Succès", "Médecin ajouté avec succès.");
        } catch (IllegalArgumentException e) {
            AlertUtil.showError("Données invalides", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Erreur", "Erreur lors de l'ajout du médecin.");
        }
    }

    @FXML
    private void onModifier() {
        Medecin selection = tableMedecins.getSelectionModel().getSelectedItem();
        if (selection == null) {
            AlertUtil.showWarning("Aucune sélection", "Veuillez sélectionner un médecin dans la table.");
            return;
        }

        try {
            selection.setNom(txtNom.getText());
            selection.setPrenom(txtPrenom.getText());
            selection.setSpecialite(cbSpecialite.getValue());
            selection.setTelephone(txtTelephone.getText());
            selection.setEmail(txtEmail.getText()); // ✅ mise à jour email

            service.modifierMedecin(selection);
            rafraichirTable();
            AlertUtil.showInfo("Succès", "Médecin modifié avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Erreur", "Erreur lors de la modification du médecin.");
        }
    }

    @FXML
    private void onSupprimer() {
        Medecin selection = tableMedecins.getSelectionModel().getSelectedItem();
        if (selection == null) {
            AlertUtil.showWarning("Aucune sélection", "Veuillez sélectionner un médecin à supprimer.");
            return;
        }

        boolean ok = AlertUtil.confirm(
                "Confirmation",
                "Voulez-vous vraiment supprimer ce médecin ?"
        );

        if (ok) {
            try {
                service.supprimerMedecin(selection.getId());
                rafraichirTable();
                clearForm();
                AlertUtil.showInfo("Succès", "Médecin supprimé.");
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.showError("Erreur", "Erreur lors de la suppression du médecin.");
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
        txtEmail.clear();  // ✅ vider email aussi
    }
}


