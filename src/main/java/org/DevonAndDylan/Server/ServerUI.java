package org.DevonAndDylan.Server;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

public class ServerUI extends JFrame {
    private boolean serverStarted = false;
    private final JTextArea logsTextArea;
    private final JButton startButton;
    private JTextField portTextField;
    private JTextField ipTextField;

    final Font arial = new Font("Arial", Font.PLAIN, 18);
    final Font arial_sm = new Font("Arial", Font.PLAIN, 14);

    public ServerUI(BlockingQueue<String> serverInfoQueue) {

        ImageIcon clientIcon = new ImageIcon("src/main/resources/clienticon.png");
        setIconImage(clientIcon.getImage());

        setTitle("CursedChess Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create title label
        JLabel titleLabel = new JLabel("CursedChess Server", JLabel.CENTER);
        titleLabel.setFont(arial);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create logs text area and add to scroll pane
        logsTextArea = new JTextArea();
        logsTextArea.setEditable(false);
        logsTextArea.setBackground(Color.BLACK);
        logsTextArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(logsTextArea);
        scrollPane.setPreferredSize(new Dimension(480, 240));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel with start button
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Server");
        startButton.setPreferredSize(new Dimension(160, 50));
        startButton.setFocusPainted(false);
        Color startColor = new Color(135, 243, 135);
        startButton.setBackground(startColor);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.add(startButton);
        startButton.setFont(arial_sm);
//        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            try {
                if (!serverStarted) {
                    serverStarted = true;
                    serverInfoQueue.put(ipTextField.getText());
                    serverInfoQueue.put(portTextField.getText());
                    ipTextField.setEditable(false);
                    portTextField.setEditable(false);
                    startButton.setBackground(Color.GRAY);
                    logsTextArea.setText("Server started by UI");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        // Create port panel with port text field
        JPanel portPanel = new JPanel();
        portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.Y_AXIS));
        portPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        portTextField = new JTextField("7777", 10);
        JLabel portLabel = new JLabel("Server Port:");

        portLabel.setFont(arial_sm);

        // Create IP label
        JLabel ipLabel = new JLabel("Server IP:");
        ipLabel.setFont(arial_sm);

        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "IP Address: N/A";
        }

        ipTextField = new JTextField(ip);
        ipLabel.setFont(arial_sm);

        portPanel.add(ipLabel, BorderLayout.SOUTH);
        portPanel.add(ipTextField);
        portPanel.add(Box.createVerticalStrut(10));
//        portLabel.setLabelFor(portTextField);
        portPanel.add(portLabel);
        portPanel.add(portTextField);
        portPanel.add(buttonPanel);
        portPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(portPanel, BorderLayout.WEST);

        Color backgroundColor = new Color(51, 51, 51);
        mainPanel.setBackground(backgroundColor);
        buttonPanel.setBackground(backgroundColor);
        portPanel.setBackground(backgroundColor);
        Color textColor = new Color(255, 255, 255);
        ipLabel.setForeground(textColor);
        portLabel.setForeground(textColor);
        titleLabel.setForeground(textColor);

        // Add main panel to frame
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void sendLogMsg(String msg) {
        logsTextArea.setText(logsTextArea.getText() + "\n" + msg);
    }
}