/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import app.CONFIG;
import static dao.BaseDAO.getConnection;
import dao.MessageAddressDAO;
import dao.UserDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.DTO.Group;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class UserService {    
    public static User loginValidate(User loginUser){
        User expected = UserDAO.getUserByUsername(loginUser.getUsername());
        if (expected != null && loginUser.getPassword().equals(expected.getPassword())){
            return expected;
        }
        return null;
    }
    
    public static User create(User user){
        return UserDAO.insertUser(user);
    }
    
    public static User getUserById(int id){
        return UserDAO.getUserById(id);
    }
    
    public static List <User> getAllMembersOf(Group group){
        return UserDAO.getAllMembersOf(group);
    }
    
    public static List <User> getAllUsersWhoAreNotFriendsOf(User user){
        return UserDAO.getAllUsersWhoAreNotFriendsOf(user);
    }
    
    public static List <User> getAllUsersWhoAreNotFriendsOf(User user, String inName){
        List <User> res = new ArrayList<>();
        for (User u:UserDAO.getAllUsersWhoAreNotFriendsOf(user)){
            if (u.getUsername().contains(inName) || u.getName().contains(inName)){
                res.add(u);
            }
        }
        return res;
    }
    
    public static List <User> getAllFriendsOf(User user){
        return UserDAO.getAllFriendsOf(user);
    }
    
    public static List <User> getAllFriendsOf(User user, String inName){
        List <User> res = new ArrayList<>();
        for (User u:UserDAO.getAllFriendsOf(user)){
            if (u.getUsername().contains(inName) || u.getName().contains(inName)){
                res.add(u);
            }
        }
        return res;
    }
}
