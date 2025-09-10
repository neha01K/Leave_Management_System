package com.lms.utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/leave_management_system_db";
    private static final String username = "root";
    private static final String password = "Hitmanbau1*@sql";

    public static Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }

        return connection;
    }
}
