package kr.ac.catholic.cls032690125.oop3team.features.chatroom.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SChatroomController extends ServerRequestListener {
    private final ChatroomDAO chatroomDAO;

    public SChatroomController(Server client) {
        super(client);
        this.chatroomDAO = new ChatroomDAO(client);
    }

    @ServerRequestHandler(CChatroomMemberListPacket.class)
    public void loadMemberList(ServerClientHandler sch, CChatroomMemberListPacket packet) {
        int roomId = packet.getChatroomId();
        ArrayList<String> members = getMemberList(roomId);
        // 정상 조회된 member 목록을 그대로 응답 패킷에 담아 전송
        sch.send(new SChatroomMemberListPacket(roomId, members));
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
    public ArrayList<String> getMemberList(int chatroomId) {
        try{
            return chatroomDAO.getMemberList(chatroomId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    /**
     * 1) CChatroomCreatePacket을 받으면 호출됩니다.
     * - 패킷에는 ‘title’과 ‘ownerId’가 들어 있습니다.
     * 2) DAO를 통해 DB에 INSERT → 생성된 Chatroom 객체를 리턴받습니다.
     * 3) 성공 여부에 따라 SChatroomCreatePacket으로 결과를 클라이언트에 보냅니다.
     */
    @ServerRequestHandler(CChatroomCreatePacket.class)
    public void createChatroom(ServerClientHandler sch, CChatroomCreatePacket packet) {
        String title = packet.getTitle();
        String ownerId = packet.getOwnerId();
        Integer parentRoomId = packet.getParentRoomId();
        List<String> participants = packet.getParticipants();
        try {
            Chatroom newRoom = chatroomDAO.createChatroomWithParticipants(title, ownerId, participants, parentRoomId);
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

    @ServerRequestHandler(CChatroomThreadListPacket.class)
    public void getThreadList(ServerClientHandler sch, CChatroomThreadListPacket packet) throws SQLException {
        int parentId = packet.getParentId();
        boolean isOpened = packet.getIsOpened();

        ArrayList<Chatroom> threadsByParentId = chatroomDAO.findThreadsByParentId(parentId, isOpened);
        sch.send(new SChatroomThreadListPacket(threadsByParentId));
    }

    @ServerRequestHandler(CChatroomThreadClosePacket.class)
    public void closeThread(ServerClientHandler sch, CChatroomThreadClosePacket packet) throws SQLException {
        int threadId = packet.getThreadId();
        int chatroomId = chatroomDAO.closeChatroom(threadId);

        sch.send(new SChatroomThreadClosePacket(chatroomId));
    }

}
