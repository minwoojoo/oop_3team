package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;
import java.util.List;

public class SBlockListRes extends ServerResponseBasePacket {
    private final List<String> blockedNames;

    public SBlockListRes(long requestId, List<String> blockedNames) {
        super(requestId);
        this.blockedNames = blockedNames;
    }

    public List<String> getBlockedNames() {
        return blockedNames;
    }
} 