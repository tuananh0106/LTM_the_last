/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import javax.swing.JOptionPane;
import view.ConnectToServerFrame;

/**
 *
 * @author ADMIN
 */
public class ServerConnector extends FrameController <ConnectToServerFrame>{

    public ServerConnector() {
        super(new ConnectToServerFrame());
    }
    
    @Override
    public boolean run() {
        try{
            String ip = frame.getIpField().getText();
            int port = Integer.parseInt(frame.getPortField().getText());
            mainController.client.setSocket(new Socket(ip, port));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException | NullPointerException ex){
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        } finally {
            if (mainController.client.getSocket() == null){
                JOptionPane.showMessageDialog(frame, "Connection failed.");
            } else{
                return true;
            }
            return false;
        }
    }
}
