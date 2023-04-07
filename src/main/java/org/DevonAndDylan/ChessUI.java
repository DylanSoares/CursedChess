package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.Board;
import org.DevonAndDylan.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessUI extends JFrame {

    private JPanel panel;
    private final Color lightSquareColor = new Color(255, 206, 158);
    private final Color darkSquareColor = new Color(209, 139, 71);
    private final Color backgroundColor = new Color(51, 51, 51);
    private final Color textColor = new Color(255, 255, 255);

    Font arial = new Font("Arial", Font.PLAIN, 20);

    private final ImageIcon bishopBlack = new ImageIcon("src/main/resources/bb.png");
    private final ImageIcon bishopWhite = new ImageIcon("src/main/resources/wb.png");

    private final ImageIcon pawnBlack = new ImageIcon("src/main/resources/bp.png");
    private final ImageIcon pawnWhite = new ImageIcon("src/main/resources/wp.png");

    private final ImageIcon kingBlack = new ImageIcon("src/main/resources/bk.png");
    private final ImageIcon kingWhite = new ImageIcon("src/main/resources/wk.png");

    private final ImageIcon knightBlack = new ImageIcon("src/main/resources/bn.png");
    private final ImageIcon knightWhite = new ImageIcon("src/main/resources/wn.png");

    private final ImageIcon rookBlack = new ImageIcon("src/main/resources/br.png");
    private final ImageIcon rookWhite = new ImageIcon("src/main/resources/wr.png");

    private final ImageIcon queenBlack = new ImageIcon("src/main/resources/bq.png");
    private final ImageIcon queenWhite = new ImageIcon("src/main/resources/wq.png");


    public ChessUI(Piece[][] pieces) {
        JFrame frame = new JFrame("Chessboard");
        drawPanel(pieces);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawPanel(Piece[][] pieces) {
        panel = new JPanel(new GridLayout(10, 10));

        // add letters along top and bottom rows
        addLabels();

        // add buttons and numbers along left and right columns
        // TODO make this in an array so we can actaully reference the buttons and start to
        //      think about data transfer for the server.
        for (int i = 8; i >= 1; i--) {
            JLabel labelStart = new JLabel(String.valueOf(i), SwingConstants.CENTER);

            labelStart.setFont(arial);
            labelStart.setForeground(textColor);
            panel.add(labelStart);
            for (char j = 'a'; j <= 'h'; j++) {
                JButton button = new JButton();

                int finalj = Board.toInt(j) - 1;
                int finali = i - 1;

                Piece currPiece = pieces[finali][finalj];

                if (currPiece != null) {
                    if (currPiece.isWhite()) {
                        switch (currPiece.getLetter()) {
                            case 'P' -> button.setIcon(pawnWhite);
                            case 'Q' -> button.setIcon(queenWhite);
                            case 'K' -> button.setIcon(kingWhite);
                            case 'N' -> button.setIcon(knightWhite);
                            case 'B' -> button.setIcon(bishopWhite);
                            case 'R' -> button.setIcon(rookWhite);
                        }
                    } else {
                        switch (currPiece.getLetter()) {
                            case 'P' -> button.setIcon(pawnBlack);
                            case 'Q' -> button.setIcon(queenBlack);
                            case 'K' -> button.setIcon(kingBlack);
                            case 'N' -> button.setIcon(knightBlack);
                            case 'B' -> button.setIcon(bishopBlack);
                            case 'R' -> button.setIcon(rookBlack);
                        }
                    }
                }

                button.addActionListener(e -> {
                    // TODO handle passing this click back to the ChessClient etc
                    System.out.println("DEBUG: Location clicked was a: " + pieces[finali][finalj]);
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
            labelEnd.setFont(arial);
            panel.add(labelEnd);
        }

        // add letters along top and bottom rows
        addLabels();
        panel.setBackground(backgroundColor);
    }

    /**
     * Helpter method for clearing and redrawing the UI
     *
     * @param pieces - board pieces, use board's getter.
     */
    public void updateUI(Piece[][] pieces) {
        panel.removeAll();
        drawPanel(pieces);
    }

    private void addLabels() {
        panel.add(new JLabel(""));
        for (char c = 'a'; c <= 'h'; c++) {
            JLabel label = new JLabel(String.valueOf(c).toUpperCase(), SwingConstants.CENTER);
            label.setForeground(textColor);
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            panel.add(label);
        }
        panel.add(new JLabel(""));
    }
}
