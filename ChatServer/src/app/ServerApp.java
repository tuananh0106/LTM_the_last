package app;


import controller.MainServerController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class ServerApp {
    public static final MainServerController mainController = new MainServerController(); 
    public static void main(String[] args) {
        mainController.initComponents();
        mainController.startApp();
    }
}
