/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import entities.AntecMedicaux;
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
public class AntecMedicauxDao implements IDao<AntecMedicaux> {
    
    DataBase database= new DataBase();
    
    /*Request sql*/
    private final String SQL_INSERT="INSERT INTO `antecmedicaux` (`libelle`) VALUES (?)";
    private final String SQL_UPDATE="UPDATE `antecmedicaux` SET `libelle`=? WHERE `id`= ?";
    private final String SQL_DELETE="Delete from classe where id_classe=?";
    private final String SQL_ALL=" SELECT * FROM antecmedicaux";
    private final String SQL_BY_ID="SELECT * FROM `antecmedicaux` WHERE id=?";
    private final String SQL_BY_PROF_ANNEE="SELECT * FROM classe c, affectation a, user p  WHERE c.id_classe=a.classe_id and a.prof_id=p.id "
                                                               +" and a.annee like ? and p.nci like ?";

    public int insert(AntecMedicaux antecMedicaux) {
        int lastInsertId=0;
        database.openConnexion();
        database.initPrepareStatement(SQL_INSERT);
        try {
            database.getPs().setString(1, antecMedicaux.getLibelle());
            database.executeUpdate(SQL_INSERT);
            ResultSet rs=database.getPs().getGeneratedKeys();
            if(rs.next()){
            lastInsertId=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        database.closeConnexion();
        return lastInsertId;
    }

    public int update(int id, String libelle) {
         int nbrLigne=0;
        database.openConnexion();
        database.initPrepareStatement(SQL_UPDATE);
        try {
            database.getPs().setString(2, libelle);
            database.getPs().setInt(1, id);
            nbrLigne=database.executeUpdate(SQL_UPDATE);         
        } catch (SQLException ex) {
            Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        database.closeConnexion();
        return nbrLigne;
    }

    @Override
    public int delete(int id) {
         int nbrLigne=0;
        database.openConnexion();
        database.initPrepareStatement(SQL_DELETE);
        try {
            database.getPs().setInt(1, id);
            nbrLigne=database.executeUpdate(SQL_UPDATE);
            
        } catch (SQLException ex) {
            Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        database.closeConnexion();
        return nbrLigne;
    }

    @Override
    public List<AntecMedicaux> findAll() {
        List<AntecMedicaux> antecMedicaux=new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_ALL);
       
            ResultSet rs =database.executeSelect(SQL_ALL);
        
        try {
            while(rs.next()){
                try {
                    //Mapping relation vers objet
                    AntecMedicaux am =new AntecMedicaux(rs.getInt("id")
                            ,rs.getString("libelle"),rs.getString("description"));
                    antecMedicaux.add(am);
                } catch (SQLException ex) {
                    Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return antecMedicaux;
    }

    @Override
    public AntecMedicaux findById(int id) {
       AntecMedicaux antecMedicaux=null;
        database.openConnexion();
        database.initPrepareStatement(SQL_BY_ID);
        try {
            database.getPs().setInt(1, id);
            ResultSet rs =database.executeSelect(SQL_BY_ID);
            //Mapping relation vers objet
            if(rs.next())
            {
            antecMedicaux=new AntecMedicaux(
                rs.getInt("id"),
                rs.getString("libelle"),
                rs.getString("description")
            );    
            }
        } catch (SQLException ex) {
            Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return antecMedicaux;
    }
    
//    public List<AntecMedicaux> findClasseByProfesseur(String nci,String annee) {
//        List<AntecMedicaux> classes=new ArrayList<>();
//        database.openConnexion();
//        database.initPrepareStatement(SQL_BY_PROF_ANNEE);
//        try {
//            database.getPs().setString(1, annee);
//            database.getPs().setString(2, nci);
//            ResultSet rs =database.executeSelect(SQL_BY_PROF_ANNEE);
//        
//        try {
//            while(rs.next()){
//                try {
//                    //Mapping relation vers objet
//                    AntecMedicaux cl =new Classe(rs.getInt("id_classe"),rs.getString("libelle"));
//                    classes.add(cl);
//                } catch (SQLException ex) {
//                    Logger.getLogger(ClasseDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ClasseDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        } catch (SQLException ex) {
//            Logger.getLogger(ClasseDao.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//            database.closeConnexion();
//        }
//        return classes;
//    }

    @Override
    public int update(AntecMedicaux ogj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
