package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.util.ArrayList;

public class CChatroomInvitePacket extends ClientOrderBasePacket {
    private int chatroomId;
    private ArrayList<String> participants;

    public CChatroomInvitePacket(int chatroomId, ArrayList<String> participants) {
        super();
        this.participants = participants;
        this.chatroomId = chatroomId;
    }

    public int getChatroomId() { return chatroomId; }
    public ArrayList<String> getParticipants() { return participants; }
}
