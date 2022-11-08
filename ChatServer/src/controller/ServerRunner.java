/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.ServerApp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class ServerRunner extends Thread{
    ServerSocket socket;
    int port;
    
    @Override
    public void run() {
        runServer();
        responseConnections();
    }
    
    public void setUpPort(){
        try{
            port = Integer.parseInt(ServerApp.mainController.frame.getPortField().getText());
        } catch (NumberFormatException e){
            port = 10000; 
        }
    }
    
    public void runServer(){
        try {
            socket = new ServerSocket(port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void responseConnections(){
        while(true){
            try {
                if (socket.isClosed()){
                    return;
                }
                Socket clientSocket = socket.accept();
                if (socket != null){
                    Client client = new Client(clientSocket);
                    ServerApp.mainController.addConnectedClient(client);
                    new ClientListener(client).start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }           
        }
    }    
}
