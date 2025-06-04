package gui.friend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatController {
    ChatScreen chatScreen;
    private List<String> messages;

    public ChatController(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
        this.messages = new ArrayList<>();
        this.chatScreen.addSendMessageListener(new SendMessageListener());
    }

    private class SendMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
    }

    private void sendMessage() {
        String message = chatScreen.getMessageText();
        if (!message.isEmpty()) {
            messages.add("나: " + message);
            chatScreen.appendMessage("나: " + message);
            chatScreen.clearMessageField();
        }
    }

    public void receiveMessage(String message) {
        messages.add("친구: " + message);
        chatScreen.appendMessage("친구: " + message);
    }
}
