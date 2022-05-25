/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.jfoenix.controls.JFXDatePicker;
import dto.RendezvousDTO;
import entities.Medecin;
import entities.Patient;
import entities.Rendezvous;
import java.net.URL;
import java.time.LocalDate;
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
 * @author HP
 */
public class RpListCancelController implements Initializable {
    Service service = new Service();
    private ObservableList<RendezvousDTO> obPrestations;
    private RendezvousDTO rvSelected;
    private Rendezvous rv;

    @FXML
    private TextField txtfMedecin;
    @FXML
    private TextField txtfNomPrenom;
    @FXML
    private Button btnUpdate;
    @FXML
    private JFXDatePicker txtfDatePicker;
    @FXML
    private ComboBox<String> cboAction;
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
    private JFXDatePicker txtfDateFilter;
    @FXML
    private ComboBox<String> cboFiltreStatutPrest;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTableViewRV();
        ViewService.loadComboBoxRP(cboAction);
        ViewService.loadComboBoxRP(cboFiltreStatutPrest);
    }    

    @FXML
    private void handleUpdatePrest(ActionEvent event) {
         if(!"".equals(cboAction.getSelectionModel().getSelectedItem())){
            rv.setDate(txtfDatePicker.getValue()+"");
            rv.setId(rvSelected.getId());
            switch(cboAction.getSelectionModel().getSelectedItem())
            {
                case "Annulee":
                    rv.setStatut("Declinee");
                    txtfError.setVisible(false);
                    update();
                    clear();
                    break;
                case "Faite": 
                    rv.setStatut("Faite");
                    txtfError.setVisible(false);
                    update();
                    clear();
                    break;
                default:
                    txtfError.setText("Selectionnez un action!");
                    txtfError.setVisible(true);
            }
        }else{
            txtfError.setText("Veuillez selectionner un action!");
            txtfError.setVisible(true);
        }
    }
    private void update(){
        if(service.updateRv(rv)){
            alertInformation("Alert", "Modification effectue avec succes!");
            tblvRendezvous.getItems().clear();
            loadTableViewRV();
        }
    }
    private void alertInformation(String title, String content ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
        obPrestations = FXCollections.observableArrayList(service.showAllPrest());
        tblvRendezvous.getItems().clear();
        tableViewMake();
    }
    private void clear() {
        rvSelected = null;
        txtfError.setVisible(false);
        txtfNomPrenom.setText("");
        txtfMedecin.setText("");
        cboAction.getSelectionModel().selectFirst();
        cboFiltreStatutPrest.getSelectionModel().selectFirst();
        txtfDatePicker.setValue(null);
        txtfDateFilter.setValue(null);
        btnUpdate.setDisable(true);
        tblvRendezvous.getSelectionModel().clearSelection();
        
    }

    @FXML
    private void HandleSelectPrest(MouseEvent event) {
        rvSelected = tblvRendezvous.getSelectionModel().getSelectedItem();
        btnUpdate.setDisable(false);
        txtfDatePicker.setValue(LocalDate.parse(rvSelected.getDate()));
        rv = service.findRvById(rvSelected.getId());
        if(rv.getIdpatient()!=0){
            Patient patient = service.findPatientById(rv.getIdpatient());
            txtfNomPrenom.setText(patient.getPrenom()+" "+patient.getNom());
        }
        if(!"Indéfini".equals(rvSelected.getMedecin())){
            Medecin medecin = service.findMedecinById(rv.getIdmedecin());
            txtfMedecin.setText("Dr. "+medecin.getNom());
        }else{
            txtfMedecin.setText("Indéfini");
        }
    }
    private void loadTableViewRV() {
        obPrestations = FXCollections.
                observableArrayList(service.showAllPrest());
        tableViewMake();
    }
    private void tableViewMake(){
        tblcType.setCellValueFactory(new PropertyValueFactory<>("consouprest"));
        tblcMedecin.setCellValueFactory(new PropertyValueFactory<>("medecin"));
        tblcDateSouh.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblcStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        tblcPrestation.setCellValueFactory(new PropertyValueFactory<>("typeprest"));
        tblvRendezvous.setItems(obPrestations);
    }

    @FXML
    private void HandleFilterByDate(ActionEvent event) {
        if(!"".equals(cboFiltreStatutPrest.getSelectionModel().getSelectedItem()) &&
               txtfDateFilter.getValue()!=null){
            obPrestations = FXCollections.observableArrayList(service.showAllPrestByDateAndStatut(cboFiltreStatutPrest.getSelectionModel().getSelectedItem(),
                    txtfDateFilter.getValue()+""));
            tblvRendezvous.getItems().clear();
            tableViewMake();
        }else if("".equals(cboFiltreStatutPrest.getSelectionModel().getSelectedItem()) &&
            txtfDateFilter.getValue()!=null){
            obPrestations = FXCollections.observableArrayList(service.showAllPrestByDate(txtfDateFilter.getValue()+""));
            tblvRendezvous.getItems().clear();
            tableViewMake();
        }else if(!"".equals(cboFiltreStatutPrest.getSelectionModel().getSelectedItem()) &&
            txtfDateFilter.getValue()==null){
            obPrestations = FXCollections.observableArrayList(service.showAllPrestByStatut(cboFiltreStatutPrest.getSelectionModel().getSelectedItem()));
            tblvRendezvous.getItems().clear();
            tableViewMake();
        }else{
            txtfError.setText("Selectionnez au moins un critere de filtre!");
            txtfError.setVisible(true);
        }
    }
}
