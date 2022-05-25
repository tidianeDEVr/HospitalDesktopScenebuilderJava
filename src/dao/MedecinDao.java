/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

//import dto.ProfesseurDTO;
import entities.Medecin;
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
public class MedecinDao implements IDao<Medecin> {

    private final String SQL_SELECT_ALL = 
            "SELECT * FROM users P WHERE P.ROLE LIKE 'ROLE_MEDECIN'";
    private final String SQL_SELECT_MEDECIN_BY_ID = 
            "SELECT * FROM users WHERE id LIKE ? ";
//    private final String SQL_SELECT_PATIENT_BY_MAIL = 
//            "SELECT * FROM users p WHERE mail LIKE ?";
    private final String SQL_INSERT = "INSERT INTO `users` "
            + "(`nom`, `prenom`, `role`,`mail` , `mdp`, idAntecMed) "
            + "VALUES (?, ?, 'ROLE_MEDECIN', ?, ?, ?);" ;
    private final DataBase database = new DataBase();
    
    @Override
    public int insert(Medecin medecin) {
        return 0;
    }

    @Override
    public int update(Medecin medecin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Medecin> findAll() {
        List<Medecin> medecins = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL);
        ResultSet rs = database.executeSelect(SQL_SELECT_ALL);
        try {
            while (rs.next()){
                Medecin medecin = new Medecin();
                try {
                    medecin.setId(rs.getInt("id"));
                    medecin.setNom(rs.getString("nom"));
                    medecin.setPrenom(rs.getString("prenom"));
                    medecins.add(medecin);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (SQLException ex) {            
            Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return medecins;
    }

    @Override
    public Medecin findById(int id) {
        Medecin medecin = null;
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_MEDECIN_BY_ID);
        try {
            database.getPs().setInt(1, id);
            ResultSet rs = database.executeSelect(SQL_SELECT_MEDECIN_BY_ID);
            //Mapping relation vers objet
            if(rs.next())
           {
            medecin = new Medecin(
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getInt("id")
            );
           }
        } catch (SQLException ex) {
            Logger.getLogger(MedecinDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return medecin;
    }
}
