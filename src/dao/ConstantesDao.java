/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import entities.Constantes;
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
public class ConstantesDao implements IDao<Constantes> {
    
    DataBase database= new DataBase();
    
    /*Request sql*/
    private final String SQL_INSERT = "INSERT INTO `constantes` "
            + "(`idpatient`, `temperature`, `tension`) "
            + "VALUES (?, ?, ?);" ;
    private final String SQL_UPDATE="UPDATE `constantes` SET `temperature`=?, `tension`=? WHERE `id`= ?";
    private final String SQL_DELETE="Delete from constantes where idpatient=?";
    private final String SQL_ALL=" SELECT * FROM constantes";
    private final String SQL_BY_ID="SELECT * FROM `constantes` WHERE idpatient = ?";
    

    public int insert(Constantes constantes) {
        int lastInsertId=0;
        database.openConnexion();
        database.initPrepareStatement(SQL_INSERT);
        try {
            database.getPs().setInt(1, constantes.getIdpatient());
            database.getPs().setInt(2, constantes.getTemperature());
            database.getPs().setInt(3, constantes.getTension());
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

    public int update(int id, int temp, int tension) {
        int nbrLigne=0;
        database.openConnexion();
        database.initPrepareStatement(SQL_UPDATE);
        try {
            database.getPs().setInt(1, temp);
            database.getPs().setInt(2, tension);
            database.getPs().setInt(3, id);
            nbrLigne=database.executeUpdate(SQL_UPDATE);         
        } catch (SQLException ex) {
            Logger.getLogger(ConstantesDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Constantes> findAll() {
        List<Constantes> constantes=new ArrayList<>();
        database.openConnexion();
        database.initPrepareStatement(SQL_ALL);
       
            ResultSet rs =database.executeSelect(SQL_ALL);
        
        try {
            while(rs.next()){
                try {
                    //Mapping relation vers objet
                    Constantes co =new Constantes(rs.getInt("id"), rs.getInt("idpatient")
                            ,rs.getInt("temperature"),rs.getInt("tension"));
                    constantes.add(co);
                } catch (SQLException ex) {
                    Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AntecMedicauxDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        database.closeConnexion();
        return constantes;
    }

    @Override
    public Constantes findById(int id) {
        Constantes constantes=null;
        database.openConnexion();
        database.initPrepareStatement(SQL_BY_ID);
        try {
            database.getPs().setInt(1, id);
            ResultSet rs =database.executeSelect(SQL_BY_ID);
            if(rs.next())
           {
                constantes = new Constantes();
                constantes.setId(rs.getInt("id"));
                constantes.setIdpatient(rs.getInt("idpatient"));
                constantes.setTemperature(rs.getInt("temperature"));
                constantes.setTension(rs.getInt("tension"));
           }
        } catch (SQLException ex) {
            Logger.getLogger(ConstantesDao.class.getName()).log(Level.SEVERE, null, ex);
        }   
        database.closeConnexion();
        return constantes;        
    }

    @Override
    public int update(Constantes ogj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
