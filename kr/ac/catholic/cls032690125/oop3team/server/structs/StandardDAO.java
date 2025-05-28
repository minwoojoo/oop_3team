package kr.ac.catholic.cls032690125.oop3team.server.structs;

import kr.ac.catholic.cls032690125.oop3team.server.Database;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

public abstract class StandardDAO {
    protected final Server server;
    protected final Database database;

    public StandardDAO(Server server) {
        this.server = server;
        database = server.getDatabase();
    }
}
