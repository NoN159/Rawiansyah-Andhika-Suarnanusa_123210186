/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import almaul.models.Account;
import almaul.views.Login;
import almaul.helper.Helper_Account;

/**
 *
 * @author NoName
 */
public class Controller_Login implements ActionListener {
    
    Login view;
    public Controller_Login(){
        view = new Login();
        view.getBlogin().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.getBlogin()){
            
            Account acc = new Account();
            acc.setUsername(view.getFusername().getText());
            acc.setPassword(view.getFpassword().getText());
            try{
                Helper_Account.LoginAccount(acc);
                if(acc.getRole()==null){
                    System.out.println("Akun tidak tersedia");
                }
                else if("ADMIN".equals(acc.getRole().toUpperCase())){
                    Controller_Admin admin = new Controller_Admin(this);
                    showPage(false);
                }
                else{
                    Controller_RoomList renter = new Controller_RoomList(this);
                    showPage(false);
                }
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void showPage(boolean stat){
        view.setVisible(stat);
    }
    
}
