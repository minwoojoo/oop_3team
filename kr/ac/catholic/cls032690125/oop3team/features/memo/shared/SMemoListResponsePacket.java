package kr.ac.catholic.cls032690125.oop3team.features.memo.shared;

import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui.MemoListScreen;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;
import java.io.Serializable;
import java.util.List;
import kr.ac.catholic.cls032690125.oop3team.models.ChatMemo;

/**
 * 서버 → 클라이언트 : 메모 목록 응답 패킷
 */
public class SMemoListResponsePacket extends ServerResponseBasePacket implements Serializable {
    private List<ChatMemo> memos;

    public SMemoListResponsePacket(long requestId, List<ChatMemo> memos) {
        super(requestId);
        this.memos = memos;
    }

    public List<ChatMemo> getMemos() {
        return memos;
    }
}