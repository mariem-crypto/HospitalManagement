package tn.hopital.model;

import java.time.LocalDateTime;

public class RendezVous {

    private int id;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateRdv;

    public RendezVous() {
    }

    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateRdv) {
        this.patient = patient;
        this.medecin = medecin;
        this.dateRdv = dateRdv;
    }

    public RendezVous(int id, Patient patient, Medecin medecin, LocalDateTime dateRdv) {
        this.id = id;
        this.patient = patient;
        this.medecin = medecin;
        this.dateRdv = dateRdv;
    }

    // Getters / Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public LocalDateTime getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(LocalDateTime dateRdv) {
        this.dateRdv = dateRdv;
    }

    @Override
    public String toString() {
        return "RDV{" +
                "patient=" + patient +
                ", medecin=" + medecin +
                ", dateRdv=" + dateRdv +
                '}';
    }
}
