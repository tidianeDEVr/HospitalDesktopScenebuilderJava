/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dto;

import java.time.Instant;
import java.util.Date;


/**
 *
 * @author HP
 */
public class RendezvousDTO{
    private int id;
    private String medecin;
    private int idpatient;
    private String patient;

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
    public int getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(int idpatient) {
        this.idpatient = idpatient;
    }

    
    private String consouprest;
    private String typeprest;
    private String date;
    private String statut;

    public RendezvousDTO(){
    }

    public RendezvousDTO(int id, String medecin
            , String consouprest, String typeprest
            , String date, String statut) {
        this.id = id;
        this.medecin = medecin;
        this.consouprest = consouprest;
        this.typeprest = typeprest;
        this.date = date;
        this.statut = statut;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }

    public String getConsouprest() {
        return consouprest;
    }

    public void setConsouprest(String consouprest) {
        this.consouprest = consouprest;
    }

    public String getTypeprest() {
        return typeprest;
    }

    public void setTypeprest(String typeprest) {
        this.typeprest = typeprest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
