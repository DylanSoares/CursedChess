package org.DevonAndDylan;
import javax.swing.*;
import java.awt.*;

public class ChessUI extends JFrame {

    public ChessUI() {

        Color lightSquareColor = new Color(255, 206, 158);
        Color darkSquareColor = new Color(209, 139, 71);
        Color backgroundColor = new Color(51, 51, 51);
        Color textColor = new Color(255, 255, 255);

        JFrame frame = new JFrame("Chessboard");
        JPanel panel = new JPanel(new GridLayout(10, 10));

        // add letters along top and bottom rows
        panel.add(new JLabel(""));
        for (char c = 'a'; c <= 'h'; c++) {
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setForeground(textColor);
            panel.add(label);
        }
        panel.add(new JLabel(""));

        // add buttons and numbers along left and right columns
        for (int i = 8; i >= 1; i--) {
            JLabel labelStart = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            labelStart.setForeground(textColor);
            panel.add(labelStart);
            for (char j = 'a'; j <= 'h'; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                if ((i + j) % 2 == 0) {
                    button.setBackground(lightSquareColor);
                } else {
                    button.setBackground(darkSquareColor);
                }
                panel.add(button);
            }
            JLabel labelEnd = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            labelEnd.setForeground(textColor);
            panel.add(labelEnd);
        }

        // add letters along top and bottom rows
        panel.add(new JLabel(""));
        for (char c = 'a'; c <= 'h'; c++) {
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setForeground(textColor);
            panel.add(label);
        }
        panel.add(new JLabel(""));
        panel.setBackground(backgroundColor);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
