/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;


/**
 *
 * @author HP
 */
public class Medecin extends User {
    private final String ROLE = "ROLE_MEDECIN";

    public Medecin() {
        this.role = ROLE;
    }

    public Medecin(String nom, String prenom,
            String mail, String mdp) {
        super(nom, prenom, mail, mdp); 
        this.role = ROLE;
    }
    
    public Medecin(String nom, String prenom, int id) {
        super(nom, prenom, id);
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return "Dr "+this.prenom+" "+this.nom;
    }
}
