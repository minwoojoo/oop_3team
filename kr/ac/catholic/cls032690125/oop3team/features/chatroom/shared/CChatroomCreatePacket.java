package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.util.ArrayList;

public class CChatroomCreatePacket extends ClientOrderBasePacket {
    private String title;
    private String ownerId;
    private ArrayList<String> participants;

    public CChatroomCreatePacket(String title, String ownerId, ArrayList<String> participants) {
        this.title = title;
        this.ownerId = ownerId;
        this.participants = participants;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
