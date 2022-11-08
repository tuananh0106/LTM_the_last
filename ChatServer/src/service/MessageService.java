/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDAO;
import dao.MessageBoxDAO;
import dao.MessageDAO;
import dao.UserDAO;
import java.util.ArrayList;
import java.util.List;
import model.DTO.Group;
import model.DTO.Message;
import model.DTO.MessageAddress;
import model.DTO.MessageBox;
import model.DTO.MessageRepository;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class MessageService {
    public static MessageRepository getMessageRepositoryOf(User user) {
        MessageRepository res
                = new MessageRepository(
                        MessageBoxService.getAllDriectMessageBoxsOf(user),
                        MessageBoxService.getAllGroupMessageBoxsOf(user)
                );
        return res;
    }
    
    public static List <Message> getAllMessagesOf(Group group){
        List <Message> res = MessageDAO.getAllMessagesOf(group);
        for(Message m:res){
            m.setSender(UserService.getUserById(m.getSender().getId()));
        }
        return res;
    }
    
    public static int saveMessage(Message message){
        return MessageDAO.insertMessage(message);
    }
}
