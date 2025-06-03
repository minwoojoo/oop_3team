package kr.ac.catholic.cls032690125.oop3team.features.chatroom.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

import java.sql.SQLException;

public class SChatroomController extends ServerRequestListener {
    private final ChatroomDAO chatroomDAO;

    public SChatroomController(Server client) {
        super(client);
        this.chatroomDAO = new ChatroomDAO(client);
    }

    @ServerRequestHandler(CChatroomMemberListPacket.class)
    public void loadMemberList(ServerClientHandler sch, CChatroomMemberListPacket packet) {
    }

    @ServerRequestHandler(CChatroomListLoadPacket.class)
    public void loadChatroomList(ServerClientHandler sch, CChatroomListLoadPacket packet) {
        try {
            Chatroom[] rooms = chatroomDAO.loadAllChatrooms(packet.isPrivate());
            sch.send(new SChatroomListPacket(
                    packet.getRequestId(),
                    rooms,
                    null
            ));
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new SChatroomListPacket(
                    packet.getRequestId(),
                    null,
                    "방 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage()
            ));
        }
    }

    /**
     * 특정 채팅방의 맴버 목록을 불러옵니다.
     *
     * @param chatroomId 채팅방 id
     * @return 채팅방 맴버의 user id
     */
    public String[] getMemberList(int chatroomId) {
        //TODO: IMPL IT
        return null;
    }


    /**
     * 1) CChatroomCreatePacket을 받으면 호출됩니다.
     *    - 패킷에는 ‘title’과 ‘ownerId’가 들어 있습니다.
     * 2) DAO를 통해 DB에 INSERT → 생성된 Chatroom 객체를 리턴받습니다.
     * 3) 성공 여부에 따라 SChatroomCreatePacket으로 결과를 클라이언트에 보냅니다.
     */
    public void createChatroom(ServerClientHandler sch, CChatroomCreatePacket packet) {
        String title   = packet.getTitle();
        String ownerId = packet.getOwnerId();
        try {
            Chatroom newRoom = chatroomDAO.createChatroom(title, ownerId);
            if (newRoom == null) {
                // DB 삽입 실패
                sch.send(new SChatroomCreatePacket(
                        packet.getRequestId(),
                        null,
                        "대화방 생성에 실패했습니다."
                ));
            } else {
                // 성공: 새 Chatroom 객체를 그대로 보내줍니다.
                sch.send(new SChatroomCreatePacket(
                        packet.getRequestId(),
                        newRoom,
                        null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new SChatroomCreatePacket(
                    packet.getRequestId(),
                    null,
                    "서버 오류: " + e.getMessage()
            ));
        }
    }
}
