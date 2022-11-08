/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.DTO.User;
import service.TCPService;
import view.LoginFrame;

/**
 *
 * @author ADMIN
 */
public class LoginRequester extends FrameController<LoginFrame>{

    public LoginRequester() {
        super(new LoginFrame());
    }

    @Override
    boolean run() {
        sendLoginRequest();
        User responseUser = receiveResponse();
        if(responseUser != null){
            mainController.client.setUser(responseUser);
            return true;
        }
        return false;
    }
    
    private void sendLoginRequest(){
        String username = frame.getUsernameField().getText();
        String password = new String(frame.getPasswordField().getPassword());
        
        User loginUser = new User(username, password);
        
        TCPService.send(mainController.client.getSocket(), loginUser);
    }
    
    private User receiveResponse(){
        return (User) TCPService.receive(mainController.client.getSocket());
    }
}
