package tn.hopital.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.hopital.model.Admin;
import tn.hopital.service.HopitalService;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    private final HopitalService service = new HopitalService();

    @FXML
    private void onLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            // Appel de la couche métier : vérifie dans la base avec BCrypt
            Admin admin = service.login(username, password);

            // Si on arrive ici : login OK
            lblMessage.setText("");

            // Charger la fenêtre principale
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tn/hopital/ui/view/MainView.fxml")
            );
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = new Stage();
            stage.setTitle("Gestion d'Hôpital");
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre de login
            Stage currentStage = (Stage) txtUsername.getScene().getWindow();
            currentStage.close();

        } catch (IllegalArgumentException e) {
            // Cas fonctionnel : mauvais login / mot de passe
            lblMessage.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Erreur interne lors de la connexion.");
        }
    }
}
