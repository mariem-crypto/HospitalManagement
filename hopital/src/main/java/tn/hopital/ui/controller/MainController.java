package tn.hopital.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    private TabPane tabPane; // si tu as un TabPane dans MainView.fxml (fx:id="tabPane")

    @FXML
    public void initialize() {
        System.out.println("MainController initialisé");
    }
}
