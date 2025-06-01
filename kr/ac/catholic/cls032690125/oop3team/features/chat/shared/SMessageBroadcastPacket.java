package kr.ac.catholic.cls032690125.oop3team.features.chat.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SMessageBroadcastPacket extends ServerResponseBasePacket {
    private Message message;

    public SMessageBroadcastPacket(Message message) {
        super();
        this.message = message;
    }

    public Message getMessage() { return message; }
}
