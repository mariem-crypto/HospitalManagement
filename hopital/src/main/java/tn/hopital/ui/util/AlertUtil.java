package tn.hopital.ui.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Optional;

public class AlertUtil {

    // Icône commune à toutes les alertes
    private static final Image ALERT_ICON;

    static {
        Image img = null;
        try (InputStream is = AlertUtil.class.getResourceAsStream("/tn/hopital/ui/img/hopital.png")) {
            if (is != null) {
                img = new Image(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ALERT_ICON = img;
    }

    // Méthode centrale pour appliquer style + icône
    private static void stylize(Alert alert) {
        DialogPane pane = alert.getDialogPane();

        // appliquer une classe CSS personnalisée
        pane.getStyleClass().add("custom-alert");

        // charger la feuille de style CSS (on la crée juste après)
        try {
            String css = AlertUtil.class
                    .getResource("/tn/hopital/ui/css/dialogs.css")
                    .toExternalForm();
            pane.getStylesheets().add(css);
        } catch (Exception e) {
            System.out.println("CSS dialogs.css non trouvé (ce n'est pas bloquant).");
        }

        // icône dans le contenu de l'alerte
        if (ALERT_ICON != null) {
            ImageView iv = new ImageView(ALERT_ICON);
            iv.setFitWidth(32);
            iv.setFitHeight(32);
            alert.setGraphic(iv);

            // icône dans la fenêtre de l'alerte
            Platform.runLater(() -> {
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.getIcons().add(ALERT_ICON);
            });
        }
    }

    // 🔵 Information
    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(message);
        stylize(alert);
        alert.showAndWait();
    }

    // 🟡 Avertissement
    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(title);
        alert.setContentText(message);
        stylize(alert);
        alert.showAndWait();
    }

    // 🔴 Erreur
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(message);
        stylize(alert);
        alert.showAndWait();
    }

    // 🟣 Confirmation
    public static boolean confirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(title);
        alert.setContentText(message);
        stylize(alert);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
