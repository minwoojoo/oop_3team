package kr.ac.catholic.cls032690125.oop3team.features.memo.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.CMemoSavePacket;
import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui.MemoListScreen;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.features.memo.serverside.MemoDAO;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.CMemoListRequestPacket;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.SMemoListResponsePacket;
import java.util.List;
import java.util.ArrayList;
import kr.ac.catholic.cls032690125.oop3team.models.ChatMemo;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.CMemoUpdatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.SMemoUpdateResponsePacket;

public class SMemoController extends ServerRequestListener {
    private final MemoDAO memoDAO;

    public SMemoController(Server server) {
        super(server);
        this.memoDAO = new MemoDAO(server.getDatabase());
    }

    @ServerRequestHandler(CMemoSavePacket.class)
    public void handleSaveMemo(ServerClientHandler sch, CMemoSavePacket packet) {
        try {
            memoDAO.saveMemo(packet.getUserId(), packet.getMessageId(), packet.getMemoText());
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), true));
        } catch (Exception e) {
            e.printStackTrace();
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), false));
        }
    }

    @ServerRequestHandler(CMemoListRequestPacket.class)
    public void handleMemoListRequest(ServerClientHandler sch, CMemoListRequestPacket packet) {
        try {
            List<ChatMemo> memos = memoDAO.getMemosByUserId(packet.getUserId());
            sch.send(new SMemoListResponsePacket(packet.getRequestId(), memos));
        } catch (Exception e) {
            e.printStackTrace();
            sch.send(new SMemoListResponsePacket(packet.getRequestId(), new ArrayList<>()));
        }
    }

    @ServerRequestHandler(CMemoUpdatePacket.class)
    public void handleUpdateOrDeleteMemo(ServerClientHandler sch, CMemoUpdatePacket packet) {
        try {
            boolean result;
            if (packet.isDelete()) {
                result = memoDAO.deleteMemo(packet.getUserId(), packet.getTimestamp());
            } else {
                result = memoDAO.updateMemo(packet.getUserId(), packet.getTimestamp(), packet.getMemoText());
            }
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), result));
        } catch (Exception e) {
            e.printStackTrace();
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), false));
        }
    }
}
