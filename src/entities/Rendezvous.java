/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

import java.time.Instant;


/**
 *
 * @author HP
 */
public class Rendezvous{
    private int id;
    private int idpatient;
    private int idmedecin;
    private String consouprest;
    private String typeprest;
    private String date;
    private String statut;

    public Rendezvous(){
    }

    public Rendezvous(int id, int idpatient, int idmedecin
            , String consouprest, String typeprest, String date
            , String statut) {
        this.id = id;
        this.idpatient = idpatient;
        this.idmedecin = idmedecin;
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

    public int getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(int idpatient) {
        this.idpatient = idpatient;
    }

    public int getIdmedecin() {
        return idmedecin;
    }

    public void setIdmedecin(int idmedecin) {
        this.idmedecin = idmedecin;
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

    public void setDate(String datefaite) {
        this.date = datefaite;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
