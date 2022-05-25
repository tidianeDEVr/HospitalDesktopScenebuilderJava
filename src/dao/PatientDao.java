/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

//import dto.ProfesseurDTO;
import entities.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class PatientDao implements IDao<Patient> {

    private final String SQL_SELECT_ALL = 
            "SELECT * FROM users P WHERE P.ROLE LIKE 'ROLE_PATIENT'";
    private final DataBase database = new DataBase();
    private final String SQL_SELECT_PATIENT_BY_MAIL = 
            "SELECT * FROM users p WHERE mail LIKE ?";
    private final String SQL_INSERT = "INSERT INTO `users` "
            + "(`nom`, `prenom`, `role`,`mail` , `mdp`, idAntecMed) "
            + "VALUES (?, ?, 'ROLE_PATIENT', ?, ?, ?);" ;
    private final String SQL_BY_ID="SELECT * FROM `users` WHERE id = ?";
    @Override
    public int insert(Patient patient) {
        int id = 0;
        try {
            database.openConnexion();
            database.initPrepareStatement(SQL_INSERT);
            database.getPs().setString(1, patient.getNom());
            database.getPs().setString(2, patient.getPrenom());
            database.getPs().setString(3, patient.getMail());
            database.getPs().setString(4, patient.getMdp());
            database.getPs().setInt(5, patient.getIdAntecMed());
            database.executeUpdate(SQL_INSERT);
            ResultSet rs = database.getPs().getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            database.closeConnexion();
        }
        return id;
    }

    @Override
    public int update(Patient ogj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL);
        ResultSet rs = database.executeSelect(SQL_SELECT_ALL);
        try {
            while (rs.next()){
                Patient patient = new Patient();
                try {
                    patient.setId(rs.getInt("id"));
                    patient.setNom(rs.getString("nom"));
                    patient.setPrenom(rs.getString("prenom"));
                    patient.setIdAntMed(rs.getInt("idAntecMed"));
                    patients.add(patient);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (SQLException ex) {            
            Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return patients;
    }

    @Override
    public Patient findById(int id) {
        Patient patient=null;
        database.openConnexion();
        database.initPrepareStatement(SQL_BY_ID);
        try {
            database.getPs().setInt(1, id);
            ResultSet rs =database.executeSelect(SQL_BY_ID);
            if(rs.next())
           {
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
                patient.setIdAntMed(rs.getInt("idAntecMed"));
           }
        } catch (SQLException ex) {
            Logger.getLogger(ConstantesDao.class.getName()).log(Level.SEVERE, null, ex);
        }   
        database.closeConnexion();
        return patient;  
    }
    
//    public ProfesseurDTO findByNci(String nci) {
//        ProfesseurDTO prof = null;
//        try {
//            database.openConnexion();
//            database.initPrepareStatement(SQL_SELECT_PATIENT_BY_MAIL);
//            database.getPs().setString(1, nci);
//            ResultSet rs = database.executeSelect(SQL_SELECT_PATIENT_BY_MAIL);
//            if(rs.next()){
//             prof = new ProfesseurDTO();
//             prof.setId(rs.getInt("if"));
//             prof.setNomComplet(rs.getString("nom_complet"));
//             prof.setGrade(rs.getString("grade"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//            database.closeConnexion();
//        }
//        return prof;
//    }

}
