/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services;


import dao.AntecMedicauxDao;
import dao.ConstantesDao;
import dao.MedecinDao;
import dao.PatientDao;
import dao.RendezvousDao;
import dao.UserDao;
import dto.RendezvousDTO;
import entities.AntecMedicaux;
import entities.Constantes;
import entities.Medecin;
import entities.Patient;
import entities.Rendezvous;
import entities.User;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author HP
 */
public class Service implements IService {
    //Dependances avec la couche DAO
  
    UserDao daoUser=new UserDao();
    AntecMedicauxDao daoAntecMedicaux = new AntecMedicauxDao();
    ConstantesDao doaConstantes = new ConstantesDao();
    PatientDao daoPatient=new PatientDao();
    MedecinDao daoMedecin=new MedecinDao();
    RendezvousDao daoRendezvous=new RendezvousDao();
    
    // Patient 
    @Override
    public int addPatient(Patient patient) {
        return daoPatient.insert(patient);
    }
    @Override
    public Patient findPatientById(int id){
        return daoPatient.findById(id);
    }
    @Override
    public List<Patient> findAllPatient(){
        return daoPatient.findAll();
    }
    // Medecin
    @Override
    public List<Medecin> findAllMedecin(){
        return daoMedecin.findAll();
    }
    @Override
    public Medecin findMedecinById(int id){
        return daoMedecin.findById(id);
    }
    
    // Rendez vous
    @Override
    public int addRendezvous(Rendezvous rv){
        return daoRendezvous.insert(rv);
    }
    @Override
    public Rendezvous findRvById(int id){
        return daoRendezvous.findById(id);
    }
    @Override
    public List<Rendezvous> showAllRV() {
        return daoRendezvous.findAll();
    }
    @Override
    public boolean annulerRV(int id) {
        return daoRendezvous.delete(id)!=0;
    }
    @Override
    public List<RendezvousDTO> showRvByPatient(int idpatient) {
        List<RendezvousDTO> rv = daoRendezvous.findAllByPatientDTO(idpatient);
        if(rv!=null){
            rv.forEach((rendezvous) -> {
                Rendezvous rend = daoRendezvous.findById(rendezvous.getId());
                if(rend.getIdmedecin()!=0){
                    Medecin medecin = daoMedecin.findById(rend.getIdmedecin());
                    rendezvous.setMedecin("Dr "+medecin.getNom());
                }else{
                    rendezvous.setMedecin("Indéfini");
                }
                
            });
        }
        return rv;
    }
    @Override
    public List<RendezvousDTO> showRvByMedecin(int idpatient) {
        List<RendezvousDTO> rv = daoRendezvous.findAllByMedecinDTO(idpatient);
        if(rv!=null){
            rv.forEach((rendezvous) -> {
                Rendezvous rend = daoRendezvous.findById(rendezvous.getId());
                if(rend.getIdmedecin()!=0){
                    Medecin medecin = daoMedecin.findById(rend.getIdmedecin());
                    rendezvous.setMedecin("Dr "+medecin.getNom());
                }else{
                    rendezvous.setMedecin("Indéfini");
                }
                Patient patient = daoPatient.findById(rendezvous.getIdpatient());
                rendezvous.setPatient(patient.getPrenom()+" "+patient.getNom());
            });
        }
        return rv;
    }
    @Override
    public List<RendezvousDTO> showAllRvDTO(){
        List<Rendezvous> rv = daoRendezvous.findAll();
        List<RendezvousDTO> rvd = new ArrayList();
        rv.forEach((rendezvous) -> {
            RendezvousDTO rend = new RendezvousDTO();
            rend.setId(rendezvous.getId());
            rend.setConsouprest(rendezvous.getConsouprest());
            rend.setDate(rendezvous.getDate());
            rend.setStatut(rendezvous.getStatut());
            rend.setTypeprest(rendezvous.getTypeprest());
            if(rendezvous.getIdmedecin()!=0){
                Medecin medecin = daoMedecin.findById(rendezvous.getIdmedecin());
                rend.setMedecin("Dr. "+medecin.getNom());
            }else{
                rend.setMedecin("Indéfini");
            }
            rvd.add(rend);
        });
        return rvd;
    }
    @Override
    public List<RendezvousDTO> showAllRvByDate(String date){
        List<Rendezvous> rv = daoRendezvous.findAllRvByDate(date);
        List<RendezvousDTO> rvd = new ArrayList();
        rv.forEach((rendezvous) -> {
            Patient patient = daoPatient.findById(rendezvous.getIdpatient());
            RendezvousDTO rend = new RendezvousDTO();
            rend.setId(rendezvous.getId());
            rend.setConsouprest(rendezvous.getConsouprest());
            rend.setDate(rendezvous.getDate());
            rend.setStatut(rendezvous.getStatut());
            rend.setTypeprest(rendezvous.getTypeprest());
            rend.setIdpatient(patient.getId());
            rend.setPatient(patient.getPrenom()+" "+patient.getNom());
            rvd.add(rend);
        });
        return rvd;
    }
    @Override
    public List<RendezvousDTO> showAllPrest(){
        List<Rendezvous> rv = daoRendezvous.findAllPrest();
        List<RendezvousDTO> rvd = new ArrayList();
        rv.forEach((rendezvous) -> {
            RendezvousDTO rend = new RendezvousDTO();
            rend.setId(rendezvous.getId());
            rend.setConsouprest(rendezvous.getConsouprest());
            rend.setDate(rendezvous.getDate());
            rend.setStatut(rendezvous.getStatut());
            rend.setTypeprest(rendezvous.getTypeprest());
            if(rendezvous.getIdmedecin()!=0){
                Medecin medecin = daoMedecin.findById(rendezvous.getIdmedecin());
                rend.setMedecin("Dr. "+medecin.getNom());
            }else{
                rend.setMedecin("Indéfini");
            }
            rvd.add(rend);
        });
        return rvd;
    }
    @Override
    public List<RendezvousDTO> showAllPrestByDate(String date){
        List<Rendezvous> rv = daoRendezvous.findAllPrestByDate(date);
        List<RendezvousDTO> rvd = new ArrayList();
        rv.forEach((rendezvous) -> {
            RendezvousDTO rend = new RendezvousDTO();
            rend.setId(rendezvous.getId());
            rend.setConsouprest(rendezvous.getConsouprest());
            rend.setDate(rendezvous.getDate());
            rend.setStatut(rendezvous.getStatut());
            rend.setTypeprest(rendezvous.getTypeprest());
            if(rendezvous.getIdmedecin()!=0){
                Medecin medecin = daoMedecin.findById(rendezvous.getIdmedecin());
                rend.setMedecin("Dr. "+medecin.getNom());
            }else{
                rend.setMedecin("Indéfini");
            }
            rvd.add(rend);
        });
        return rvd;
    }
    @Override
    public List<RendezvousDTO> showAllPrestByStatut(String statut){
        List<Rendezvous> rv = daoRendezvous.findAllPrestByStatut(statut);
        List<RendezvousDTO> rvd = new ArrayList();
        rv.forEach((rendezvous) -> {
            RendezvousDTO rend = new RendezvousDTO();
            rend.setId(rendezvous.getId());
            rend.setConsouprest(rendezvous.getConsouprest());
            rend.setDate(rendezvous.getDate());
            rend.setStatut(rendezvous.getStatut());
            rend.setTypeprest(rendezvous.getTypeprest());
            if(rendezvous.getIdmedecin()!=0){
                Medecin medecin = daoMedecin.findById(rendezvous.getIdmedecin());
                rend.setMedecin("Dr. "+medecin.getNom());
            }else{
                rend.setMedecin("Indéfini");
            }
            rvd.add(rend);
        });
        return rvd;
    }
    public List<RendezvousDTO> showAllPrestByDateAndStatut(String statut, String date){
        List<Rendezvous> rv = daoRendezvous.findAllPrestByDateAndStatut(statut, date);
        List<RendezvousDTO> rvd = new ArrayList();
        rv.forEach((rendezvous) -> {
            RendezvousDTO rend = new RendezvousDTO();
            rend.setId(rendezvous.getId());
            rend.setConsouprest(rendezvous.getConsouprest());
            rend.setDate(rendezvous.getDate());
            rend.setStatut(rendezvous.getStatut());
            rend.setTypeprest(rendezvous.getTypeprest());
            if(rendezvous.getIdmedecin()!=0){
                Medecin medecin = daoMedecin.findById(rendezvous.getIdmedecin());
                rend.setMedecin("Dr. "+medecin.getNom());
            }else{
                rend.setMedecin("Indéfini");
            }
            rvd.add(rend);
        });
        return rvd;
    }
    @Override
    public boolean updateRv(Rendezvous rv) {
        return daoRendezvous.update(rv)!=0;
    }

    // Antecedant Medicaux 
    @Override
    public List<AntecMedicaux> searchAllAntMedicaux() {
        return daoAntecMedicaux.findAll();
    }
    @Override
    public AntecMedicaux findAntMedByPatient(int id){
        return daoAntecMedicaux.findById(id);
    }
    @Override
    public int updateAntMed(int id, String libelle){
        return daoAntecMedicaux.update(id, libelle);
    }
    @Override
    public int insertAntMed(AntecMedicaux antmed){
        return daoAntecMedicaux.insert(antmed);
    }
    
    // Constantes
    @Override
    public Constantes findConstanteByPatient(int id){
        return doaConstantes.findById(id);
    }
    @Override
    public int updateConstante(int id, int temp, int tension){
        return doaConstantes.update(id, temp, tension);
    }
    @Override
    public int insertConstante(Constantes constante){
        return doaConstantes.insert(constante);
    }
    
    // Se connecter
    @Override
    public User login(String login, String password) {
        return daoUser.findUserLoginAndPassword(login, password);
         
    }
}
