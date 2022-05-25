/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

/**
 *
 * @author HP
 */
public class User {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String role;
    protected String mail;
    protected String mdp;
    protected String dateInscription;
    protected int actif;

    
    public User() {
    }

    //Insert patient
    public User(String mail) {
        this.mail = mail;
    }
    
    //Insert patient
    public User(String nom, String prenom, String mail, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
    }
    
    //Select  medecin
    public User(String nom, String prenom, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
    }
    
    public User(int id, String role, String prenom, String nom) {
        this.id = id;
        this.role = role;
        this.prenom = prenom;
        this.nom = nom;
    }
    
//Update patient 
//    public User(int id, String nomComplet, String login, String password, String role) {
//        this.id = id;
//        this.nomComplet = nomComplet;
//        this.login = login;
//        this.password = password;
//        this.role = role;
//    }
    
    // Getters && Setters 
    
    public int getId() {
        return id;
    }
    

    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getMdp() {
        return mdp;
    }
    
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public int getActif() {
        return actif;
    }

    public void setActif(int actif) {
        this.actif = actif;
    }  
}
