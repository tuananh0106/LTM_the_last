/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import model.Client;
import view.ConnectToServerFrame;
import view.LoginFrame;

/**
 *
 * @author ADMIN
 */
public class ControllerNavigator{
    Client client;
    ServerConnector serverConnector;
    LoginRequester loginRequester;
    RegistrationRequester registrationRequester;
    MainFrameManager mainFrameManager;
    AddFriendRequester addFriendRequester;
    NewGroupRequester newGroupRequester;
    InteractiveThread interactiveThread;
    
    public void initComponents(){
        client = new Client();
        serverConnector = new ServerConnector();
        loginRequester = new LoginRequester();
        registrationRequester = new RegistrationRequester();
        mainFrameManager = new MainFrameManager();
        addFriendRequester = new AddFriendRequester();
        newGroupRequester = new NewGroupRequester();
    }
    public void startApp(){
        initComponents();
        serverConnector.showFrame();
    }
    
    public void connectToServer(){
        if (serverConnector.run()){
            serverConnector.hideFrame();
            loginRequester.showFrame();
        }
    }
    
    public void tryLogin(){
        if (loginRequester.run()){
            loginRequester.hideFrame();
            interactiveThread = new InteractiveThread();
            interactiveThread.start();
            mainFrameManager.run();
        }
    }
    
    public void showRegisterFrame(){
        registrationRequester.showFrame();
    }
    
    public void submitRegistration(){
        registrationRequester.run();
    }
    
    public void showMessageHistoryOfSelectedRow(){
        mainFrameManager.updateMessageTable();
    }
    
    public void sendMessage(){
        mainFrameManager.sendMessage();
    }
    
    public void logOut(){
        mainFrameManager.logOut();
    }
    
    public void showAddFriendFrame(){
        addFriendRequester.run();
    }
    
    public void sendSearchUserRequest(){
        addFriendRequester.sendSearchRequest();
    }
    
    public void sendAddFriendRequest(){
        addFriendRequester.sendAddFriendRequest();
    }
    
    public void showNewGroupFrame(){
        newGroupRequester.run();
    }
    public void sendNewGroupRequest(){
        newGroupRequester.sendNewGroupRequest();
    }
}
