package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SUserNameGetRes extends ServerResponseBasePacket {
    private final String name;

    public SUserNameGetRes(long requestId, String name) {
        super(requestId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
} 