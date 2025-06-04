package kr.ac.catholic.cls032690125.oop3team.features.chat.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;


public class SMessageLoadPacket extends ServerResponseBasePacket {
    private Message[] messages;

    public SMessageLoadPacket(long requestId, Message[] messages) {
        super(requestId);
        this.messages = messages;
    }

    public Message[] getMessages() {
        return messages;
    }
}
