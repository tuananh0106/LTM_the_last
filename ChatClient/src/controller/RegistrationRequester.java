/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.ClientApp;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.DTO.User;
import service.TCPService;
import view.RegisterDialog;

/**
 *
 * @author ADMIN
 */
public class RegistrationRequester{
    RegisterDialog registerDialog;

    public void showFrame(){
        registerDialog = new RegisterDialog(ClientApp.navigator.loginRequester.frame);
        showDialog();
    }
    private void showDialog() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegisterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                registerDialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        
//                    }
//                });
                registerDialog.setVisible(true);
            }
        });
    }
    
    public void run(){
        sendRequest();
        JOptionPane.showMessageDialog(registerDialog, receiveResponse());
    }
    private void sendRequest(){
        User user = new User(
                registerDialog.getUsernameField().getText(),
                registerDialog.getPasswordField().getText(),
                registerDialog.getNameField().getText()
        );
        TCPService.send(ClientApp.navigator.client.getSocket(), user);
    }
    
    private String receiveResponse(){
        return (String) TCPService.receive(ClientApp.navigator.client.getSocket());
    }
}
