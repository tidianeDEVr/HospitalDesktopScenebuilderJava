/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.jfoenix.controls.JFXDatePicker;
import dto.RendezvousDTO;
import entities.AntecMedicaux;
import entities.Constantes;
import entities.Patient;
import entities.Rendezvous;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import services.Service;
import utils.ViewService;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MedecinActionsController implements Initializable {
    Service service = new Service();
    private ObservableList<RendezvousDTO> obRendezvous;
    private ObservableList<Patient> obPatient;
    private RendezvousDTO rvSelected;
    private Rendezvous rvEntity;
    private Patient patient;
    private Constantes constante;
    private AntecMedicaux antmed ;

    @FXML
    private Button btnUpdate;
    @FXML
    private JFXDatePicker txtfDatePicker;
    @FXML
    private Text txtfError;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<RendezvousDTO> tblvRendezvous;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcMedecin;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcType;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcDateSouh;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcStatut;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcPrestation;
    @FXML
    private ComboBox<Patient> cboPatient;
    @FXML
    private ComboBox<String> cboTypePrest;
    @FXML
    private ComboBox<String> cboTypeRv;
    @FXML
    private TextField txtfTemp;
    @FXML
    private TextField txtfTension;
    @FXML
    private JFXDatePicker txtfDateFilter;
    @FXML
    private Button btnClear;
    @FXML
    private TextField txtfIDPatient;
    @FXML
    private TextField txtfAntMed;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<RendezvousDTO> rendezvous = service.showRvByMedecin(ConnexionController.getCtrl().getUser().getId());
        loadTableViewRV();
        ViewService.loadComboBoxTypeRv(cboTypeRv);
        ViewService.loadComboBoxTypePrest(cboTypePrest);
        loadComboBoxAllMedecin(cboPatient);
    }    

    @FXML
    private void HandleFilterByDate(ActionEvent event) {
        obRendezvous = FXCollections.observableArrayList(service.showAllRvByDate(txtfDateFilter.getValue()+""));
        tblvRendezvous.getItems().clear();
        tableViewMake();
    }
    private void loadTableViewRV() {
        obRendezvous = FXCollections.observableArrayList(
                service.showRvByMedecin(
                        ConnexionController.getCtrl().getUser().getId()
                ));
        tableViewMake();
    }
    private void tableViewMake(){
        tblcType.setCellValueFactory(new PropertyValueFactory<>("consouprest"));
        tblcMedecin.setCellValueFactory(new PropertyValueFactory<>("patient"));
        tblcDateSouh.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblcStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        tblcPrestation.setCellValueFactory(new PropertyValueFactory<>("typeprest"));
        tblvRendezvous.setItems(obRendezvous);
    }

    @FXML
    private void handleAddRv(ActionEvent event) {
        if(null!=txtfDatePicker.getValue()){
            if("Prestation".equals(cboTypeRv.getSelectionModel().getSelectedItem()) 
                && "".equals(cboTypePrest.getSelectionModel().getSelectedItem()) 
                    ){
                txtfError.setText("Selectionnez une prestation!");
                txtfError.setVisible(true);
            }else if(patient==null){
                txtfError.setText("Selectionnez un patient!");
                txtfError.setVisible(true);
            }else{
                Rendezvous rv = new Rendezvous();
                rv.setIdpatient(patient.getId());
                rv.setIdmedecin(ConnexionController.getCtrl().getUser().getId());
                rv.setConsouprest(cboTypeRv.getSelectionModel().getSelectedItem());
                rv.setTypeprest(cboTypePrest.getSelectionModel().getSelectedItem());
                rv.setDate(txtfDatePicker.getValue() + "");
                rv.setStatut("Valide");
                service.addRendezvous(rv);
                clear();
                alertInformation("Rendez-vous","Rendez-vous ajoute avec succes!");
                tblvRendezvous.getItems().clear();
                loadTableViewRV();
            }
        }else{
            txtfError.setText("Selectionnez une date valide !");
            txtfError.setVisible(true);
        }
    }
    private void alertInformation(String title, String content ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    @FXML
    private void HandleSelectRV(MouseEvent event) {
        rvSelected = tblvRendezvous.getSelectionModel().getSelectedItem();
        btnCancel.setDisable(false);
        rvEntity = service.findRvById(rvSelected.getId());
        if(rvEntity.getIdpatient()!=0){
            patient = service.findPatientById(rvEntity.getIdpatient());
        }
    }
    @FXML
    private void handleCancel(ActionEvent event) {
        
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }
    private void clear() {
        tblvRendezvous.getSelectionModel().clearSelection();
        cboTypeRv.getSelectionModel().selectFirst();
        
        txtfDatePicker.setValue(null);
        txtfDateFilter.setValue(null);
        
        txtfError.setVisible(false);
        btnUpdate.setDisable(true);
        btnCancel.setDisable(true);
        
        rvSelected = null;
        constante = null;
        
        tblvRendezvous.getItems().clear();
        loadTableViewRV();
    }
     public void loadComboBoxAllMedecin(ComboBox<Patient> cbo){
        obPatient = FXCollections.observableArrayList(service.findAllPatient());
        cbo.setItems(obPatient);
    }

    @FXML
    private void HandleSelectPatientCbo(ActionEvent event) {
        patient = cboPatient.getSelectionModel().getSelectedItem();
        txtfIDPatient.setText(patient.getId()+"");
        btnUpdate.setDisable(false);
        obRendezvous = FXCollections.observableArrayList(service.showRvByPatient(patient.getId()));
        tableViewMake();
        
        loadConstante();
        loadAntMed();
    }
    private void loadConstante() {
        constante = service.findConstanteByPatient(patient.getId());
        if (constante == null) {
            txtfTemp.setText("0");
            txtfTension.setText("0");
        } else {
            txtfTemp.setText(Integer.toString(constante.getTemperature()));
            txtfTension.setText(Integer.toString(constante.getTension()));
        }
    }
    private void loadAntMed() {
        antmed = service.findAntMedByPatient(patient.getIdAntecMed());
        if (antmed == null) {
            txtfAntMed.setText("Nulle");
        } else {
            txtfAntMed.setText(antmed.getLibelle());
        }
    }

    @FXML
    private void HandlePrest(ActionEvent event) {
        cboTypePrest.getSelectionModel().selectFirst();
        if ("Prestation".equals(cboTypeRv.getValue())) {
            cboTypePrest.setDisable(false);
        } else {
            cboTypePrest.setDisable(true);
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if(constante!=null){
            // Update
            int result = service.updateConstante(constante.getId(), Integer.parseInt(txtfTemp.getText()), Integer.parseInt(txtfTension.getText()));
            if(result!=0){
                alertInformation("Patient","Modification effectue avec succes!");
            }
        }else{
            // Create
            Constantes c = new Constantes();
            c.setTemperature(Integer.parseInt(txtfTemp.getText()));
            c.setTension(Integer.parseInt(txtfTension.getText()));
            c.setIdpatient(patient.getId());
            int result = service.insertConstante(c);
            if(result!=0){
                    alertInformation("Patient","Constantes ajoutes avec succes!");
            }
        }
        
        if(antmed!=null){
            service.updateAntMed(antmed.getId(), txtfAntMed.getText());
        }else{
            AntecMedicaux antmed = new AntecMedicaux();
            antmed.setLibelle(txtfAntMed.getText());
            service.insertAntMed(antmed);
        }
        clear();
    }
}
