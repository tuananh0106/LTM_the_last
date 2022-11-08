/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import app.ClientApp;
import javax.swing.JFrame;

/**
 *
 * @author ADMIN
 */
public abstract class FrameController <F>{
    protected final F frame;
    final ControllerNavigator mainController = ClientApp.navigator;
    
    public FrameController(F frame){
        this.frame = frame;
    }
    
    void showFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ((JFrame)frame).setVisible(true);
            }
        });
    }
    
    void hideFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ((JFrame)frame).setVisible(false);
            }
        });
    }
    abstract boolean run();
}
