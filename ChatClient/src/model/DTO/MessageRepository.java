/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DTO;

import model.DTO.MessageBox;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author ADMIN
 */
public class MessageRepository implements Serializable{
    private static final long serialVersionUID = 4L;
    private Map <Integer, MessageBox> messageBoxs;
    
    public MessageRepository(List <MessageBox> driectBoxs, List <MessageBox> groupBoxs){
        this.messageBoxs = new HashMap<>();
        for (MessageBox mb:driectBoxs){
            this.messageBoxs.put(mb.getReceiver().getId(), mb);
        }
        for (MessageBox mb:groupBoxs){
            this.messageBoxs.put(mb.getReceiver().getId(), mb);
        }
    }

    public Map<Integer, MessageBox> getMessageBoxs() {
        return messageBoxs;
    }

    public void setMessageBoxs(Map<Integer, MessageBox> messageBoxs) {
        this.messageBoxs = messageBoxs;
    }
    
}
