/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;


/**
 *
 * @author HP
 */
public class Patient extends User {
    private final String ROLE = "ROLE_PATIENT";
    private int idAntecMed;

    @Override
    public String toString() {
        return  nom+" "+ prenom;
    }

    public Patient() {
        this.role = ROLE;
    }

    public Patient(String nom, String prenom,
            String mail, String mdp, int idAntecMed) {
        super(nom, prenom, mail, mdp);
        this.idAntecMed = idAntecMed; 
        this.role = ROLE;
    }    

     public int getIdAntecMed() {
        return idAntecMed;
    }

    public void setIdAntMed(int idAntMed) {
        this.idAntecMed = idAntMed;
    }
}
