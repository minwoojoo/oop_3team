/*package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MessageProgram?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}*/