package kr.ac.catholic.cls032690125.oop3team.features.chatroom.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.MessageBuilder;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.features.chat.serverside.ChatDAO;
import kr.ac.catholic.cls032690125.oop3team.features.setting.serverside.SettingDAO;
import kr.ac.catholic.cls032690125.oop3team.models.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SChatroomController extends ServerRequestListener {
    private final ChatroomDAO chatroomDAO;
    private final ChatDAO chatDAO = new ChatDAO(server);
    private final SettingDAO settingDAO = new SettingDAO(server);

    public SChatroomController(Server client) {
        super(client);
        this.chatroomDAO = new ChatroomDAO(client);
    }

    @ServerRequestHandler(CChatroomMemberListPacket.class)
    public void loadMemberList(ServerClientHandler sch, CChatroomMemberListPacket packet) {
        int roomId = packet.getChatroomId();
        List<ChatroomDAO.MemberInfo> members = new ArrayList<>();
        try {
            members = chatroomDAO.getMemberIdNameList(roomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // id, name 리스트로 분리
        ArrayList<String> memberIds = new ArrayList<>();
        ArrayList<String> memberNames = new ArrayList<>();
        for (ChatroomDAO.MemberInfo m : members) {
            memberIds.add(m.userId);
            memberNames.add(m.name != null ? m.name : m.userId);
        }
        sch.send(new SChatroomMemberListPacket(packet.getRequestId(), roomId, memberIds, memberNames));
    }

    @ServerRequestHandler(CChatroomListLoadPacket.class)
    public void loadChatroomList(ServerClientHandler sch, CChatroomListLoadPacket packet) {
        try {
            // 현재 로그인한 사용자의 ID를 가져옴
            String userId = sch.getSession().getUserId();
            // 사용자가 속한 대화방만 불러옴
            Chatroom[] rooms = chatroomDAO.loadUserChatrooms(userId, packet.isPrivate());
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
     * - 패킷에는 'title'과 'ownerId'가 들어 있습니다.
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
            Chatroom newRoom = chatroomDAO.createChatroomWithParticipants(
                title, ownerId, participants, parentRoomId, packet.getIsPrivate()
            );
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

    @ServerRequestHandler(CChatroomInvitePacket.class)
    public void inviteUser(ServerClientHandler sch, CChatroomInvitePacket packet) {
        try (Connection conn = server.getDatabase().getConnection()) {
            for(var s : packet.getParticipants()) {
                try {
                    chatroomDAO.insertParticipants(conn, packet.getChatroomId(), s);

                    // 1. userId → 이름 변환
                    String name = settingDAO.getUserName(s);
                    // 2. 시스템 메시지 생성 및 저장
                    Message systemMsg = new MessageBuilder()
                        .setChatroomId(packet.getChatroomId())
                        .setSenderId("system")
                        .setContent(name + "님이 들어왔습니다")
                        .setSystem(true)
                        .setCurrentTime()
                        .build();
                    chatDAO.insertMessage(systemMsg);

                    // (기존 broadcastMessage 등은 필요에 따라 유지)
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), true));
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), false));
        }
    }

    @ServerRequestHandler(CChatroomThreadListPacket.class)
    public void loadThreadList(ServerClientHandler sch, CChatroomThreadListPacket packet) {
        try {
            ArrayList<Chatroom> threads = chatroomDAO.findThreadsByParentId(packet.getParentId(), packet.isClosed());
            sch.send(new SChatroomThreadListPacket(
                    packet.getRequestId(),
                    threads,
                    null
            ));
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new SChatroomThreadListPacket(
                    packet.getRequestId(),
                    null,
                    "스레드 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage()
            ));
        }
    }

    @ServerRequestHandler(CChatroomThreadClosePacket.class)
    public void closeThread(ServerClientHandler sch, CChatroomThreadClosePacket packet) {
        try {
            String leaderId = chatroomDAO.getLeaderId(packet.getThreadId());
            if (leaderId != null && leaderId.equals(packet.getUserId())) {
                int result = chatroomDAO.closeChatroom(packet.getThreadId());
                sch.send(new SChatroomThreadClosePacket(packet.getRequestId(), result > 0, null));
            } else {
                sch.send(new SChatroomThreadClosePacket(packet.getRequestId(), false, "권한이 없습니다: 리더만 닫을 수 있습니다."));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new SChatroomThreadClosePacket(packet.getRequestId(), false, "스레드 닫기 실패: " + e.getMessage()));
        }
    }

    @ServerRequestHandler(CChatroomLeavePacket.class)
    public void leaveChatroom(ServerClientHandler sch, CChatroomLeavePacket packet) {
        try {
            boolean result = chatroomDAO.removeParticipant(packet.getChatroomId(), packet.getUserId());
            if (result) {
                // 1. userId → 이름 변환
                String name = settingDAO.getUserName(packet.getUserId());
                // 2. 시스템 메시지 생성 및 저장
                Message systemMsg = new MessageBuilder()
                    .setChatroomId(packet.getChatroomId())
                    .setSenderId("system")
                    .setContent(name + "님이 나갔습니다")
                    .setSystem(true)
                    .setCurrentTime()
                    .build();
                chatDAO.insertMessage(systemMsg);

                sch.send(new SChatroomLeavePacket(true, "채팅방을 성공적으로 나갔습니다."));
            } else {
                sch.send(new SChatroomLeavePacket(false, "채팅방 나가기에 실패했습니다."));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new SChatroomLeavePacket(false, "서버 오류: " + e.getMessage()));
        }
    }

    @ServerRequestHandler(CPrivateChatRoomRequestPacket.class)
    public void handlePrivateChatRoomRequest(ServerClientHandler sch, CPrivateChatRoomRequestPacket packet) {
        String userA = packet.getUserA();
        String userB = packet.getUserB();
        try {
            Chatroom room = chatroomDAO.findOrCreatePrivateChatroom(userA, userB);
            sch.send(new SPrivateChatRoomResponsePacket(packet.getRequestId(), room));
        } catch (SQLException e) {
            e.printStackTrace();
            sch.send(new SPrivateChatRoomResponsePacket(packet.getRequestId(), null));
        }
    }
}
