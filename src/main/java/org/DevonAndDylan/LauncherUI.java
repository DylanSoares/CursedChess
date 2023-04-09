package org.DevonAndDylan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.BlockingQueue;

public class LauncherUI extends JFrame {

    boolean chooseBlack = false;
    private final BlockingQueue<Boolean> choiceQueue;

    Font arial = new Font("Arial", Font.PLAIN, 20);

    private final Color backgroundColor = new Color(51, 51, 51);
    private final Color buttonColor = new Color(101, 101, 101);
    private final Color textColor = new Color(255, 255, 255);
    private final Color selectedPieceColor = new Color(226, 207, 89);

    private final JTextField serverTextfield;
    private final JTextField portTextfield;

    public LauncherUI(BlockingQueue<Boolean> choiceQueue) {

        this.choiceQueue = choiceQueue;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300)); // set preferred size

        // Create header label
        JLabel headerLabel = new JLabel("Cursed Chess", JLabel.CENTER);
        headerLabel.setFont(arial);
        add(headerLabel, BorderLayout.NORTH);

        // Create vertical button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // add vertical spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // center horizontally


        // Create header label
        JButton whiteButton = new JButton("White");
        JButton blackButton = new JButton("Black");

        // Add buttons to button panel

        buttonPanel.add(whiteButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // spacing
        buttonPanel.add(blackButton);


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


        JLabel colorLabel = new JLabel("Please choose a color:", JLabel.CENTER);
        headerLabel.setFont(arial);

        JPanel boardContainer = new JPanel();
        boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
        boardContainer.add(colorLabel);
        boardContainer.add(buttonPanel);
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
        serverTextfield = new JTextField(20);
        portTextfield = new JTextField(20);

        serverTextfield.setVisible(false);
        portTextfield.setVisible(false); // initially hidden

        serverTextfield.setText("localhost");
        portTextfield.setText("7777");

        textFieldPanel.add(serverTextfield);
        textFieldPanel.add(portTextfield);

        togglePanel.add(textFieldPanel, BorderLayout.CENTER);

        // Create center panel for button and toggle panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)); // allow expansion
        centerPanel.add(boardContainer, BorderLayout.CENTER);

        boardContainer.setBackground(backgroundColor);
        textFieldPanel.setBackground(backgroundColor);
        buttonPanel2.setBackground(backgroundColor);
        buttonPanel.setBackground(backgroundColor);
        setBackground(backgroundColor);

        // Add panels to frame
        add(centerPanel, BorderLayout.CENTER);
        add(togglePanel, BorderLayout.SOUTH);

        // Display the frame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        exitButton.setForeground(textColor);
        confirmButton.setForeground(textColor);
        whiteButton.setForeground(textColor);
        blackButton.setForeground(textColor);
        toggleButton.setForeground(textColor);


        exitButton.setFont(arial);
        confirmButton.setFont(arial);
        whiteButton.setFont(arial);
        blackButton.setFont(arial);
        toggleButton.setFont(arial);


        toggleButton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);
        confirmButton.setBackground(buttonColor);
        blackButton.setBackground(buttonColor);
        whiteButton.setBackground(selectedPieceColor);

        // TODO listeners
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        confirmButton.addActionListener(e -> {
            try {
                choiceQueue.put(chooseBlack); // add the move to the blocking queue
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                System.err.println("help me please");
            }

            Container frame = confirmButton.getParent();
            do
                frame = frame.getParent();
            while (!(frame instanceof JFrame));
            ((JFrame) frame).dispose();

            // TODO handle checking for server info changes
        });

        whiteButton.addActionListener(e -> {
            chooseBlack = false;
            blackButton.setBackground(buttonColor);
            whiteButton.setBackground(selectedPieceColor);
        });

        blackButton.addActionListener(e -> {
            chooseBlack = true;
            blackButton.setBackground(selectedPieceColor);
            whiteButton.setBackground(buttonColor);
        });
    }

}
