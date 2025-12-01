package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_NAME = "newbd1_23";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "rootuser";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
