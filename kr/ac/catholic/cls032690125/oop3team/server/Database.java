package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 데이터베이스 클래스
 */
public class Database {
    private final Server server;

    /**
     * 데이터베이스 생성
     *
     * @implSpec 실행 시점에 프로그램이 사용하는 데이터베이스 드라이버를 지정합니다.
     * @param server 서버 클래스
     * @throws ClassNotFoundException 환경변수에 지정된 데이터베이스 드라이버가 유효하지 않을 때
     */
    public Database(Server server) throws ClassNotFoundException {
        this.server = server;
        Class.forName(server.getProperties().getServerDBDriver());
    }

    /**
     * ConnectionPool에서 데이터베이스 연결을 만듭니다.
     *
     * @return 데이터베이스 연결 객체, 연결 실패 시 null을 반환
     */
    public Connection getConnection() {
        try {
            ProgramProperties prop = server.getProperties();
            return DriverManager.getConnection(
                    prop.getServerDBPath() + "/" + prop.getServerDBName(),
                    prop.getServerDBID(),
                    prop.getServerDBPassword()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
