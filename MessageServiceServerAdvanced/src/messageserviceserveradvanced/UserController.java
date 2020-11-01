/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserviceserveradvanced;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author youcee
 */
public class UserController {
    
    String driver;
    String url;
    
    public UserController(){
        this.driver = "com.mysql.jdbc.Driver";
        this.url = "jdbc:mysql://localhost/msgDB";
    }
    public boolean addUser(User user){
        boolean outcome = false;
        try {
            //Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,"root", "java@1986");
            String sql = "INSERT INTO Users (userName,password,userRole) VALUES (?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole());
            pst.execute();
            outcome = true;
        } catch (SQLException ex) {
            Logger.getLogger(MessageServiceServerAdvanced.class.getName()).log(Level.SEVERE, null, ex);
            outcome = false;
        }
        return outcome;
    }
    
    public String confirmLoginDetails(User user){
        String result = "";
        try {
            Connection conn = DriverManager.getConnection(url,"root", "java@1986");
            String sql = "SELECT * FROM Users WHERE userName = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            ResultSet rset = pst.executeQuery();
            if (rset.next()){
                result = "success:" + rset.getString("userRole");
            }else{
                result = "failure: ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageServiceServerAdvanced.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public boolean removeUser(User user){
        boolean outcome = false;
        try {
            Connection conn = DriverManager.getConnection(url,"root", "java@1986");
            String sql = "SELECT * FROM Users WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            ResultSet rset = pst.executeQuery();
            rset.next();
            int id = rset.getInt(1);
            String sql2 = "DELETE FROM Users WHERE userID=?";
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            pst2.setInt(1, id);
            pst2.execute();
            outcome = true;
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outcome;
    }
    
}
