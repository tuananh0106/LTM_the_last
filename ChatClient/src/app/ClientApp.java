package app;


import controller.ControllerNavigator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class ClientApp {
    public static final ControllerNavigator navigator = new ControllerNavigator();
    public static void main(String[] args) {
        navigator.startApp();
    }
}
