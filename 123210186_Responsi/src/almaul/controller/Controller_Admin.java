/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
import almaul.models.Renter;
import almaul.views.AdminPage;
import almaul.views.UbahData;


/**
 *
 * @author NoName
 */
public class Controller_Admin implements ActionListener , ListSelectionListener {
    JDialog dialog;
    AdminPage view;
    Controller_Login loginPage;
    List<Renter> renterList;
    public Controller_Admin(Controller_Login lc){
        view = new AdminPage();
        loginPage = lc;
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        dtm.addColumn("ID");
        dtm.addColumn("Name");
        dtm.addColumn("Contact");
        dtm.addColumn("Room");
        dtm.addColumn("Duration");
        dtm.addColumn("Bill");
        dtm.addColumn("Status");
        view.getFTabel().getSelectionModel().addListSelectionListener(this);
        view.getFlogout().addActionListener(this);
        try{
            renterList = Helper_Renter.getListRenter();
            for(Renter r : renterList){
                dtm.addRow(new Object[]{r.getId(),r.getNama(),r.getContact(),r.getRoom(),r.getDuration(),r.getBill(),r.getStatus()});
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
       if(e.getSource()==view.getFlogout()){
           loginPage.showPage(true);
           view.dispose();
           
       }
    }
    
    public JDialog getDialog(){
        return this.dialog;
    }
    
    public void updateTable(){
        renterList.clear();
        view.setTableModel(null);
        try {
            renterList = Helper_Renter.getListRenter();
            DefaultTableModel dtm = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            dtm.addColumn("ID");
            dtm.addColumn("Name");
            dtm.addColumn("Contact");
            dtm.addColumn("Room");
            dtm.addColumn("Duration");
            dtm.addColumn("Bill");
            dtm.addColumn("Status");
            
            for(Renter r : renterList){
                dtm.addRow(new Object[]{r.getId(),r.getNama(),r.getContact(),r.getRoom(),r.getDuration(),r.getBill(),r.getStatus()});
            }
            view.getFTabel().setModel(dtm);
        } catch (SQLException ex) {
            Logger.getLogger(Controller_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(ArrayIndexOutOfBoundsException aex){
            
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            System.out.println(view.getFTabel().getValueAt(view.getFTabel().getSelectedRow(), 6));
            if(!"PAID".equals(view.getFTabel().getValueAt(view.getFTabel().getSelectedRow(), 6).toString().toUpperCase())){
                try{
                    int choice = JOptionPane.showOptionDialog(null, "Sudah Dibayar?", "Konfirmasi",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if(choice == JOptionPane.YES_OPTION){
                        try {
                            Helper_Renter.updateRenter(Integer.parseInt(view.getFTabel().getValueAt(view.getFTabel().getSelectedRow(),0).toString()));
                            updateTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(Controller_Admin.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
                catch(Exception ex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,ex.getMessage());
                }
            }
            else{
                System.out.println("Sudah Dibayar");
                EditDeleteDialogController edc = null;
                try {
                    edc = new EditDeleteDialogController(this,Helper_Renter.getRenterById(Integer.parseInt(view.getFTabel().getValueAt(view.getFTabel().getSelectedRow(), 0).toString())));
                } catch (SQLException ex) {
                    Logger.getLogger(Controller_Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(edc!=null){
                    dialog = new JDialog(view.getWindow(), "Choose Action", true);
                    dialog.setContentPane(edc.getView().getContentPane());
                    dialog.pack();
                    dialog.setLocationRelativeTo(view.getWindow());
                    dialog.setVisible(true);
                    dialog.requestFocus();
                }
                
                
            }
        }
    }
    
    public class EditDeleteDialogController implements ActionListener{
        UbahData EDview;
        Renter renter;
        Controller_Admin parent;
        public EditDeleteDialogController(Controller_Admin p,Renter r){
            this.EDview = new UbahData();
            this.EDview.getFEdit().addActionListener(this);
            this.EDview.getFHapus().addActionListener(this);
            this.renter = r;
            this.parent=p;
        }
        
        public UbahData getView(){
            return this.EDview;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==EDview.getFHapus()){
                try{
                    Helper_Renter.deleteRenter(renter.getId());
                    Helper_Room.setEmptyRoom(renter.getRoom());
                    parent.updateTable();
                    parent.getDialog().dispose();
                }
                catch(SQLException ex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
                }
            }
            else{
                System.out.println(renter.getRoom());
                Contoller_DataRenter rdc = new Contoller_DataRenter(renter,parent);
                System.out.println("click here");
                parent.getDialog().dispose();
            }
        }
        
    }
}
