package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.Board;
import org.DevonAndDylan.Pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class ChessUI extends JFrame {

    private JPanel boardPanel;
    private JPanel boardContainer;
    private JPanel topPanel;
    private final JFrame frame;

    private final Color lightSquareColor = new Color(255, 206, 158);
    private final Color darkSquareColor = new Color(209, 139, 71);
    private final Color backgroundColor = new Color(51, 51, 51);
    private final Color buttonColor = new Color(101, 101, 101);
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


    /**
     * A constructor to initilaize the Chessboard UI
     * Note: It is assumed white is the first player to play.
     * @param pieces Array of pieces
     * @param rotated If true, the board will be drawn rotated for the second player.
     */
    public ChessUI(Piece[][] pieces, boolean rotated) {
        frame = new JFrame("Chessboard");
        ImageIcon clientIcon = new ImageIcon("src/main/resources/clienticon.png");
        frame.setIconImage(clientIcon.getImage());

        drawBoardPanel(pieces, rotated);
        drawTopPanel(true);

        // Create a new JPanel for the board container
        boardContainer = new JPanel();
        boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
        boardContainer.add(topPanel);
        boardContainer.add(boardPanel);

        // Add the board container to the frame
        frame.setResizable(false);
        frame.add(boardContainer);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawTopPanel(boolean isWhitesTurn) {
        // Create a new JPanel for the buttons container
        topPanel = new JPanel(new GridLayout(1, 3));

        String output;
        if(isWhitesTurn) output = "White's Turn";
        else output = "Black's Turn";

        JLabel labelTurn = new JLabel(output, SwingConstants.CENTER);
        labelTurn.setFont(arial);
        labelTurn.setForeground(textColor);

        JButton button2 = new JButton("Button 2");
        button2.setBackground(buttonColor);
        button2.setForeground(textColor);
        button2.setFont(arial);

        JButton button3 = new JButton("Button 3");
        button3.setBackground(buttonColor);
        button3.setForeground(textColor);
        button3.setFont(arial);

        topPanel.add(labelTurn);
        topPanel.add(button2);
        topPanel.add(button3);
        topPanel.setBackground(backgroundColor);
    }

    private void drawBoardPanel(Piece[][] pieces, boolean rotated) {
        boardPanel = new JPanel(new GridLayout(10, 10));
        // add letters along top and bottom rows
        addLabels(rotated);
        if(rotated) pieces = rotatePieces(pieces);

        // add buttons and numbers along left and right columns
        // TODO make this in an array so we can actaully reference the buttons and start to
        //      think about data transfer for the server.
        for (int i = 8; i >= 1; i--) {
            JLabel labelStart;
            if(rotated) labelStart = new JLabel(String.valueOf(9-i), SwingConstants.CENTER);
            else labelStart = new JLabel(String.valueOf(i), SwingConstants.CENTER);

            labelStart.setFont(arial);
            labelStart.setForeground(textColor);
            boardPanel.add(labelStart);
            for (char j = 'a'; j <= 'h'; j++) {
                JButton button = new JButton();

                int finalj = Board.toInt(j) - 1;
                int finali = i - 1;

                Piece currPiece = pieces[finali][finalj];

                if (currPiece != null) {
                    if (currPiece.isWhite()) {
                        //noinspection DuplicatedCode
                        switch (currPiece.getLetter()) {
                            case 'P' -> button.setIcon(pawnWhite);
                            case 'Q' -> button.setIcon(queenWhite);
                            case 'K' -> button.setIcon(kingWhite);
                            case 'N' -> button.setIcon(knightWhite);
                            case 'B' -> button.setIcon(bishopWhite);
                            case 'R' -> button.setIcon(rookWhite);
                        }
                    } else {
                        //noinspection DuplicatedCode
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

                Piece[][] finalPieces = pieces;
                button.addActionListener(e -> {
                    // TODO handle passing this click back to the ChessClient etc
                    System.out.println("[UI:99] DEBUG: Location clicked was: " + finalPieces[finali][finalj]);
                });

                button.setPreferredSize(new Dimension(50, 50));
                if ((i + j) % 2 == 0) {
                    button.setBackground(lightSquareColor);
                } else {
                    button.setBackground(darkSquareColor);
                }
                boardPanel.add(button);
            }

            JLabel labelEnd;
            if(rotated) labelEnd = new JLabel(String.valueOf(9-i), SwingConstants.CENTER);
            else labelEnd = new JLabel(String.valueOf(i), SwingConstants.CENTER);

            labelEnd.setForeground(textColor);
            labelEnd.setFont(arial);
            boardPanel.add(labelEnd);
        }

        // add letters along top and bottom rows
        addLabels(rotated);
        boardPanel.setBackground(backgroundColor);
    }

    /**
     * @param pieces 2D Array of pieces fetched from the board's method.
     * @param rotated If true, the board will be drawn rotated for players
     */
    public void redrawBoard(Piece[][] pieces, boolean rotated, boolean isWhitesTurn) {
        frame.remove(topPanel);
        frame.remove(boardPanel);
        frame.remove(boardContainer);

        drawTopPanel(isWhitesTurn);
        drawBoardPanel(pieces, rotated);

        // Create a new JPanel for the board container
        boardContainer = new JPanel();
        boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
        boardContainer.add(topPanel);
        boardContainer.add(boardPanel);
        boardContainer.setBackground(backgroundColor);

        frame.add(boardContainer);

        frame.validate();
        frame.repaint();
    }


    private Piece[][] rotatePieces(Piece[][] pieces) {
        Piece[][] rotatedPieces = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                rotatedPieces[i][j] = pieces[7 - i][7 - j];
            }
        }
        return rotatedPieces;
    }

    private void addLabels(boolean rotated) {
        boardPanel.add(new JLabel(""));
        if (!rotated) {
            for (char c = 'a'; c <= 'h'; c++) {
                JLabel label = new JLabel(String.valueOf(c).toUpperCase(), SwingConstants.CENTER);
                label.setForeground(textColor);
                label.setFont(new Font("Arial", Font.PLAIN, 20));
                boardPanel.add(label);
            }
        } else {
            for (char c = 'h'; c >= 'a'; c--) {
                JLabel label = new JLabel(String.valueOf(c).toUpperCase(), SwingConstants.CENTER);
                label.setForeground(textColor);
                label.setFont(new Font("Arial", Font.PLAIN, 20));
                boardPanel.add(label);
            }
        }
        boardPanel.add(new JLabel(""));
    }
}
