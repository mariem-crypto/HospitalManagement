package tn.hospital.hospitalmanagementproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.hospital.hospitalmanagementproject.dao.DatabaseInitializer;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe principale de l'application Hôpital.
 * <p>
 * Elle initialise la base de données, charge l'interface graphique
 * et configure la fenêtre principale de manière transparente et déplaçable.
 */
public class MainApplication extends Application {

    // Décalages utilisés pour le drag & drop de la fenêtre
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Point d'entrée JavaFX de l'application.
     *
     * @param primaryStage la fenêtre principale
     * @throws IOException si le fichier FXML n'est pas trouvé ou ne peut être chargé
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Crée les tables dans la base de données si elles n'existent pas
        DatabaseInitializer.createTablesIfNotExist();

        // Charge le fichier FXML de l'interface
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("hospital-view.fxml"))
        );

        // Rend la fenêtre transparente
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        // Permet de déplacer la fenêtre en la faisant glisser avec la souris
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // Crée la scène avec transparence
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        // Affecte la scène à la fenêtre et l'affiche
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Point d'entrée de l'application Java.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}
