package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.util.ArrayList;

public class CChatroomCreatePacket extends ClientOrderBasePacket {
    private String title;
    private String ownerId;
    private ArrayList<String> participants;
    private Integer parentRoomId;
    private boolean isPrivate;


    public CChatroomCreatePacket(String title, String ownerId, ArrayList<String> participants, Integer parentRoomId,
                                 boolean isPrivate) {
        super();
        this.title = title;
        this.ownerId = ownerId;
        this.participants = participants;
        this.parentRoomId = parentRoomId;
        this.isPrivate = isPrivate;
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

    public Integer getParentRoomId(){
        return parentRoomId;
    }

    public boolean getIsPrivate() {
        return this.isPrivate;
    }
}
