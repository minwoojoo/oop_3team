package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.List;

public class SChatroomMemberListPacket extends ServerResponseBasePacket {
    private int chatroomId;
    private List<String> members;

    public SChatroomMemberListPacket(int chatroomId, List<String> members) {
        this.chatroomId = chatroomId;
        this.members = members;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
