/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DTO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 2L;
    private int id;
    private User sender;
    private MessageAddress receiver;
    private Timestamp time;
    private String content;

    public Message(User sender, MessageAddress receiver, Timestamp time, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
    }

    public Message(User sender, Timestamp time, String content) {
        this.sender = sender;
        this.time = time;
        this.content = content;
    }    
    
    public Message(Timestamp time, String content) {
        this.time = time;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public MessageAddress getReceiver() {
        return receiver;
    }

    public void setReceiver(MessageAddress receiver) {
        this.receiver = receiver;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
