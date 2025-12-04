package tn.hopital.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.hopital.model.Medecin;
import tn.hopital.model.Patient;
import tn.hopital.model.RendezVous;
import tn.hopital.service.HopitalService;
import tn.hopital.ui.util.AlertUtil;   

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RendezVousController {

    @FXML
    private ComboBox<Patient> cbPatient;

    @FXML
    private ComboBox<Medecin> cbMedecin;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField txtHeure;

    @FXML
    private TableView<RendezVous> tableRdv;

    @FXML
    private TableColumn<RendezVous, Integer> colId;

    @FXML
    private TableColumn<RendezVous, String> colPatient;

    @FXML
    private TableColumn<RendezVous, String> colMedecin;

    @FXML
    private TableColumn<RendezVous, String> colDateHeure;

    private final HopitalService service = new HopitalService();
    private final ObservableList<RendezVous> rdvList = FXCollections.observableArrayList();

    // Pour afficher une jolie date/heure dans la table
    private static final DateTimeFormatter DATE_HEURE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    private void initialize() {
        // Charger patients & médecins dans les ComboBox
        cbPatient.setItems(FXCollections.observableArrayList(service.listerPatients()));
        cbMedecin.setItems(FXCollections.observableArrayList(service.listerMedecins()));

        cbPatient.setPromptText("Choisir un patient");
        cbMedecin.setPromptText("Choisir un médecin");

        // Colonnes du tableau
        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        colPatient.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPatient().getNom() + " " +
                                data.getValue().getPatient().getPrenom()
                ));

        colMedecin.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getMedecin().getNom() + " " +
                                data.getValue().getMedecin().getPrenom()
                ));

        colDateHeure.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDateRdv().format(DATE_HEURE_FORMATTER)
                ));

        // Charger les RDV existants
        rafraichirTable();

        // Quand on sélectionne un rdv dans la table, remplir le formulaire
        tableRdv.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> afficherDetailsRdv(newSel)
        );
    }

    private void rafraichirTable() {
        rdvList.setAll(service.listerRendezVous());
        tableRdv.setItems(rdvList);
    }

    private void afficherDetailsRdv(RendezVous rdv) {
        if (rdv != null) {
            cbPatient.setValue(rdv.getPatient());
            cbMedecin.setValue(rdv.getMedecin());
            LocalDateTime dt = rdv.getDateRdv();
            dpDate.setValue(dt.toLocalDate());
            txtHeure.setText(dt.toLocalTime().toString()); // HH:mm:ss mais c’est ok
        }
    }

    @FXML
    private void onAjouter() {
        try {
            Patient patient = cbPatient.getValue();
            Medecin medecin = cbMedecin.getValue();
            LocalDate date = dpDate.getValue();
            String heureStr = txtHeure.getText();

            if (patient == null) {
                throw new IllegalArgumentException("Veuillez choisir un patient.");
            }
            if (medecin == null) {
                throw new IllegalArgumentException("Veuillez choisir un médecin.");
            }
            if (date == null) {
                throw new IllegalArgumentException("Veuillez choisir une date.");
            }
            if (heureStr == null || heureStr.isBlank()) {
                throw new IllegalArgumentException("Veuillez saisir une heure (ex: 14:30).");
            }

            LocalTime heure;
            try {
                // Accepte HH:mm ou HH:mm:ss
                heure = LocalTime.parse(heureStr);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Format d'heure invalide. Utilisez HH:mm (ex: 09:15).");
            }

            LocalDateTime dateRdv = LocalDateTime.of(date, heure);

            service.planifierRendezVous(patient, medecin, dateRdv);
            rafraichirTable();
            clearForm();
            AlertUtil.showInfo("Succès", "Rendez-vous ajouté avec succès.");

        } catch (IllegalArgumentException e) {
            AlertUtil.showError("Données invalides", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Erreur", "Erreur lors de l'ajout du rendez-vous.");
        }
    }

    @FXML
    private void onSupprimer() {
        RendezVous selection = tableRdv.getSelectionModel().getSelectedItem();
        if (selection == null) {
            AlertUtil.showWarning("Aucune sélection", "Veuillez sélectionner un rendez-vous à supprimer.");
            return;
        }

        boolean ok = AlertUtil.confirm(
                "Confirmation",
                "Voulez-vous vraiment supprimer ce rendez-vous ?"
        );

        if (ok) {
            try {
                service.supprimerRendezVous(selection.getId());
                rafraichirTable();
                clearForm();
                AlertUtil.showInfo("Succès", "Rendez-vous supprimé.");
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.showError("Erreur", "Erreur lors de la suppression du rendez-vous.");
            }
        }
    }

    @FXML
    private void onClear() {
        clearForm();
        tableRdv.getSelectionModel().clearSelection();
    }

    private void clearForm() {
        cbPatient.getSelectionModel().clearSelection();
        cbMedecin.getSelectionModel().clearSelection();
        dpDate.setValue(null);
        txtHeure.clear();
    }
}
