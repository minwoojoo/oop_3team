package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.ArrayList;

public class SChatroomThreadListPacket extends ServerResponseBasePacket {
    private final ArrayList<Chatroom> threads;
    private final String errorMessage;

    public SChatroomThreadListPacket(long requestId, ArrayList<Chatroom> threads, String errorMessage) {
        super(requestId);
        this.threads = threads;
        this.errorMessage = errorMessage;
    }

    public ArrayList<Chatroom> getThreads() {
        return threads;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return errorMessage == null;
    }
}
