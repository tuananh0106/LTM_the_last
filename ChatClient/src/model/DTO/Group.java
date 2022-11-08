/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Group extends MessageAddress implements Serializable{
    private static final long serialVersionUID = 12L;
    private List <User> users;
    
    public Group(int id){
        this.id = id;
    }

    public Group(String name) {
        this.name = name;
    }
    
    public Group(int id, String name){
        this.id = id;
        this.name = name;
    }
}
