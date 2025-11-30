package tn.hospital.hospitalmanagementproject;

import javafx.application.Application;

/**
 * Classe de lancement de l'application Hôpital.
 * <p>
 * Elle délègue simplement le démarrage à la classe MainApplication.
 */
public class Launcher {

    /**
     * Point d'entrée principal de l'application.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }
}
