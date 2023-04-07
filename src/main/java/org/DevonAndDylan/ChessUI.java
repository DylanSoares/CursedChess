package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChessUI extends JFrame {

    private JPanel panel;
    private final Color lightSquareColor = new Color(255, 206, 158);
    private final Color darkSquareColor = new Color(209, 139, 71);
    private final Color backgroundColor = new Color(51, 51, 51);
    private final Color textColor = new Color(255, 255, 255);

    public ChessUI(ArrayList<Piece> pieces) {
        JFrame frame = new JFrame("Chessboard");
        drawPanel(pieces);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawPanel(ArrayList<Piece> pieces) {
        panel = new JPanel(new GridLayout(10, 10));

        // add letters along top and bottom rows
        addLabels();

        // add buttons and numbers along left and right columns
        // TODO make this in an array so we can actaully reference the buttons and start to
        //      think about data transfer for the server.
        for (int i = 8; i >= 1; i--) {
            JLabel labelStart = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            labelStart.setForeground(textColor);
            panel.add(labelStart);
            for (char j = 'a'; j <= 'h'; j++) {
                JButton button = new JButton();
                int finali = i;
                char finalJ = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO handle passing this click back to the ChessClient etc
                        System.out.println("User clicked button at " + finali + "" + finalJ);
                    }
                });
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
        addLabels();
        panel.setBackground(backgroundColor);
    }

    private void addLabels() {
        panel.add(new JLabel(""));
        for (char c = 'a'; c <= 'h'; c++) {
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setForeground(textColor);
            panel.add(label);
        }
        panel.add(new JLabel(""));
    }
}
