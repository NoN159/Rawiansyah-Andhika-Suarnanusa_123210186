/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import almaul.helper.Helper_Renter;
import almaul.helper.Helper_Room;
import almaul.models.Renter;
import almaul.views.DataRenter;

/**
 *
 * @author NoName
 */
public class Contoller_DataRenter implements ActionListener{

    Renter renter;
    DataRenter view;
    Controller_Admin ac;
    String selectedRoom;
    public Contoller_DataRenter(Renter r, Controller_Admin parent){
        this.renter=r;
        this.ac = parent;
        view = new DataRenter();
        view.setOnTop(true);
        view.setTfContact(renter.getContact());
        view.setTfName(renter.getNama());
        view.setTfRentTime(String.valueOf(renter.getDuration()));
        view.setTfid(String.valueOf(renter.getId()));
        view.getBtnAddPanel().addActionListener(this);
        view.getBtnLogout().setVisible(false);
    }
    
    Controller_RoomList rlc;
    public Contoller_DataRenter(String room, Controller_RoomList parent){
        view = new DataRenter();
        this.selectedRoom=room;
        this.rlc = parent;
        view.getBtnAddPanel().addActionListener(this);
        view.getBtnLogout().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.getBtnAddPanel()){
            if(view.getBtnLogout().isVisible()==true){
                System.out.println("added clicked");
                Renter renter = new Renter();
                renter.setNama(view.getTfName().getText());
                renter.setContact(view.getTfContact().getText());
                renter.setDuration(Integer.parseInt(view.getTfRentTime().getText().toString()));
                renter.setId(Integer.parseInt(view.getTfid().getText().toString()));
                renter.setRoom(selectedRoom);
                rlc.updateTable();
                try {
                    Helper_Renter.addRenter(renter);
                    Helper_Room.updateRoom(renter.getRoom(),renter.getNama());
                } catch (SQLException ex) {
                    Logger.getLogger(Contoller_DataRenter.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally{
                    view.getWindow().dispose();
                }
            }
            else{
                renter.setContact(view.getTfContact().getText());
                renter.setId(Integer.parseInt(view.getTfid().getText()));
                renter.setDuration(Integer.parseInt(view.getTfRentTime().getText()));
                renter.setNama(view.getTfName().getText());
                try {
                    renter.setBill(renter.TotalBill(Helper_Room.getPrice(renter.getRoom())));
                    Helper_Renter.updateRenter(renter);
                    view.getWindow().dispose();
                    ac.updateTable();
                } catch (SQLException ex) {
                    Logger.getLogger(Contoller_DataRenter.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch(ArrayIndexOutOfBoundsException aex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,aex.getCause()+"\n"+aex.getMessage());
                }
            }
        }
        else if(e.getSource()==view.getBtnLogout()){
            view.getWindow().dispose();
        }
    }
    
}
