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

    // Bouton de déconnexion (assure-toi d'avoir un fx:id="btnLogout" dans MainView.fxml)
    @FXML
    private Button btnLogout;

    // Service métier
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
            // stage.initOwner(btnPatients.getScene().getWindow()); // optionnel

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ouverture de la fenêtre Patient.");
        }
    }

    // ➤ Ouvrir la fenêtre de gestion des médecins
    @FXML
    private void onMedecinsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tn/hopital/ui/view/MedecinView.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 600);

            Stage stage = new Stage();
            stage.setTitle("Gestion des médecins");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ouverture de la fenêtre Médecins.");
        }
    }

    // ➤ Ouvrir la fenêtre de gestion des rendez-vous
    @FXML
    private void onRendezVousClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tn/hopital/ui/view/RendezVousView.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 600);

            Stage stage = new Stage();
            stage.setTitle("Gestion des rendez-vous");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            // stage.initOwner(btnRendezVous.getScene().getWindow()); // optionnel

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ouverture de la fenêtre Rendez-vous.");
        }
    }

    // ➤ Déconnexion : fermer la fenêtre principale et revenir à l'écran de login
    @FXML
    private void onLogout() {
        try {
            // Charger l'écran de login
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tn/hopital/ui/view/LoginView.fxml")
            );
            Scene scene = new Scene(loader.load(), 400, 250);

            Stage loginStage = new Stage();
            loginStage.setTitle("Login - Gestion d'Hôpital");
            loginStage.setScene(scene);
            loginStage.setResizable(false);
            loginStage.show();

            // Fermer la fenêtre principale
            Stage currentStage = (Stage) lblTitre.getScene().getWindow();
            currentStage.close();

            System.out.println("Déconnexion effectuée, retour à l'écran de login.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la déconnexion.");
        }
    }

    // ➤ Quitter complètement l'application
    @FXML
    private void onQuitter() {
        System.out.println("Application fermée.");
        javafx.application.Platform.exit();
    }
}
