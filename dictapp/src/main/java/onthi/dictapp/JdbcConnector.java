/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onthi.dictapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    private static JdbcConnector instance;
    private static final String DB_URL = "jdbc:sqlite:dictionary.db";

    private JdbcConnector() {
        try {
            // Nạp driver SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized JdbcConnector getInstance() {
        if (instance == null) {
            instance = new JdbcConnector();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}