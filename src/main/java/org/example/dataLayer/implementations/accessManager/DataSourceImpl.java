package org.example.dataLayer.implementations.accessManager;

import org.example.dataLayer.interfaces.accessManager.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceImpl{

    private static final String URL = "jdbc:mysql://localhost:3306/QoS_CHAIN";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
