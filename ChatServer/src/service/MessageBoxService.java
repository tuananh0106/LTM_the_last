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
import model.DTO.MessageBox;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class MessageBoxService {
    public static List<MessageBox> getAllDriectMessageBoxsOf(User user) {
        List<MessageBox> res = MessageBoxDAO.getAllDirectMessageBoxsOf(user);
        for (MessageBox mb:res) {
            mb.setReceiver(UserService.getUserById(mb.getReceiver().getId()));
            mb.setMessages(MessageDAO.getAllMessagesBetween(mb.getSender(), (User) mb.getReceiver()));
        }
        return res;
    }

    public static List<MessageBox> getAllGroupMessageBoxsOf(User user) {
        List<MessageBox> res = MessageBoxDAO.getAllGroupMessageBoxsOf(user);
        for (MessageBox mb:res) {
            mb.setReceiver(GroupService.getGroupById(mb.getReceiver().getId()));
            mb.setMessages(MessageService.getAllMessagesOf((Group) mb.getReceiver()));
        }
        return res;
    }
    
    public static int create(MessageBox messageBox){
        return MessageBoxDAO.insertMessageBox(messageBox);
    }
    
    public static int create(List <User> members, Group group){
        int cnt = 0;
        for (User u:members){
            cnt += create(new MessageBox(u, group, false));
        }
        return cnt;
    }
}
