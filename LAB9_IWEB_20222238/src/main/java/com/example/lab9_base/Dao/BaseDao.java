package com.example.lab9_base.Dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    public Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String user = "root";
        String password = "root0706";
        String url = "jdbc:mysql://localhost:3306/lab9?serverTimezone=America/Lima";
        return DriverManager.getConnection(url, user, password);
    }
}
