package org.DevonAndDylan.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

public class LauncherUI extends JFrame {

    final Font arial = new Font("Arial", Font.PLAIN, 20);
    private final JTextField serverTextfield;
    private final JTextField portTextfield;

    public LauncherUI(BlockingQueue<String> serverInfoQueue) {

        ImageIcon clientIcon = new ImageIcon("src/main/resources/clienticon.png");
        setIconImage(clientIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 310)); // set preferred size

        setResizable(false);

        // Create header label
        JLabel headerLabel = new JLabel("Cursed Chess", JLabel.CENTER);
        headerLabel.setFont(arial);
        add(headerLabel, BorderLayout.NORTH);

        // Create vertical button panel
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // add vertical spacing
        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel2.setAlignmentX(JComponent.CENTER_ALIGNMENT); // center horizontally

        JButton confirmButton = new JButton("Continue");
        JButton exitButton = new JButton("Quit");

        // Add buttons to button panel
        buttonPanel2.add(exitButton);
        buttonPanel2.add(Box.createHorizontalStrut(10)); // spacing
        buttonPanel2.add(confirmButton);

        JPanel boardContainer = new JPanel();
        boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
        boardContainer.add(buttonPanel2);

        // Create toggle button and text fields
        JPanel togglePanel = new JPanel(new BorderLayout());
        JButton toggleButton = new JButton("Change Server Info");
        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isVisible = serverTextfield.isVisible();
                serverTextfield.setVisible(!isVisible);
                portTextfield.setVisible(!isVisible);
                revalidate();
                repaint();
            }
        });

        togglePanel.add(toggleButton, BorderLayout.NORTH);

        JPanel textFieldPanel = new JPanel();
        serverTextfield = new JTextField(15);
        portTextfield = new JTextField(5);

        serverTextfield.setVisible(false); // initially hidden
        portTextfield.setVisible(false); // initially hidden


        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "localhost";
        }
        serverTextfield.setText(ip);
        portTextfield.setText("7777");

        textFieldPanel.add(serverTextfield);
        textFieldPanel.add(portTextfield);

        togglePanel.add(textFieldPanel, BorderLayout.CENTER);

        // Create center panel for button and toggle panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)); // allow expansion
        centerPanel.add(boardContainer, BorderLayout.CENTER);

        Color backgroundColor = new Color(51, 51, 51);
        boardContainer.setBackground(backgroundColor);
        textFieldPanel.setBackground(backgroundColor);
        buttonPanel2.setBackground(backgroundColor);
        setBackground(backgroundColor);

        // Add panels to frame
        add(centerPanel, BorderLayout.CENTER);
        add(togglePanel, BorderLayout.SOUTH);

        // Display the frame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        Color textColor = new Color(255, 255, 255);
        exitButton.setForeground(textColor);
        confirmButton.setForeground(textColor);
        toggleButton.setForeground(textColor);


        exitButton.setFont(arial);
        confirmButton.setFont(arial);

        toggleButton.setFont(arial);


        Color buttonColor = new Color(101, 101, 101);
        toggleButton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);
        confirmButton.setBackground(buttonColor);


        exitButton.addActionListener(e -> System.exit(0));

        confirmButton.addActionListener(e -> {
            try {
                serverInfoQueue.put(serverTextfield.getText());
                serverInfoQueue.put(portTextfield.getText());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                System.err.println("help me please");
            }

            Container frame = confirmButton.getParent();
            do
                frame = frame.getParent();
            while (!(frame instanceof JFrame));
            ((JFrame) frame).dispose();
        });
    }

}
