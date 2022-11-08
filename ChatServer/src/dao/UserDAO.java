/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class UserDAO extends MessageAddressDAO{
    public static User getUserByUsername(String username){
        User res = null;
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM user WHERE username = ?;")
                ){
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                res = new User(
                        id,
                        rs.getString("username"),
                        rs.getString("password"),
                        getNameOfMessageAddressById(id)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
    public static User getUserById(int id){
        User res = null;
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM user WHERE id = ?;")
                ){
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                res = new User(
                        id,
                        rs.getString("username"),
                        rs.getString("password"),
                        getNameOfMessageAddressById(id)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }
    public static User insertUser(User user){
        int id = insertMessageAddress(user);
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "INSERT INTO user (id, username, password) VALUES (?, ?, ?);"
                )
            ){
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            if (preparedStatement.executeUpdate() > 0){
                return getUserById(id);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static List <User> getAllMembersOf(Group group){
        List <User> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM user WHERE id IN (SELECT senderId FROM message_box WHERE receiverId = ?);")
                ){
            preparedStatement.setInt(1, group.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int userId = rs.getInt("id");
                res.add(
                        new User(
                                userId,
                                rs.getString("username"),
                                getNameOfMessageAddressById(userId)
                        )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;     
    }
    
    public static List <User> getAllFriendsOf(User user){
        List <User> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM user WHERE id <> ? AND id IN (SELECT receiverId FROM message_box WHERE senderId = ?);")
                ){
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int userId = rs.getInt("id");
                res.add(
                        new User(
                                userId,
                                rs.getString("username"),
                                getNameOfMessageAddressById(userId)
                        )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;     
    }
    
    public static List <User> getAllUsersWhoAreNotFriendsOf(User user){
        List <User> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM user WHERE id <> ? AND id NOT IN (SELECT receiverId FROM message_box WHERE senderId = ?);")
                ){
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int userId = rs.getInt("id");
                res.add(
                        new User(
                                userId,
                                rs.getString("username"),
                                getNameOfMessageAddressById(userId)
                        )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;     
    }
}
