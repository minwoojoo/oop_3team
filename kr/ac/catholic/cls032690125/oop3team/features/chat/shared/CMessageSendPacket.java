package kr.ac.catholic.cls032690125.oop3team.features.chat.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CMessageSendPacket extends ClientOrderBasePacket {
    private Message message;

    public CMessageSendPacket(Message message) {
        super();
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
