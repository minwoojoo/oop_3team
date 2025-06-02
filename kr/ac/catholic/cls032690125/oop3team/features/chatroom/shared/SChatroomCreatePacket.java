package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

/**
 * 서버가 방 생성 요청(CChatroomCreatePacket)에 대한 응답으로 보내는 패킷
 *  - status: OK/FAIL
 *  - room: 생성된 Chatroom (status == OK 일 때만 유효)
 *  - errorMsg: 실패 시 에러 메시지
 */
public class SChatroomCreatePacket extends ServerResponseBasePacket {
    private Chatroom       room;
    private String         errorMsg;

    public SChatroomCreatePacket(long requestId, Chatroom room, String errorMsg) {
        super(requestId);
        this.room     = room;
        this.errorMsg = errorMsg;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
