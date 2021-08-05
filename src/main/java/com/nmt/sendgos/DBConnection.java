/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nmt.sendgos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author user2
 */
public class DBConnection {
   
 

    static Connection getDBConnection(Properties properties) throws SQLException, ClassNotFoundException {
      
       Class.forName("org.firebirdsql.jdbc.FBDriver");
       return DriverManager.getConnection("jdbc:firebirdsql:"+properties.getProperty("DB_PATH"),properties.getProperty("DB_LOGIN"),properties.getProperty("DB_PASSWORD"));
    }

}