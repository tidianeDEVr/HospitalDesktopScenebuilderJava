/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.AntecMedicaux;
import entities.Patient;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.Service;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class ConnexionController implements Initializable {
    Patient patient;
    ObservableList<AntecMedicaux> obAntMed;
    @FXML
    private Text txtError;
    @FXML
    private TextField txtlLogin;
    @FXML
    private TextField txtpPassword;
    private final Service service = new Service();
    private static ConnexionController ctrl;
    
    private User user;
    @FXML
    private TextField txtlNom;
    @FXML
    private TextField txtlMail;
    @FXML
    private TextField txtlPrenom;
    @FXML
    private TextField txtlPasswordIns;
    @FXML
    private ComboBox<AntecMedicaux> cboAntMed;
    @FXML
    private Text txtErrorInscription;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtError.setVisible(false);
        txtErrorInscription.setVisible(false);
        ctrl = this;
        loadComboBoxAntMedicaux(cboAntMed);
    }    
    /**
     * Retourne une list d'observable 
     */
    private void loadComboBoxAntMedicaux(ComboBox<AntecMedicaux> cbo)
    {
       /* 1. Permet d'ajouter la liste des antecedents medicaux
        dans le observableArrayList */
      obAntMed = FXCollections.observableArrayList(service.searchAllAntMedicaux());
      
      /* 2. Permet a la liste classe de s'abonner a l'observable*/
      cbo.setItems(obAntMed);
    }

    @FXML
    private void handleClear(ActionEvent event) {
        txtlLogin.clear();
        txtpPassword.clear();
        txtlPasswordIns.clear();
        txtlMail.clear();
        txtlPrenom.clear();
        txtlNom.clear();
        txtError.setVisible(false);
        txtErrorInscription.setVisible(false);
    }
    
    public void loadHome(String home){
        this.txtError.getScene().getWindow().hide();
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/"+home+".fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void HandleConnexion(ActionEvent event) {
        String login = txtlLogin.getText().trim();
        String password = txtpPassword.getText().trim();
        if(login.isEmpty() || password.isEmpty())
        {
          txtError.setText("Login ou mot de passe obligatoire !");
          txtError.setVisible(true);
        }
        else{
            user = service.login(login, password);
            if(user == null)
            {
                txtError.setText("Login ou mot de passe incorrect !");
                txtError.setVisible(true);
            }
            else
            {
                this.txtError.getScene().getWindow().hide();
                switch(ctrl.getUser().getRole())
                {
                    case "ROLE_PATIENT":
                        loadHome("v_home_patient");
                        break;
                    case "ROLE_MEDECIN":
                        loadHome("v_home_medecin");
                        break;
                    case "ROLE_SECRETAIRE":
                        loadHome("v_home_secretaire");
                        break;
                    default:
                        loadHome("v_home_respon");
                }
            }
        }
    }
    
    
    @FXML
    private void HandleRegister(ActionEvent event) {
        Patient patient = new Patient();
        AntecMedicaux am = cboAntMed.getSelectionModel().getSelectedItem();
        // On recupere les donnees pour la connexion
        String nom = txtlNom.getText().trim();
        String prenom = txtlPrenom.getText().trim();
        String passwordIns = txtlPasswordIns.getText().trim();
        String mail = txtlMail.getText().trim();
        int idAntMed = 0;
        if(nom.isEmpty() || prenom.isEmpty() ||
                passwordIns.isEmpty() || mail.isEmpty()){
            txtErrorInscription.setText("Tous les champs sont obligatoirs !");
            txtErrorInscription.setVisible(true);
        }else{
            // On cree une instance de patient
            if(cboAntMed.getValue()==null){
                patient = new Patient(nom, prenom, mail, passwordIns, 0);
            }else{
                idAntMed = am.getId();
                patient = new Patient(nom, prenom, mail, passwordIns, idAntMed);
            }
            // On ajoute l'instance dans la bd
            service.addPatient(patient);
            // On charge la vue home patient
            this.txtError.getScene().getWindow().hide();
            loadHome("v_home_patient");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscription");
            alert.setContentText("Inscription reussie");
            alert.show();
        }
    }
    
    public static ConnexionController getCtrl() {
        return ctrl;
    }

    public User getUser() {
        return user;
    }
}
