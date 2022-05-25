/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;


import dto.RendezvousDTO;
import entities.Rendezvous;
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
public class RendezvousDao implements IDao<Rendezvous> {

    private final String SQL_SELECT_ALL = "SELECT * FROM rendezvous";
    private final String SQL_SELECT_ALL_PREST = "SELECT * FROM rendezvous WHERE `consouprest`=?";
    private final String SQL_SELECT_ALL_PREST_BY_DATE = "SELECT * FROM rendezvous WHERE `consouprest`=? AND `date`=?";
    private final String SQL_SELECT_ALL_RV_BY_DATE = "SELECT * FROM rendezvous WHERE `date`=?";
    private final String SQL_SELECT_ALL_PREST_BY_STATUT = "SELECT * FROM rendezvous WHERE `consouprest`=? AND `statut`=?";
    private final String SQL_SELECT_ALL_PREST_BY_DATE_AND_STATUT = "SELECT * FROM rendezvous WHERE `consouprest`LIKE? AND `statut`LIKE? AND `date`LIKE? ";
    private final String SQL_INSERT = "INSERT INTO `rendezvous` "
            + "(`idpatient`, `idmedecin`, `consouprest`,`typeprest` , `date`, `statut`) "
            + "VALUES (?, ?, ?, ?, ?, 'En attente');" ;
    private final String SQL_DELETE="Delete from rendezvous where id=?";
    private final String SQL_UPDATE="UPDATE `rendezvous` SET `statut`=?, `date`=?, `idmedecin`=?  WHERE `id`= ?";
    private final DataBase database = new DataBase();
    private final String SQL_SELECT_RV_BY_PATIENT = 
            "SELECT * FROM rendezvous WHERE idpatient LIKE ?";
    private final String SQL_SELECT_MEDECIN_BY_RV = 
            "SELECT * FROM rendezvous WHERE idmedecin LIKE ?";
    
     private final String SQL_BY_ID="SELECT * FROM `rendezvous` WHERE id = ?";
    
    private DataBase dataBase = new DataBase();
    
    
    @Override
    public int insert(Rendezvous rendezvous) {
        int id = 0;
        try {
            database.openConnexion();
            database.initPrepareStatement(SQL_INSERT);
            database.getPs().setInt(1, rendezvous.getIdpatient());
            database.getPs().setInt(2, rendezvous.getIdmedecin());
            database.getPs().setString(3, rendezvous.getConsouprest());
            database.getPs().setString(4, rendezvous.getTypeprest());
            database.getPs().setString(5, rendezvous.getDate());
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
    public int update(Rendezvous rv) {
        int nbrLigne=0;
        database.openConnexion();
        database.initPrepareStatement(SQL_UPDATE);
        try {
            database.getPs().setString(1, rv.getStatut());
            database.getPs().setString(2, rv.getDate());
            database.getPs().setInt(3, rv.getIdmedecin());
            database.getPs().setInt(4, rv.getId());
            nbrLigne=database.executeUpdate(SQL_UPDATE);         
        } catch (SQLException ex) {
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        database.closeConnexion();
        return nbrLigne;
    }

    @Override
    public int delete(int id) {
        int nbrLigne = 0;
        database.openConnexion();
        database.initPrepareStatement(SQL_DELETE);
        try {
            database.getPs().setInt(1, id);
            nbrLigne = database.executeUpdate(SQL_UPDATE);

        } catch (SQLException ex) {
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        database.closeConnexion();
        return nbrLigne;
    }

    @Override
    public List<Rendezvous> findAll() {
        List<Rendezvous> rendezvous = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL);
        ResultSet rs = database.executeSelect(SQL_SELECT_ALL);
        try {
            while (rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
    public List<Rendezvous> findAllRvByDate(String date){
        List<Rendezvous> rendezvous = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL_RV_BY_DATE);
        try {
            database.getPs().setString(1, date);
            ResultSet rs = database.executeSelect(SQL_SELECT_ALL_RV_BY_DATE);
            while (rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
    public List<Rendezvous> findAllPrest(){
        String type = "Prestation";
        List<Rendezvous> rendezvous = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL_PREST);
        try {
            database.getPs().setString(1, type);
            ResultSet rs = database.executeSelect(SQL_SELECT_ALL_PREST);
            while (rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
    public List<Rendezvous> findAllPrestByDate(String date){
        String type = "Prestation";
        List<Rendezvous> rendezvous = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL_PREST_BY_DATE);
        try {
            database.getPs().setString(1, type);
            database.getPs().setString(2, date);
            ResultSet rs = database.executeSelect(SQL_SELECT_ALL_PREST_BY_DATE);
            while (rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
    public List<Rendezvous> findAllPrestByStatut(String statut){
        String type = "Prestation";
        List<Rendezvous> rendezvous = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL_PREST_BY_STATUT);
        try {
            database.getPs().setString(1, type);
            database.getPs().setString(2, statut);
            ResultSet rs = database.executeSelect(SQL_SELECT_ALL_PREST_BY_STATUT);
            while (rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
     public List<Rendezvous> findAllPrestByDateAndStatut(String statut, String date){
        String type = "Prestation";
        List<Rendezvous> rendezvous = new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_ALL_PREST_BY_DATE_AND_STATUT);
        try {
            database.getPs().setString(1, type);
            database.getPs().setString(2, statut);
            database.getPs().setString(3, date);
            ResultSet rs = database.executeSelect(SQL_SELECT_ALL_PREST_BY_DATE_AND_STATUT);
            while (rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {            
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
    @Override
    public Rendezvous findById(int id) {
        Rendezvous rendezvous=null;
        database.openConnexion();
        database.initPrepareStatement(SQL_BY_ID);
        try {
            database.getPs().setInt(1, id);
            ResultSet rs =database.executeSelect(SQL_BY_ID);
            if(rs.next())
           {
                rendezvous = new Rendezvous();
                rendezvous.setId(rs.getInt("id"));
                rendezvous.setIdpatient(rs.getInt("idpatient"));
                rendezvous.setIdmedecin(rs.getInt("idmedecin"));
                rendezvous.setConsouprest(rs.getString("consouprest"));
                rendezvous.setTypeprest(rs.getString("typeprest"));
                rendezvous.setDate(rs.getString("date"));
                rendezvous.setStatut(rs.getString("statut"));
           }
        } catch (SQLException ex) {
            Logger.getLogger(ConstantesDao.class.getName()).log(Level.SEVERE, null, ex);
        }   
        database.closeConnexion();
        return rendezvous;  
    }
    
    public List<Rendezvous> findAllByPatient(int idpatient) throws SQLException {
        List<Rendezvous> rendezvous=new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_SELECT_RV_BY_PATIENT);
        database.getPs().setInt(1, idpatient);
        ResultSet rs =database.executeSelect(SQL_SELECT_RV_BY_PATIENT);
        try {
            while(rs.next()){
                Rendezvous rv = new Rendezvous();
                try {
                    rv.setId(rs.getInt("id"));
                    rv.setIdpatient(rs.getInt("idpatient"));
                    rv.setIdmedecin(rs.getInt("idmedecin"));
                    rv.setConsouprest(rs.getString("consouprest"));
                    rv.setTypeprest(rs.getString("typeprest"));
                    rv.setDate(rs.getString("date"));
                    rv.setStatut(rs.getString("statut"));
                    rendezvous.add(rv);
                } catch (SQLException ex) {
                    Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RendezvousDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return rendezvous;
    }
    
    public List<RendezvousDTO> findAllByPatientDTO(int patient) {
        List<RendezvousDTO> rendezvous = new ArrayList(); 
        dataBase.openConnexion();
        dataBase.initPrepareStatement(SQL_SELECT_RV_BY_PATIENT);
        try {
            dataBase.getPs().setInt(1, patient);
            ResultSet rs = dataBase.executeSelect(SQL_SELECT_RV_BY_PATIENT);
            while(rs.next())
            {
                RendezvousDTO rv = new RendezvousDTO();
                rv.setId(rs.getInt("id"));
                rv.setConsouprest(rs.getString("consouprest"));
                rv.setTypeprest(rs.getString("typeprest"));
                rv.setDate(rs.getString("date"));
                rv.setStatut(rs.getString("statut"));
                rendezvous.add(rv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RendezvousDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataBase.closeConnexion();
        return rendezvous;
    }
    public List<RendezvousDTO> findAllByMedecinDTO(int patient) {
        List<RendezvousDTO> rendezvous = new ArrayList(); 
        dataBase.openConnexion();
        dataBase.initPrepareStatement(SQL_SELECT_MEDECIN_BY_RV);
        try {
            dataBase.getPs().setInt(1, patient);
            ResultSet rs = dataBase.executeSelect(SQL_SELECT_MEDECIN_BY_RV);
            while(rs.next())
            {
                RendezvousDTO rv = new RendezvousDTO();
                rv.setId(rs.getInt("id"));
                rv.setIdpatient(rs.getInt("idpatient"));
                rv.setConsouprest(rs.getString("consouprest"));
                rv.setTypeprest(rs.getString("typeprest"));
                rv.setDate(rs.getString("date"));
                rv.setStatut(rs.getString("statut"));
                rendezvous.add(rv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RendezvousDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataBase.closeConnexion();
        return rendezvous;
    }
}
