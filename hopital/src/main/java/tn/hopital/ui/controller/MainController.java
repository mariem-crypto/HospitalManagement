package tn.hopital.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    // Service métier (tu pourras t'en servir si besoin)
    private final HopitalService service = new HopitalService();

    @FXML
    private void initialize() {
        lblTitre.setText("Bienvenue dans l'application de gestion d'hôpital");
        System.out.println("MainController initialisé.");
    }

    // ➤ Ouvrir la fenêtre de gestion des patients
    @FXML
    private void onPatientsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tn/hopital/ui/view/PatientView.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 600);

            Stage stage = new Stage();
            stage.setTitle("Gestion des patients");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            // Optionnel : rattacher à la fenêtre principale
            // stage.initOwner(btnPatients.getScene().getWindow());

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ouverture de la fenêtre Patient.");
        }
    }

    // ➤ (Plus tard) ouvrir la fenêtre de gestion des médecins
    @FXML
    private void onMedecinsClick() {
        System.out.println("Médecins cliqué (à implémenter)");
        // Tu feras pareil que pour PatientView.fxml avec MedecinView.fxml
    }

    // ➤ (Plus tard) ouvrir la fenêtre de gestion des rendez-vous
    @FXML
    private void onRendezVousClick() {
        System.out.println("Rendez-vous cliqué (à implémenter)");
        // Idem : ouvrir RendezVousView.fxml
    }

    // ➤ Quitter l'application (appelé par le MenuItem Quitter)
    @FXML
    private void onQuitter() {
        System.out.println("Application fermée.");
        javafx.application.Platform.exit();
    }
}
