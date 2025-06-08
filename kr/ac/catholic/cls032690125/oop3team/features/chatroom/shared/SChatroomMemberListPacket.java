package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.ArrayList;

public class SChatroomMemberListPacket extends ServerResponseBasePacket {
    private int chatroomId;
    // List는 Serializeable하지 않습니다.
    private ArrayList<String> members;
    private ArrayList<String> memberNames;

    public SChatroomMemberListPacket(long requestId, int chatroomId, ArrayList<String> members, ArrayList<String> memberNames) {
        super(requestId);
        this.chatroomId = chatroomId;
        this.members = members;
        this.memberNames = memberNames;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(ArrayList<String> memberNames) {
        this.memberNames = memberNames;
    }
}
