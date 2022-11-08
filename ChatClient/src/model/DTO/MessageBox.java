/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DTO;

import model.DTO.MessageAddress;
import model.DTO.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class MessageBox implements Serializable{
    private static final long serialVersionUID = 3L;
    private List <Message> messages;
    private User sender;
    private MessageAddress receiver;
    private boolean seen;

    public MessageBox(User sender, MessageAddress receiver, boolean seen) {
        this.sender = sender;
        this.receiver = receiver;
        this.seen = seen;
        this.messages = new ArrayList<>();
    }
    
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
    
}
