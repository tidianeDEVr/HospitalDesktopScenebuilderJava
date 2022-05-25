/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Medecin;
import entities.Rendezvous;
import java.util.List;
import javafx.scene.control.ComboBox;
import services.Service;

/**
 *
 * @author Cheikh Tidiane Ndiaye
 */
public class ViewService 
{
    Service service = new Service();
    List<Medecin> medecins = service.findAllMedecin();
    
    public static void loadComboBoxTypeRv(ComboBox<String> cboTypeRv) {
        cboTypeRv.getItems().add("Consultation");
        cboTypeRv.getItems().add("Prestation");
        cboTypeRv.getSelectionModel().selectFirst();
    }
       
    public static void loadComboBoxTypePrest(ComboBox<String> cboTypePrest) {
        cboTypePrest.getItems().add("");
        cboTypePrest.getItems().add("Analyse");
        cboTypePrest.getItems().add("Radio");
        cboTypePrest.getItems().add("Biopsie");
        cboTypePrest.getItems().add("IRM");
        cboTypePrest.getItems().add("Radiotherapie");
        cboTypePrest.getItems().add("Vaccination");
    }

    public static void loadComboBoxSecretaire(ComboBox<String> cboAction,
            ComboBox<String> cboStatutRv, ComboBox<String> cboTypeRv) {
        cboAction.getItems().add("");
        cboAction.getItems().add("Valider");
        cboAction.getItems().add("Decliner");
        cboAction.getItems().add("Mettre en attente");
        cboAction.getSelectionModel().selectFirst();
        //
        cboStatutRv.getItems().add("Tous");
        cboStatutRv.getItems().add("En attente");
        cboStatutRv.getItems().add("Valides");
        cboStatutRv.getItems().add("Faites");
        cboStatutRv.getSelectionModel().selectFirst();
        //
        cboTypeRv.getItems().add("");
        cboTypeRv.getItems().add("Consultations");
        cboTypeRv.getItems().add("Prestations");
        cboTypeRv.getSelectionModel().selectFirst();
    }
    
    public static void loadComboBoxRP(ComboBox<String> cboTypePrest) {
        cboTypePrest.getItems().add("");
        cboTypePrest.getItems().add("Faite");
        cboTypePrest.getItems().add("Annulee");
        cboTypePrest.getSelectionModel().selectFirst();
    }
}
