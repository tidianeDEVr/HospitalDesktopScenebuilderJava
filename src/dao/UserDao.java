/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class UserDao implements IDao{

    private final String  SQL_LOGIN = "SELECT * FROM users WHERE mail =  ? AND mdp = ? AND actif = 1";
    private final String  SQL_MAIL = "SELECT * FROM users WHERE mail =  ?";
    private final DataBase database= new DataBase();
    
    @Override
    public int insert(Object ogj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Object ogj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public User findUserByMail(String mail){
        User user = null;
        database.openConnexion();
        database.initPrepareStatement(SQL_MAIL);
        try {
            database.getPs().setString(1, mail);
            ResultSet rs = database.executeSelect(SQL_MAIL);
        
            if(rs.next())
            {
                    user = new User(
                    rs.getString("nom")
//                    rs.getString("login"),
//                    rs.getString("password"),
//                    rs.getString("role") 
                    );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    public User findUserLoginAndPassword(String mail,String password){
        User user = null;
        database.openConnexion();
        database.initPrepareStatement(SQL_LOGIN);
        try {
            database.getPs().setString(1, mail);
            database.getPs().setString(2, password);
            ResultSet rs = database.executeSelect(SQL_LOGIN);
            if(rs.next())
            {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("role"),
                    rs.getString("prenom"),
                    rs.getString("nom") 
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
