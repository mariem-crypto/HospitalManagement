package tn.hospital.hospitalmanagementproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.hospital.hospitalmanagementproject.dao.DBConnection;
import tn.hospital.hospitalmanagementproject.model.Medecin;
import tn.hospital.hospitalmanagementproject.model.Patient;
import tn.hospital.hospitalmanagementproject.model.RendezVous;
import tn.hospital.hospitalmanagementproject.service.HopitalService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Controller for managing the Hospital application UI.
 * <p>
 * Handles CRUD operations for Patients, Medecins, and RendezVous.
 */
public class HospitalController {

    private final HopitalService service = new HopitalService();

    // -------------------- PATIENTS --------------------
    @FXML
    private TableView<Patient> tablePatients;
    @FXML
    private TableColumn<Patient, Integer> colPatientId;
    @FXML
    private TableColumn<Patient, String> colPatientNom;
    @FXML
    private TableColumn<Patient, String> colPatientPrenom;
    @FXML
    private TableColumn<Patient, LocalDate> colPatientDateNaissance;
    @FXML
    private TableColumn<Patient, String> colPatientAdresse;
    @FXML
    private TableColumn<Patient, String> colPatientTelephone;
    @FXML
    private TableColumn<Patient, String> colPatientEmail;

    @FXML
    private TextField txtPatientNom;
    @FXML
    private TextField txtPatientPrenom;
    @FXML
    private DatePicker dpPatientDateNaissance;
    @FXML
    private TextField txtPatientAdresse;
    @FXML
    private TextField txtPatientTelephone;
    @FXML
    private TextField txtPatientEmail;

    private ObservableList<Patient> patientList;

    // -------------------- MEDECINS --------------------
    @FXML
    private TableView<Medecin> tableMedecins;
    @FXML
    private TableColumn<Medecin, Integer> colMedId;
    @FXML
    private TableColumn<Medecin, String> colMedNom;
    @FXML
    private TableColumn<Medecin, String> colMedPrenom;
    @FXML
    private TableColumn<Medecin, String> colMedSpecialite;
    @FXML
    private TableColumn<Medecin, String> colMedTelephone;
    @FXML
    private TableColumn<Medecin, String> colMedEmail;

    @FXML
    private TextField txtMedNom;
    @FXML
    private TextField txtMedPrenom;
    @FXML
    private TextField txtMedSpecialite;
    @FXML
    private TextField txtMedTelephone;

    @FXML
    private TextField txtMedEmail;

    private ObservableList<Medecin> medecinList;

    // -------------------- RENDEZ-VOUS --------------------
    @FXML
    private TableView<RendezVous> tableRdv;
    @FXML
    private TableColumn<RendezVous, Integer> colRdvId;
    @FXML
    private TableColumn<RendezVous, String> colRdvPatient;
    @FXML
    private TableColumn<RendezVous, String> colRdvMedecin;
    @FXML
    private TableColumn<RendezVous, LocalDateTime> colRdvDate;

    @FXML
    private ComboBox<Patient> cbRdvPatient;
    @FXML
    private ComboBox<Medecin> cbRdvMedecin;
    @FXML
    private DatePicker dpRdvDate;
    @FXML
    private Spinner<Integer> spRdvHour;
    @FXML
    private Spinner<Integer> spRdvMinute;

    private ObservableList<RendezVous> rdvList;

    private Connection connection;

    /**
     * Default constructor initializes the database connection.
     *
     * @throws SQLException if a database access error occurs
     */
    public HospitalController() throws SQLException {
        connection = DBConnection.getConnection();
    }

    /**
     * Initializes the controller class after all @FXML fields are injected.
     * Loads Patients, Medecins, and RendezVous into tables and sets up listeners.
     */
    @FXML
    public void initialize() {
        loadPatients();
        loadMedecins();
        loadRendezVous();

        // Sélection Patient listener
        tablePatients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtPatientNom.setText(newSelection.getNom());
                txtPatientPrenom.setText(newSelection.getPrenom());
                dpPatientDateNaissance.setValue(newSelection.getDateNaissance());
                txtPatientAdresse.setText(newSelection.getAdresse());
                txtPatientTelephone.setText(newSelection.getTelephone());
            }
        });

        // Sélection Médecin listener
        tableMedecins.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtMedNom.setText(newSel.getNom());
                txtMedPrenom.setText(newSel.getPrenom());
                txtMedSpecialite.setText(newSel.getSpecialite());
                txtMedTelephone.setText(newSel.getTelephone());
            }
        });

        // Configure spinners for hour and minute
        spRdvHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 9));
        spRdvMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }

    // -------------------- PATIENT CRUD --------------------

    /**
     * Loads all patients from the service into the TableView and ComboBox.
     */
    private void loadPatients() {
        List<Patient> list = service.listerPatients();
        patientList = FXCollections.observableArrayList(list);
        tablePatients.setItems(patientList);

        colPatientId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colPatientNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colPatientPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        colPatientDateNaissance.setCellValueFactory(data -> new javafx.beans.property.ObjectPropertyBase<LocalDate>() {
            { set(data.getValue().getDateNaissance()); }
            @Override public Object getBean() { return data.getValue(); }
            @Override public String getName() { return "dateNaissance"; }
        });
        colPatientAdresse.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAdresse()));
        colPatientTelephone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));

        cbRdvPatient.setItems(patientList);
    }

    /**
     * Adds a new patient based on input fields and updates the TableView.
     */
    @FXML
    private void ajouterPatient() {
        Patient p = new Patient(txtPatientNom.getText(), txtPatientPrenom.getText(),
                dpPatientDateNaissance.getValue(), txtPatientAdresse.getText(), txtPatientTelephone.getText(),txtPatientEmail.getText());
        service.ajouterPatient(p);
        patientList.add(p);
        clearPatientForm();
    }

    /**
     * Modifies the selected patient with updated input fields.
     */
    @FXML
    private void modifierPatient() {
        Patient selected = tablePatients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtPatientNom.getText());
            selected.setPrenom(txtPatientPrenom.getText());
            selected.setDateNaissance(dpPatientDateNaissance.getValue());
            selected.setAdresse(txtPatientAdresse.getText());
            selected.setTelephone(txtPatientTelephone.getText());
            service.modifierPatient(selected);
            tablePatients.refresh();
            clearPatientForm();
        }
    }

    /**
     * Deletes the selected patient from the database and TableView.
     */
    @FXML
    private void supprimerPatient() {
        Patient selected = tablePatients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.supprimerPatient(selected.getId());
            patientList.remove(selected);
            clearPatientForm();
        }
    }

    /**
     * Clears all patient input fields and selection.
     */
    @FXML
    private void clearPatientForm() {
        txtPatientNom.clear();
        txtPatientPrenom.clear();
        dpPatientDateNaissance.setValue(null);
        txtPatientAdresse.clear();
        txtPatientTelephone.clear();
        tablePatients.getSelectionModel().clearSelection();
    }

    // -------------------- MEDECIN CRUD --------------------

    /**
     * Loads all medecins from the service into the TableView and ComboBox.
     */
    private void loadMedecins() {
        List<Medecin> list = service.listerMedecins();
        medecinList = FXCollections.observableArrayList(list);
        tableMedecins.setItems(medecinList);

        colMedId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colMedNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colMedPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        colMedSpecialite.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSpecialite()));
        colMedTelephone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));
        colMedEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        cbRdvMedecin.setItems(medecinList);
    }

    /**
     * Adds a new medecin based on input fields and updates the TableView.
     */
    @FXML
    private void ajouterMedecin() {
        Medecin m = new Medecin(txtMedNom.getText(), txtMedPrenom.getText(), txtMedSpecialite.getText(), txtMedTelephone.getText(),txtMedEmail.getText());
        service.ajouterMedecin(m);
        medecinList.add(m);
        clearMedForm();
    }

    /**
     * Modifies the selected medecin with updated input fields.
     */
    @FXML
    private void modifierMedecin() {
        Medecin selected = tableMedecins.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtMedNom.getText());
            selected.setPrenom(txtMedPrenom.getText());
            selected.setSpecialite(txtMedSpecialite.getText());
            selected.setTelephone(txtMedTelephone.getText());
            service.modifierMedecin(selected);
            tableMedecins.refresh();
            clearMedForm();
        }
    }

    /**
     * Deletes the selected medecin from the database and TableView.
     */
    @FXML
    private void supprimerMedecin() {
        Medecin selected = tableMedecins.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.supprimerMedecin(selected.getId());
            medecinList.remove(selected);
            clearMedForm();
        }
    }

    /**
     * Clears all medecin input fields and selection.
     */
    @FXML
    private void clearMedForm() {
        txtMedNom.clear();
        txtMedPrenom.clear();
        txtMedSpecialite.clear();
        txtMedTelephone.clear();
        tableMedecins.getSelectionModel().clearSelection();
    }

    /**
     * Closes the application window.
     *
     * @param event the ActionEvent triggered by the close button
     */
    @FXML
    private void closeWindow(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // -------------------- RENDEZ-VOUS --------------------

    /**
     * Loads all rendezvous from the service into the TableView.
     */
    private void loadRendezVous() {
        List<RendezVous> list = service.listerRendezVous();
        rdvList = FXCollections.observableArrayList(list);
        tableRdv.setItems(rdvList);

        colRdvId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colRdvPatient.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPatient().toString()));
        colRdvMedecin.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMedecin().toString()));
        colRdvDate.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateRdv()));
    }

    /**
     * Plans a new rendezvous based on selected patient, medecin, date, and time.
     */
    @FXML
    private void planifierRdv() {
        Patient patient = cbRdvPatient.getSelectionModel().getSelectedItem();
        Medecin medecin = cbRdvMedecin.getSelectionModel().getSelectedItem();
        LocalDate date = dpRdvDate.getValue();
        LocalTime time = LocalTime.of(spRdvHour.getValue(), spRdvMinute.getValue());
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        service.planifierRendezVous(patient, medecin, dateTime);
        loadRendezVous();
    }

    /**
     * Deletes the selected rendezvous from the database and TableView.
     */
    @FXML
    private void supprimerRdv() {
        RendezVous selected = tableRdv.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.supprimerRendezVous(selected.getId());
            rdvList.remove(selected);
        }
    }
}
