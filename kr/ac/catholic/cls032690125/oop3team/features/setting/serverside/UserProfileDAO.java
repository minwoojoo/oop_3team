package kr.ac.catholic.cls032690125.oop3team.features.setting.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserProfileDAO extends StandardDAO {
    public UserProfileDAO(Server server) {
        super(server);
    }

    public void updateUserProfile(String userId, String displayName, String statusMessage) throws Exception {
        Connection connection = database.getConnection();
        String sql = """
            INSERT INTO user_profile (user_id, display_name, status_message)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE display_name = ?, status_message = ?
        """;
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userId);
        ps.setString(2, displayName);
        ps.setString(3, statusMessage);
        ps.setString(4, displayName); // for update
        ps.setString(5, statusMessage); // for update
        ps.executeUpdate();
        ps.close();
        connection.close();

    }
}
