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
public class MessageDAO {
//    public static List <Message> getAllMessagesOf(MessageBox messageBox){
//        List <Message> res = new ArrayList<>();
//        try (PreparedStatement preparedStatement = 
//                getConnection().prepareStatement(
//                        "SELECT * FROM message WHERE senderId = ? AND receiverId = ? ORDER BY time;")
//                ){
//            preparedStatement.setInt(1, messageBox.getSender().getId());
//            preparedStatement.setInt(2, messageBox.getReceiver().getId());
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()){
//                res.add(
//                        new Message(
//                                messageBox.getSender(),
//                                messageBox.getReceiver(),
//                                rs.getDate("time"),
//                                rs.getString("content")
//                        )
//                );
//            }
//            return res;
//        } catch (SQLException ex) {
//            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    public static List <Message> getAllMessagesBetween(User user1, User user2){
        List <Message> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM message"
                                + " WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?)"
                                + " ORDER BY time;")
                ){
            preparedStatement.setInt(1, user1.getId());
            preparedStatement.setInt(2, user2.getId());
            preparedStatement.setInt(3, user2.getId());
            preparedStatement.setInt(4, user1.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                boolean senderIsUser1 = rs.getInt("senderId") == user1.getId();
                res.add(
                        new Message(
                                senderIsUser1?user1:user2,
                                senderIsUser1?user2:user1,
                                rs.getTimestamp("time"),
                                rs.getString("content")
                        )
                );
            }
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static List <Message> getAllMessagesOf(Group group){
        List <Message> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT * FROM message WHERE receiverId = ? ORDER BY time;")
                ){
            preparedStatement.setInt(1,group.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                res.add(
                        new Message(
                                new User(rs.getInt("senderId")),
                                group,
                                rs.getTimestamp("time"),
                                rs.getString("content")
                        )
                );
            }
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static int insertMessage(Message message){
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "INSERT INTO message (senderId, receiverId, time, content) VALUES (?, ?, ?, ?);")
                ){
            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setTimestamp(3, message.getTime());
            preparedStatement.setString(4, message.getContent());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;        
    }
}
