/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

/**
 *
 * @author HP
 */
public class AntecMedicaux {
    private int id;
    private String libelle;
    private String description;

    public AntecMedicaux() {
    }

    public AntecMedicaux(String libelle) {
        this.libelle = libelle;
    }

    public AntecMedicaux(int id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return  libelle ;
    }
}
