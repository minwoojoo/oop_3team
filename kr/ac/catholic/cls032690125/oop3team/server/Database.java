package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final Server server;


    public Database(Server server) throws ClassNotFoundException {
        this.server = server;
        //Class.forName(server.getProperties().getServerDBDriver());
    }

    public Connection getConnection() {
        try {
            ProgramProperties prop = server.getProperties();
            return DriverManager.getConnection(
                    prop.getServerDBPath() + ";databaseName=" + prop.getServerDBName(),
                    prop.getServerDBID(),
                    prop.getServerDBPassword()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
