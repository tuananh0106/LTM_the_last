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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DTO.MessageAddress;
import model.DTO.User;

/**
 *
 * @author ADMIN
 */
public class MessageAddressDAO extends BaseDAO{
    public static String getNameOfMessageAddressById(int id){
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "SELECT name FROM message_address WHERE id = ?;")
                ){
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return rs.getString("name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static int insertMessageAddress(MessageAddress messageAddress){
        try (PreparedStatement preparedStatement = 
                getConnection().prepareStatement(
                        "INSERT INTO message_address (name) VALUES (?);",
                        Statement.RETURN_GENERATED_KEYS
                )
        ){
            preparedStatement.setString(1, messageAddress.getName());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
