/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.BaseDAO.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DTO.Group;
import model.DTO.Message;
import model.DTO.MessageAddress;
import model.DTO.MessageBox;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class MessageBoxDAO {    
    public static MessageBox getMessageBoxBetween(User sender, MessageAddress receiver){
        MessageBox res = null;
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM message_box WHERE senderId = ? AND receiverId = ?;")
                ){
            preparedStatement.setInt(1, sender.getId());
            preparedStatement.setInt(2, receiver.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                res = new MessageBox(sender, receiver, rs.getBoolean("seen"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageBoxDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public static List <MessageBox> getAllDirectMessageBoxsOf(User user){
        List <MessageBox> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM message_box WHERE"
                                + " senderId = ? AND"
                                + " receiverId NOT IN"
                                + " (SELECT id FROM `group`);")
                ){
            preparedStatement.setInt(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                res.add(new MessageBox(user, new User(rs.getInt("receiverId")), rs.getBoolean("seen")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageBoxDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public static List <MessageBox> getAllGroupMessageBoxsOf(User user){
        List <MessageBox> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM message_box WHERE"
                                + " senderId = ? AND"
                                + " receiverId IN"
                                + " (SELECT id FROM `group`);")
                ){
            preparedStatement.setInt(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                res.add(new MessageBox(user, new Group(rs.getInt("receiverId")), rs.getBoolean("seen")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageBoxDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static int insertMessageBox(MessageBox messageBox){
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "INSERT INTO message_box (senderId, receiverId, seen) VALUES (?, ?, ?);")
                ){
            preparedStatement.setInt(1, messageBox.getSender().getId());
            preparedStatement.setInt(2, messageBox.getReceiver().getId());
            preparedStatement.setBoolean(3, false);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
