/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.helper;

import almaul.models.Account;
import java.sql.*;

/**
 *
 * @author NoName
 */
public class Helper_Account {
    Account acc;
    
    public static void LoginAccount(Account acc) throws SQLException{
        Koneksi db = new Koneksi();
        
        String query = "select * from accounts where username = \""+acc.getUsername()+"\" and password=\""+acc.getPassword()+"\"";
        try{
            ResultSet rs =  db.getStatement().executeQuery(query);
            if(rs.next()){
                acc.setRole(rs.getString("role"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
        }
    }
    
}
