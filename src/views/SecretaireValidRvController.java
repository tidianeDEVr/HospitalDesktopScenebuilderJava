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
import static utils.ViewService.loadComboBoxSecretaire;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SecretaireValidRvController implements Initializable {
    Service service = new Service();
    private ObservableList<RendezvousDTO> obRendezvous;
    private ObservableList<Medecin> obMedecins;
    private RendezvousDTO rvSelected;
    private Rendezvous rvEntity;

    @FXML
    private TextField txtfNomPrenom;
    @FXML
    private JFXDatePicker txtfDatePicker;
    @FXML
    private Text txtfError;
    @FXML
    private ComboBox<Medecin> cboMedecin;
    @FXML
    private ComboBox<String> cboAction;
    @FXML
    private ComboBox<String> cboFiltreStatutRv;
    @FXML
    private ComboBox<String> cboFiltreTypeRv;
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
    private Button btnUpdate;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTableViewRV();
        loadComboBoxAllMedecin(cboMedecin);
        loadComboBoxSecretaire(cboAction,cboFiltreStatutRv,cboFiltreTypeRv);
    }
    

    @FXML
    private void handleUpdateRv(ActionEvent event) {
        if(confirmDate(txtfDatePicker.getValue())==0 && cboMedecin.getSelectionModel().getSelectedItem()!=null){
            rvEntity.setDate(txtfDatePicker.getValue()+"");
            rvEntity.setIdmedecin(cboMedecin.getSelectionModel().getSelectedItem().getId());
            rvEntity.setId(rvSelected.getId());
            switch(cboAction.getSelectionModel().getSelectedItem())
            {
                case "Valider":
                    rvEntity.setStatut("Valide");
                    txtfError.setVisible(false);
                    update();
                    clear();
                    break;
                case "Decliner": 
                    rvEntity.setStatut("Decline");
                    txtfError.setVisible(false);
                    update();
                    clear();
                    break;
                case "Mettre en attente":
                    rvEntity.setStatut("En attente");
                    txtfError.setVisible(false);
                    update();
                    clear();
                    break;
                default:
                    txtfError.setText("Selectionnez un action!");
                    txtfError.setVisible(true);
            }
        }else{
            txtfError.setText("Verifier les donnees saisies!");
            txtfError.setVisible(true);
        }
    }
    private void update(){
        if(service.updateRv(rvEntity)){
            alertInformation("Alert", "Modification effectue avec succes!");
            tblvRendezvous.getItems().clear();
            loadTableViewRV();
        }
    }
    private int confirmDate(LocalDate date) {
        //  Creation et tranformation de la date actuelle
        LocalDate today = LocalDate.now();
        String today_str = today + "";
        String[] today_array;
        today_array = today_str.split("-");

        // Recuperation et transformation de la date
        String date_str = date + "";
        String[] date_array = date_str.split("-");

        if (date_array.length != 3) {
            return 2;
        } else if ((Integer.parseInt(date_array[2])
                - Integer.parseInt(today_array[2]) <= 0)) {
            if (Integer.parseInt(date_array[1])
                    - Integer.parseInt(today_array[1]) <= 0) {
                if (Integer.parseInt(date_array[0])
                        - Integer.parseInt(today_array[0]) <= 0) {
                    return 1;
                }
            }

        }
        return 0;
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
        btnUpdate.setDisable(false);
        txtfDatePicker.setValue(LocalDate.parse(rvSelected.getDate()));
        Rendezvous rv = service.findRvById(rvSelected.getId());
        if(rv.getIdpatient()!=0){
            Patient patient = service.findPatientById(rv.getIdpatient());
            txtfNomPrenom.setText(patient.getPrenom()+" "+patient.getNom());
        }
        rvEntity = service.findRvById(rvSelected.getId());
    }
    
    
    private void loadTableViewRV() {
        obRendezvous = FXCollections.
                observableArrayList(service.showAllRvDTO());
        tblcType.setCellValueFactory(new PropertyValueFactory<>("consouprest"));
        tblcMedecin.setCellValueFactory(new PropertyValueFactory<>("medecin"));
        tblcDateSouh.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblcStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        tblcPrestation.setCellValueFactory(new PropertyValueFactory<>("typeprest"));
        tblvRendezvous.setItems(obRendezvous);
    }
    
     public void loadComboBoxAllMedecin(ComboBox<Medecin> cbo){
        obMedecins = FXCollections.observableArrayList(service.findAllMedecin());
        cbo.setItems(obMedecins);
    }

    @FXML
    private void HandleFilterByStatutAndType(ActionEvent event) {
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }
    private void clear() {
        tblvRendezvous.getSelectionModel().clearSelection();
        txtfDatePicker.setValue(null);
        rvSelected = null;
        txtfError.setVisible(false);
        txtfNomPrenom.setText("");
        cboAction.getSelectionModel().selectFirst();
        cboMedecin.getSelectionModel().selectFirst();
        cboFiltreTypeRv.getSelectionModel().selectFirst();
        btnUpdate.setDisable(true);
    }
}
