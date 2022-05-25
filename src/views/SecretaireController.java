/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SecretaireController implements Initializable {
    private static SecretaireController ctrl;
    @FXML
    private Text lblNomComplet;
    @FXML
    private AnchorPane anchorContent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNomComplet.setText(ConnexionController.getCtrl().getUser().getPrenom()
                + " " + ConnexionController.getCtrl().getUser().getNom());
        try {
            loadView("v_secretaire_valid_rv");
            /*initialiser le controller dans la methode imiatialse*/
            ctrl = this;
        } catch (IOException ex) {
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void loadView(String view) throws IOException{
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("/views/"+view+".fxml"));
        anchorContent.getChildren().clear();
        anchorContent.getChildren().add(root);
    }
    
    @FXML
    private void handleLoadViewConnexion(ActionEvent event) {
        this.lblNomComplet.getScene().getWindow().hide();
        AnchorPane root = null;      
        try {
            root = FXMLLoader.load(getClass().getResource("/views/v_connexion.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
