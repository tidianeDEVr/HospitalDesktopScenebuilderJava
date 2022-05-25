/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package services;


import dto.RendezvousDTO;
import entities.AntecMedicaux;
import entities.Constantes;
import entities.Medecin;
import entities.Patient;
import entities.Rendezvous;
import entities.User;
import java.util.List;

/**
 *
 * @author HP
 */
public interface IService {
  
    // Patient
    public int addPatient(Patient patient);
    public Patient findPatientById(int id);
    public List<Patient> findAllPatient();
    
    // Medecin
    public List<Medecin> findAllMedecin();
    public Medecin findMedecinById(int id);
    
    // Rendez vous
    public int addRendezvous(Rendezvous rv);
    public Rendezvous findRvById(int id);
    public List<Rendezvous> showAllRV();
    public List<RendezvousDTO> showAllRvByDate(String date);
    public List<RendezvousDTO> showAllRvDTO();
    public List<RendezvousDTO> showAllPrest();
    public List<RendezvousDTO> showAllPrestByDate(String date);
    public List<RendezvousDTO> showAllPrestByStatut(String statut);
    public List<RendezvousDTO> showAllPrestByDateAndStatut(String statut, String date);
    public List<RendezvousDTO> showRvByPatient(int user);
    public List<RendezvousDTO> showRvByMedecin(int user);
    public boolean annulerRV(int id);
    public boolean updateRv(Rendezvous rv);
    
    // antecedents medical
    public List<AntecMedicaux> searchAllAntMedicaux();
    public AntecMedicaux findAntMedByPatient(int id);
    public int updateAntMed(int id, String libelle);
    public int insertAntMed(AntecMedicaux antmed);
    
    // Constantes
    public Constantes findConstanteByPatient(int id);
    public int updateConstante(int id, int temp, int tension);
    public int insertConstante(Constantes constante);
    
    /*Se connecter */
    public User login(String login,String password);
    
    
    // gerer patient
      

}
