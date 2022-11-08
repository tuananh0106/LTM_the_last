/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.ServerApp;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import model.Client;
import model.DTO.User;
import service.UserService;
import view.ServerMainFrame;

/**
 *
 * @author ADMIN
 */
public class MainServerController {
    ServerMainFrame frame;
    ServerRunner server;
    ClientListener messageReceiver;
    Map <Integer, Client> connectedClients;

    public MainServerController() {
    }
    
    public void initComponents(){
        frame = new ServerMainFrame();
        server = new ServerRunner();
        connectedClients = new HashMap<>();
    }
    public void startApp(){
        initComponents();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });
    }
    
    public void startServer(){
        server = new ServerRunner();
        server.setUpPort();
        server.start();
        frame.getPortField().setText("" + server.port);
        frame.getPowerButton().setText("Stop");
        frame.getConnectedTable().setEnabled(true);
        System.out.println("Start server.");
    }
    
    public void stopServer(){
        try {
            server.socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        connectedClients.clear();
        updateConnectedTable();
        frame.getPowerButton().setText("Start");
        frame.getConnectedTable().setEnabled(false);   
        System.out.println("Stop server.");     
    }
    
    public void addConnectedClient(Client client){
        connectedClients.put(client.getSocket().getPort(), client);
        updateConnectedTable();
    }
    public void updateConnectedTable(){
        DefaultTableModel dtm = (DefaultTableModel) frame.getConnectedTable().getModel();
        dtm.getDataVector().removeAllElements();
        for(int p : connectedClients.keySet()){
            Client client = connectedClients.get(p);
            String ip = client.getSocket().getInetAddress().getHostAddress();
            int port = client.getSocket().getPort();
            User user = client.getUser();
            String username = user == null?"":user.getUsername();
            String name = user == null?"":user.getName();            
            dtm.addRow(
                    new Object[]{
                        ip,
                        port,
                        username,
                        name
                    }
            );
        }
    }    
}