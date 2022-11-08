/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import app.CONFIG;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public abstract class BaseDAO {
    protected static Connection connection;
    
    public static Connection getConnection() {
        try {
            Class.forName(CONFIG.DATABASE.CLASSNAME);
            if (connection == null)
                connection = DriverManager.getConnection(
                        CONFIG.DATABASE.URL, 
                        CONFIG.DATABASE.USERNAME, 
                        CONFIG.DATABASE.PASSWORD
                );
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
