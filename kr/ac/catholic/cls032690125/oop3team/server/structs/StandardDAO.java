package kr.ac.catholic.cls032690125.oop3team.server.structs;

import kr.ac.catholic.cls032690125.oop3team.server.Database;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

/**
 * 서버 측 데이터베이스 처리를 위한 기본 클래스
 */
public abstract class StandardDAO {
    protected final Server server;
    protected final Database database;

    public StandardDAO(Server server) {
        this.server = server;
        database = server.getDatabase();
    }
}
