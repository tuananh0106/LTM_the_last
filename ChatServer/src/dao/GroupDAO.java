/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DTO.Group;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class GroupDAO extends MessageAddressDAO{
    public static List<Group> getAllGroupsOf(User user){
        List<Group> res = new ArrayList<>();
        try(PreparedStatement preparedStatement = 
               getConnection().prepareStatement(
                       "SELECT id FROM `group` WHERE id IN"
                       + " (SELECT receiverId FROM message_box WHERE senderId = ?);")
                )
        {
            preparedStatement.setInt(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                res.add(new Group(id, getNameOfMessageAddressById(id)));
            }
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static Group getGroupById(int id){
        Group res = null;
        try(PreparedStatement preparedStatement = 
               getConnection().prepareStatement("SELECT * FROM `group` WHERE id = ?;"))
        {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                res = new Group(id, getNameOfMessageAddressById(id));
            }
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static Group insertGroup(Group group){
        int id = insertMessageAddress(group);
        if (id != 0){
            try (PreparedStatement preparedStatement = 
                    getConnection().prepareStatement("INSERT INTO `group` VALUES (?);")
            ){
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() > 0){
                    return getGroupById(id);
                }
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}
