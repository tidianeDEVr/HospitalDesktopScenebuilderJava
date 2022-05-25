/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

/**
 *
 * @author HP
 */
public class Constantes {
    private int id;
    private int idpatient;
    private int temperature;
    private int tension;

    public Constantes() {
    }

    public Constantes(int id) {
        this.id = id;
    }

    public Constantes(int id, int idpatient, int temperature, int tension) {
        this.id = id;
        this.idpatient = idpatient;
        this.temperature = temperature;
        this.tension = tension;
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

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTension() {
        return tension;
    }

    public void setTension(int tension) {
        this.tension = tension;
    }
}
