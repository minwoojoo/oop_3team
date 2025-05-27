package kr.ac.catholic.cls032690125.oop3team.gui.setting;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockListScreen extends JFrame {
    private List<String> blockedUsers;
    private JPanel blockListPanel;

    public BlockListScreen(JFrame parent) {
        setTitle("차단 목록");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 샘플 데이터 추가
        blockedUsers = new ArrayList<>();
        blockedUsers.add("김철수");
        blockedUsers.add("이영희");
        blockedUsers.add("박민수");

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 차단 목록 패널
        blockListPanel = new JPanel();
        blockListPanel.setLayout(new BoxLayout(blockListPanel, BoxLayout.Y_AXIS));
        updateBlockList();

        JScrollPane scrollPane = new JScrollPane(blockListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void updateBlockList() {
        blockListPanel.removeAll();
        
        for (String user : blockedUsers) {
            JPanel userPanel = new JPanel(new BorderLayout(5, 5));
            userPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            userPanel.setBackground(Color.WHITE);
            userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JLabel nameLabel = new JLabel(user);
            nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

            JButton unblockButton = new JButton("차단 해제");
            unblockButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            unblockButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(this,
                    user + "님의 차단을 해제하시겠습니까?",
                    "차단 해제",
                    JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                    blockedUsers.remove(user);
                    updateBlockList();
                }
            });

            userPanel.add(nameLabel, BorderLayout.WEST);
            userPanel.add(unblockButton, BorderLayout.EAST);
            
            blockListPanel.add(userPanel);
            blockListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        blockListPanel.revalidate();
        blockListPanel.repaint();
    }

    private List<String> generateRandomBlockedFriends() {
        List<String> blocked = new ArrayList<>();
        String[] firstNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
        String[] lastNames = {"민준", "서연", "지우", "서준", "하은", "도윤", "시윤", "지아", "하준", "지민"};
        
        Random random = new Random();
        String name = firstNames[random.nextInt(firstNames.length)] + 
                     lastNames[random.nextInt(lastNames.length)];
        blocked.add(name);
        
        return blocked;
    }
} 