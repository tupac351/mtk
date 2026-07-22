/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.quizzapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class JdbcConnector {
    private static JdbcConnector instance;
    private Connection conn;
    
    private JdbcConnector(){
        
    }
    public static JdbcConnector getInstance(){
        if (instance == null){
            instance = new JdbcConnector();
        }
        return instance;
    }
    public Connection connect() throws SQLException{
//            conn = DriverManager.getConnection("jdbc:sqlite:quizznam.db");
    conn = DriverManager.getConnection("jdbc:sqlite:D:/3.OU nam/ON_GK_DESIGN_PATTERN/namonthi/quizznam.db");

        return conn;  
    }
    
}
