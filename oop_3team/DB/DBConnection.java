package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
 private static final String URL = "jdbc:mysql://localhost:3306/schedule_db";
 private static final String USER = "root";
 private static final String PASSWORD = "Vireak2002!!";

 static {
     try {
         Class.forName("com.mysql.cj.jdbc.Driver"); // load JDBC driver
     } catch (ClassNotFoundException e) {
         e.printStackTrace();
     }
 }

 public static Connection getConnection() throws SQLException {
     return DriverManager.getConnection(URL, USER, PASSWORD);
 }
}
