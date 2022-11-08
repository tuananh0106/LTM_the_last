/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDAO;
import dao.MessageAddressDAO;
import java.util.List;
import model.DTO.Group;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class GroupService {
    public static Group getGroupById(int id){
        return GroupDAO.getGroupById(id);
    }
    public static List <Group> getAllGroupsOf(User user){
        return GroupDAO.getAllGroupsOf(user);
    }
    public static Group create(Group group){
        return GroupDAO.insertGroup(group);
    }
}
