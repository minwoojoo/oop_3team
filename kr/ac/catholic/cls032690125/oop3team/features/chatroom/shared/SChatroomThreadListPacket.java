package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.ArrayList;

public class SChatroomThreadListPacket extends ServerResponseBasePacket {
    private ArrayList<Chatroom> threads;

    public SChatroomThreadListPacket(ArrayList<Chatroom> thread) {
        this.threads = thread;
    }

    public ArrayList<Chatroom> getThread() {
        return this.threads;
    }

}
