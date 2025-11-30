package tn.hopital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chemin ABSOLU vers le FXML dans src/main/resources
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/tn/hopital/ui/view/MainView.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);
        primaryStage.setTitle("Hospital Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 