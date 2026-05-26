package com.lms.utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {

        try {
            DatabaseConfig config = DatabaseConfig.load();
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
        } catch (ClassNotFoundException | SQLException exception){
            throw new IllegalStateException("Unable to connect to database: " + exception.getMessage(), exception);
        }
    }
}
