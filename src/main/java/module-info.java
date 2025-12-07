module tn.hospital.hospitalmanagementproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jakarta.mail;

    opens tn.hospital.hospitalmanagementproject to javafx.fxml;
    opens tn.hospital.hospitalmanagementproject.controller to javafx.fxml;

    exports tn.hospital.hospitalmanagementproject;
    exports tn.hospital.hospitalmanagementproject.controller;

}