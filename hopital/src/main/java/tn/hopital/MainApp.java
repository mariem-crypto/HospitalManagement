package tn.hopital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 👉 Charger LoginView au lieu de MainView :
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/tn/hopital/ui/view/LoginView.fxml")
        );

        // 👉 Taille de l'écran
        Scene scene = new Scene(loader.load(), 800, 600);

        primaryStage.setTitle("Gestion d'Hôpital");
        primaryStage.setScene(scene);

        // 👉 Maximiser la fenêtre automatiquement
        primaryStage.setMaximized(true);

        // 👉 Icône personnalisée
        var iconUrl = getClass().getResource("/tn/hopital/ui/img/hopital.png");
        if (iconUrl != null) {
            Image icon = new Image(iconUrl.toExternalForm());
            primaryStage.getIcons().add(icon);
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 