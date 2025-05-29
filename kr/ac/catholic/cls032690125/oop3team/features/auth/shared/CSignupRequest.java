package kr.ac.catholic.cls032690125.oop3team.features.auth.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CSignupRequest extends ClientOrderBasePacket {
    private String userid;
    private String password;
    private String username;

    public CSignupRequest(String userid, String password, String username) {
        this.userid = userid;
        this.password = password;
        this.username = username;
    }

    public String getUserid() { return userid; }
    public String getPassword() { return password; }
    public String getUsername() { return username; }
}
