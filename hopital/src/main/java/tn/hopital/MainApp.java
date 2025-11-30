package tn.hopital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Chargement de la vue principale
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/tn/hopital/ui/view/MainView.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);

        primaryStage.setTitle("Gestion d'Hôpital");
        primaryStage.setScene(scene);

        // Optionnel : si tu veux une icône plus tard
        // primaryStage.getIcons().add(new Image("/tn/hopital/ui/img/hopital.png"));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 