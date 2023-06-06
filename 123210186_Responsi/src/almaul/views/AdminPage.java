/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almaul.views;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author NoName
 */
public class AdminPage { 
    JFrame window = new JFrame("Admin Page View");
    Object columnName [];

    public JFrame getWindow() {
        return window;
    }
    
    String data[][] = new String [100][4];
    DefaultTableModel tableModel = new DefaultTableModel(columnName,0);
    JTable ftabel = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(ftabel);
    
    JButton flogout = new JButton("Logout");

    public AdminPage() {
        window.setLayout(null);
        window.setSize(550,600);
       
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        window.add(scrollPane);
        window.add(flogout);
        scrollPane.setBounds(20, 35, 500, 300);
        flogout.setBounds(20, 350, 100,50);
        showData();
        
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getFTabel() {
        return ftabel;
    }

    public JButton getFlogout() {
        return flogout;
    }
    
    private void showData(){
        
    }
    public void dispose(){
        window.dispose();
    }
    
}
