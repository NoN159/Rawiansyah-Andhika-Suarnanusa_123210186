/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.helper;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import almaul.models.Renter;

/**
 *
 * @author NoName
 */
public class Helper_Renter {
    
    
    public static void addRenter(Renter r) throws SQLException{
        Koneksi db = new Koneksi();
        try{  
            String query = "INSERT INTO renter (name, id, contact, duration, bill, room) VALUES ('" + r.getNama() + "', '" + r.getId() + "', '" + r.getContact() + "', '" + r.getDuration() + "', '" + r.TotalBill(Helper_Room.getPrice(r.getRoom())) + "', '" + r.getRoom() + "')";
            db.getStatement().executeUpdate(query);
            System.out.println("inserted");
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
        }
    }
    
    public static List<Renter> getListRenter() throws SQLException{
        Koneksi db = new Koneksi();
        String query = "select * from renter";
        List<Renter> aw = new ArrayList<>();
        try{
            ResultSet rs = db.getStatement().executeQuery(query);
            while(rs.next()){
                Renter r = new Renter();
                r.setNama(rs.getString("name"));
                r.setContact(rs.getString("contact"));
                r.setId(rs.getInt("id"));
                r.setDuration(rs.getInt("duration"));
                r.setBill(Double.parseDouble(rs.getString("bill")));
                r.setStatus(rs.getString("status"));
                r.setRoom(rs.getString("room"));
                aw.add(r);
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
            return aw;
        }
        
    }

    public static void updateRenter(int id) throws SQLException {
        Koneksi db = new Koneksi();
        String query = "update renter set status = \"Paid\" where id = "+id;
        try{  
            db.getStatement().executeUpdate(query);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
        }
    }
    public static void deleteRenter(int id) throws SQLException{
        Koneksi db = new Koneksi();
        String query =  "delete from renter where id = "+id;
        try{
            db.getStatement().executeUpdate(query);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
        }
    }
    
    public static void updateRenter(Renter renter) throws SQLException{
        String[] dataRenter = {String.valueOf(renter.getId()),renter.getNama(),renter.getContact(),String.valueOf(renter.getDuration()),String.valueOf(renter.getBill())};
        String query = "update renter set id= "+dataRenter[0]+", "+"name = \""+dataRenter[1]+"\", contact = \""+dataRenter[2]+"\", duration="+dataRenter[3]+", bill="+dataRenter[4];
        query += " where room= \""+renter.getRoom()+"\"";
        System.out.println(query);
        Koneksi db = new Koneksi();
        try{
            db.getStatement().executeUpdate(query);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
        }
    }
    
    public static Renter getRenterById(int id) throws SQLException{
        Koneksi db = new Koneksi();
        Renter r = new Renter();
        String query = "select * from renter where id = "+id;
        try{
            ResultSet rs = db.getStatement().executeQuery(query);
            if(rs.next()){
                r.setNama(rs.getString("name"));
                r.setContact(rs.getString("contact"));
                r.setId(rs.getInt("id"));
                r.setDuration(rs.getInt("duration"));
                r.setBill(Double.parseDouble(rs.getString("bill")));
                r.setStatus(rs.getString("status"));
                r.setRoom(rs.getString("room"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
            return r;
        }
    }
}
