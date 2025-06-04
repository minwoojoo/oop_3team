// ChatTest.java
package gui.chat;

public class ChatTest {
    public static void main(String[] args) {
        ChatController controller = new ChatController();

        ChatWindowUi userA = new ChatWindowUi("A", "B", controller);
        ChatWindowUi userB = new ChatWindowUi("B", "A", controller);

        controller.registerUser("A", userA);
        controller.registerUser("B", userB);

        userA.setVisible(true);
        userB.setVisible(true);
    }
}
