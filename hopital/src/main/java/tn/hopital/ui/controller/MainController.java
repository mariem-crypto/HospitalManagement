package tn.hopital.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.hopital.service.HopitalService;

public class MainController {

    @FXML
    private Label lblTitre;

    @FXML
    private Button btnPatients;

    @FXML
    private Button btnMedecins;

    @FXML
    private Button btnRendezVous;

    private final HopitalService service = new HopitalService();

    @FXML
    private void initialize() {
        lblTitre.setText("Bienvenue dans l'application de gestion d'hôpital");
    }

    @FXML
    private void onPatientsClick() {
        System.out.println("Patients cliqué");
    }

    @FXML
    private void onMedecinsClick() {
        System.out.println("Médecins cliqué");
    }

    @FXML
    private void onRendezVousClick() {
        System.out.println("Rendez-vous cliqué");
    }

    @FXML
    private void onQuitter() {
        System.out.println("Application fermée.");
        javafx.application.Platform.exit();
    }
}
