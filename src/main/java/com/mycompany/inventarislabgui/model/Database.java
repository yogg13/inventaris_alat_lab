/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventarislabgui.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author muhammadyoga
 */
public class Database {
    private Database() {}
    
    private static final String URL = "jdbc:mysql://localhost:3309/db_inventaris_lab" + 
                "?sslMode=DISABLED&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "@MYqep_1913!";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
