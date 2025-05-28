package kr.ac.catholic.cls032690125.oop3team.features.auth.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class ClientLoginRequest extends ClientOrderBasePacket {
    private String username;
    private String password;

    public ClientLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
