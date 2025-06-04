package gui.friend;

public class ChatTest {
    public static void main(String[] args) {
        // 채팅 화면 생성
        ChatScreen screen = new ChatScreen("친구1");

        // 컨트롤러에 연결
        ChatController controller = new ChatController(screen);

        // 화면 보이기
        screen.setVisible(true);

        // 테스트용 수신 메시지
        controller.receiveMessage("안녕! 잘 지내?");
    }
}
