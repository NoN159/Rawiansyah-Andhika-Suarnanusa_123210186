/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import almaul.helper.Helper_Renter;
import almaul.helper.Helper_Room;
import almaul.models.Room;
import almaul.views.ListRoom;

/**
 *
 * @author NoName
 */
public class Controller_RoomList implements ActionListener, ListSelectionListener{
    
    Controller_Login parent;
    ListRoom view;
    public Controller_RoomList(Controller_Login lc){
        this.parent=lc;
        view = new ListRoom();
        view.getFTabel().getSelectionModel().addListSelectionListener(this);
        view.getFcancel().addActionListener(this);
        
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        dtm.addColumn("Name");
        dtm.addColumn("Size");
        dtm.addColumn("Price");
        dtm.addColumn("Status");
        
        List<Room> roomList = new ArrayList<>();
        
        try{
            roomList = Helper_Room.getRoomList();
            for(Room r :roomList){
                dtm.addRow(new Object[]{r.getNama(),r.getSize(),r.getPrice(),r.getStatus()});
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            view.getFTabel().setModel(dtm);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.getFcancel()){
           parent.showPage(true);
           view.dispose();
       }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            try{
                String name = view.getFTabel().getValueAt(view.getFTabel().getSelectedRow(), 0).toString();
                Contoller_DataRenter rdc = new Contoller_DataRenter(name,this);
                rdc.view.setOnTop(true);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                
            }
        }
    }
    
    public void updateTable(){
        view.setTableModel(null);
        view.getFTabel().clearSelection();
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        dtm.addColumn("Name");
        dtm.addColumn("Size");
        dtm.addColumn("Price");
        dtm.addColumn("Status");
        
        List<Room> roomList = new ArrayList<>();
        
        try{
            roomList = Helper_Room.getRoomList();
            for(Room r :roomList){
                dtm.addRow(new Object[]{r.getNama(),r.getSize(),r.getPrice(),r.getStatus()});
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException ex){
            
        }
        finally{
            view.getFTabel().setModel(dtm);
        }
    }
    
    
}
