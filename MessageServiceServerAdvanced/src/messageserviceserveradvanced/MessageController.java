/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserviceserveradvanced;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author youcee
 */

public class MessageController {
    
    String driver;
    String url;
    
    public MessageController(){
        this.driver = "com.mysql.jdbc.Driver";
        this.url = "jdbc:mysql://localhost/msgDB";
    }
    
    public List<String> allMessages(){
        List<String> allmsg = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url,"root", "java@1986");
            String sql = "SELECT * FROM Messages";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                allmsg.add(rst.getString("message"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allmsg;
    }
    
    public boolean writeMessage(Message message){
            boolean outcome = false;
        try {
            Connection conn = DriverManager.getConnection(url,"root", "java@1986");
            String sql = "INSERT INTO Messages (message) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql); 
            stmt.setString(1, message.getMessage());
            stmt.execute();
            outcome = true;
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outcome;
    }
    
}
