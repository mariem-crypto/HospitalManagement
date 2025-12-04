package tn.hopital.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavBarController {

    private static final Logger LOGGER = Logger.getLogger(NavBarController.class.getName());

    // Boutons injectés depuis NavBar.fxml
    @FXML
    private Button btnPatients;

    @FXML
    private Button btnMedecins;

    @FXML
    private Button btnRdv;

    @FXML
    private Button btnLogout;

    // 👉 Nouveau : bouton ChatBot
    @FXML
    private Button btnChatBot;

    // ===== Utilitaire : récupérer la fenêtre courante =====
    private Stage getStage() {
        if (btnPatients != null && btnPatients.getScene() != null) {
            return (Stage) btnPatients.getScene().getWindow();
        }
        throw new IllegalStateException("La navbar n'est pas encore attachée à une fenêtre.");
    }

    // ================== NAVIGATION GÉNÉRIQUE ==================

    private void navigateTo(String fxmlPath, String title, Button activeButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = getStage();
            Scene scene = stage.getScene();

            if (scene == null) {
                // Premier affichage : créer la scène
                scene = new Scene(root, 1100, 700);
                stage.setScene(scene);
            } else {
                // On conserve la même scène, on change juste le root
                scene.setRoot(root);
            }

            stage.setTitle(title);

            // Mettre à jour le bouton actif
            updateActiveButton(activeButton);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la vue : " + fxmlPath, e);
            showError("Une erreur est survenue lors de l'ouverture de la page.",
                    "Détails : " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur inattendue lors de la navigation.", e);
            showError("Une erreur inattendue est survenue.",
                    "Détails : " + e.getMessage());
        }
    }

    private void updateActiveButton(Button activeButton) {
        // On enlève le style "actif" de tous les boutons
        if (btnPatients != null) {
            btnPatients.getStyleClass().remove("nav-btn-active");
        }
        if (btnMedecins != null) {
            btnMedecins.getStyleClass().remove("nav-btn-active");
        }
        if (btnRdv != null) {
            btnRdv.getStyleClass().remove("nav-btn-active");
        }
        if (btnChatBot != null) {
            btnChatBot.getStyleClass().remove("nav-btn-active");
        }

        // Puis on l'ajoute au bouton actuel
        if (activeButton != null && !activeButton.getStyleClass().contains("nav-btn-active")) {
            activeButton.getStyleClass().add("nav-btn-active");
        }
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ================== HANDLERS FXML ==================

    @FXML
    private void onPatients() {
        navigateTo("/tn/hopital/ui/view/PatientView.fxml",
                "Gestion des patients",
                btnPatients);
    }

    @FXML
    private void onMedecins() {
        navigateTo("/tn/hopital/ui/view/MedecinView.fxml",
                "Gestion des médecins",
                btnMedecins);
    }

    @FXML
    private void onRdv() {
        navigateTo("/tn/hopital/ui/view/RendezVousView.fxml",
                "Gestion des rendez-vous",
                btnRdv);
    }

    // 👉 Nouveau : handler pour le bouton ChatBot
    @FXML
    private void onChatBot() {
        navigateTo("/tn/hopital/ui/view/ChatBotView.fxml",
                "Assistant IA - ChatBot",
                btnChatBot);
    }

    @FXML
    private void onLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tn/hopital/ui/view/LoginView.fxml")
            );
            Parent root = loader.load();

            Stage stage = getStage();
            Scene scene = stage.getScene();

            if (scene == null) {
                scene = new Scene(root, 420, 260);
                stage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            stage.setTitle("Login - Gestion d'hôpital");

            // On désactive le style "actif"
            updateActiveButton(null);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la vue Login.", e);
            showError("Impossible d'ouvrir l'écran de login.",
                    "Détails : " + e.getMessage());
        }
    }
}

