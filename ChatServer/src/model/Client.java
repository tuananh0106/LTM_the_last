/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.DTO.User;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author ADMIN
 */
public class Client {
    private User user;
    private Socket socket;
    private int clientDatagramSocketPort;

    public Client(){}
    
    public Client(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
    }

    public Client(Socket socket) {
        this.socket = socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public int getClientDatagramSocketPort() {
        return clientDatagramSocketPort;
    }

    public void setClientDatagramSocketPort(int clientDatagramSocketPort) {
        this.clientDatagramSocketPort = clientDatagramSocketPort;
    }
}
