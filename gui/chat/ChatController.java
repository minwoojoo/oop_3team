package gui.chat;

import java.util.HashMap;
import java.util.Map;

public class ChatController {
    // 사용자 이름을 기준으로 ChatWindowUi를 관리
    private Map<String, ChatWindowUi> userChatMap = new HashMap<>();

    // ChatWindowUi 등록
    public void registerUser(String username, ChatWindowUi ui) {
        userChatMap.put(username, ui);
    }

    // 메시지 전송
    public void sendMessage(String fromUser, String toUser, String message) {
        ChatWindowUi senderUi = userChatMap.get(fromUser);
        ChatWindowUi receiverUi = userChatMap.get(toUser);

        if (senderUi != null) {
            senderUi.appendMessage("나: " + message);
        }
        if (receiverUi != null) {
            receiverUi.appendMessage(fromUser + ": " + message);
        }
    }
}
