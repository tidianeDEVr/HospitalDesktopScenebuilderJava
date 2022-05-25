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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class PatientAddListController implements Initializable {

    Service service = new Service();
    private ObservableList<RendezvousDTO> obRendezvous;
    private RendezvousDTO rvSelected;
    private static PatientController ctrl;
    
    @FXML
    private TextField txtfTemp;
    @FXML
    private TextField txtfTension;
    @FXML
    private ComboBox<String> cboTypeRv;
    @FXML
    private JFXDatePicker txtfDatePicker;
    @FXML
    private ComboBox<String> cboTypePrest;
    @FXML
    private TableView<RendezvousDTO> tblvRendezvous;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcType;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcMedecin;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcDateSouh;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcStatut;
    @FXML
    private TableColumn<RendezvousDTO, String> tblcPrestation;
    @FXML
    private Text txtfError;
    @FXML
    private Button btnAnnuler;
    @FXML
    private TextField txtfAntMed;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<RendezvousDTO> rendezvous = service.showRvByPatient(ConnexionController.getCtrl().getUser().getId());
        ViewService.loadComboBoxTypeRv(cboTypeRv);
        ViewService.loadComboBoxTypePrest(cboTypePrest);
        loadConstante();
        loadAntMed();
        loadTableViewRV();
    }

    @FXML
    private void HandleClear(ActionEvent event) {
        clear();
    }
    private void clear() {
        rvSelected = null;
        txtfDatePicker.setValue(null);
        txtfError.setVisible(false);
        btnAnnuler.setDisable(true);
        tblvRendezvous.getSelectionModel().clearSelection();
    }
    @FXML
    private void handleMakeRv(ActionEvent event) {
        txtfError.setVisible(false);
        // Recuperation des champs
        int id_patient = ConnexionController.getCtrl().getUser().getId();
        LocalDate date = txtfDatePicker.getValue();
        String typeRv = cboTypeRv.getValue();
        String typePrest = cboTypePrest.getValue();

        if (confirmDate(date) != 0) {
            txtfError.setText("Selectionnez une date valide!");
            txtfError.setVisible(true);
        } else if ("Prestation".equals(typeRv) && "".equals(typePrest)) {
            txtfError.setText("Selectionnez un type de prestation!");
            txtfError.setVisible(true);
        } else {
            Rendezvous rv = new Rendezvous();
            rv.setIdpatient(id_patient);
            rv.setIdmedecin(0);
            rv.setConsouprest(typeRv);
            rv.setTypeprest(typePrest);
            rv.setDate(date + "");
            rv.setStatut("En attente");
            service.addRendezvous(rv);
            clear();
            // Ajout du rv dans l'obervable liste 
            RendezvousDTO rvd = new RendezvousDTO();
            rvd.setId(rv.getId());
            rvd.setMedecin("Indéfini");
            rvd.setDate(rv.getDate());
            rvd.setTypeprest(rv.getTypeprest());
            rvd.setConsouprest(rv.getConsouprest());
            rvd.setStatut(rv.getStatut());

            obRendezvous.add(rvd);
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

    private void loadTableViewRV() {
        obRendezvous = FXCollections.
                observableArrayList(service.showRvByPatient(ConnexionController.getCtrl().getUser().getId()));
        tblcType.setCellValueFactory(new PropertyValueFactory<>("consouprest"));
        tblcMedecin.setCellValueFactory(new PropertyValueFactory<>("medecin"));
        tblcDateSouh.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblcStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        tblcPrestation.setCellValueFactory(new PropertyValueFactory<>("typeprest"));
        tblvRendezvous.setItems(obRendezvous);
    }

    @FXML
    private void handleAnnulerRV(ActionEvent event) {
        System.out.println(delDate(rvSelected.getDate()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Rendez-vous");
        alert.setContentText("Etes-vous sure de vouloir annuler le rendez-vous ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (delDate(rvSelected.getDate())) {
                if (service.annulerRV(rvSelected.getId())) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rendez-vous");
                    alert.setContentText("Rendez-vous annuler avec succes");
                    alert.show();
                    obRendezvous.remove(searchRV(rvSelected));
                    rvSelected = tblvRendezvous.getSelectionModel().getSelectedItem();
                    txtfError.setVisible(false);
                }
            } else {
                txtfError.setText("Moins de deux jours ! Suppression impossible");
                txtfError.setVisible(true);
            }
        }
    }

    private boolean delDate(String date) {
        //  Creation et tranformation de la date actuelle
        LocalDate today = LocalDate.now();
        String today_str = today + "";
        String[] today_array;
        today_array = today_str.split("-");
        
        String[] date_array = rvSelected.getDate().split("-");
        if ((Integer.parseInt(date_array[2]) - Integer.parseInt(today_array[2]) <= 2 
                && (Integer.parseInt(date_array[2]) - Integer.parseInt(today_array[2]) >=0)
                )) {
            if (Integer.parseInt(date_array[1]) == Integer.parseInt(today_array[1])) {
                if (Integer.parseInt(date_array[0]) == Integer.parseInt(today_array[0])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private int searchRV(RendezvousDTO rendezvous) {
        int pos = -1;
        for (RendezvousDTO rv : obRendezvous) {
            pos++;
            if (rv.getId() == rendezvous.getId()) {
                return pos;
            }
        }
        return pos;
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
    private void HandleSelectRV(MouseEvent event) {
        rvSelected = tblvRendezvous.getSelectionModel().getSelectedItem();
        btnAnnuler.setDisable(false);
    }

    private void loadConstante() {
        Constantes constante = service.findConstanteByPatient(ConnexionController.getCtrl().getUser().getId());
        if (constante == null) {
            txtfTemp.setText("0");
            txtfTension.setText("0");
        } else {
            txtfTemp.setText(Integer.toString(constante.getTemperature()));
            txtfTension.setText(Integer.toString(constante.getTension()));
        }
    }

    private void loadAntMed() {
        int id = ConnexionController.getCtrl().getUser().getId();
        Patient patient = service.findPatientById(id);
        AntecMedicaux antmed = service.findAntMedByPatient(patient.getIdAntecMed());
        if (antmed == null) {
            txtfAntMed.setText("Nulle");
        } else {
            txtfAntMed.setText(antmed.getLibelle());
        }
    }
}
