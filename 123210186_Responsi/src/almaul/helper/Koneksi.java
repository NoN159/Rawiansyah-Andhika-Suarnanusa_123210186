/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author NoName
 */
public class Koneksi {
    private Connection connectionDB;
    private Statement statement;
    public Koneksi(){
        try{
            connectionDB = DriverManager.getConnection("jdbc:mysql://localhost/almaul_db", "root", "");
            statement=connectionDB.createStatement();
        }
        catch(SQLException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Statement getStatement(){
        return this.statement;
    }
    public void closeConnection() throws SQLException{
        this.connectionDB.close();
    }
}
