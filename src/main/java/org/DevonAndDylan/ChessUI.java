package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.Board;
import org.DevonAndDylan.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class ChessUI extends JFrame {

    private JPanel boardPanel;
    private JPanel boardContainer;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private final JFrame frame;

    private final Color lightSquareColor = new Color(255, 206, 158);
    private final Color darkSquareColor = new Color(209, 139, 71);
    private final Color backgroundColor = new Color(51, 51, 51);
    private final Color buttonColor = new Color(101, 101, 101);
    private final Color textColor = new Color(255, 255, 255);
    private final Color selectedPieceColor = new Color(226, 207, 89);
    private final Color selectedMoveColor = new Color(249, 240, 123);

    private final BlockingQueue<String> moveQueue;
    private final BlockingQueue<Character> promoteQueue;

    final Font arial = new Font("Arial", Font.PLAIN, 20);

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

    private JLabel labelOutput;

    boolean selectedFirst = false;
    boolean selectedSecond = false;
    private int firstSelectedX = -1;
    private int firstSelectedY = -1;
    private int secondSelectedX = -1;
    private int secondSelectedY = -1;

    private boolean rotated;
    private boolean isWhitesTurn;

    private char promoteType;


    /**
     * A constructor to initialize the Chessboard UI
     * Note: this.moveQueue = moveQueue; assumed white is the first player to play.
     * Requires the blocking queue object from the summoning class
     *
     * @param pieces  Array of pieces
     * @param rotated If true, the board will be drawn rotated for the second player.
     */
    public ChessUI(Piece[][] pieces, boolean rotated, BlockingQueue<String> moveQueue, BlockingQueue<Character> promoteQueue) {

        this.moveQueue = moveQueue;
        this.promoteQueue = promoteQueue;

        frame = new JFrame("Chessboard");
        ImageIcon clientIcon = new ImageIcon("src/main/resources/clienticon.png");
        frame.setIconImage(clientIcon.getImage());

        this.rotated = rotated;

        drawBoardPanel(pieces, rotated);
        drawTopPanel(true);
        drawBottomPanel(-1);

        // Create a new JPanel for the board container
        boardContainer = new JPanel();
        boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
        boardContainer.add(topPanel);
        boardContainer.add(boardPanel);
        boardContainer.add(bottomPanel);

        // Add the board container to the frame
        frame.setResizable(false);
        frame.add(boardContainer);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawBottomPanel(int result) {
        // Create a new JPanel for the buttons container
        bottomPanel = new JPanel(new GridLayout(2, 1));
        String output;
        switch (result) {
            case 0 -> output = "Successfully moved.";
            case 1 -> output = "Illegal move.";
            case 2, 3 -> output = "You shouldn't see this.";
            case 4 -> output = "No piece selected to move.";
            case 5 -> output = "It's not your turn!";
            case 6 -> output = "Piece collides while trying to move.";
            case 7 -> output = "A piece is awaiting promotion.";
            default -> output = "Waiting for move.";
        }

        labelOutput = new JLabel(output, SwingConstants.CENTER);
        labelOutput.setFont(arial);
        labelOutput.setForeground(textColor);

        bottomPanel.add(labelOutput);
        bottomPanel.setBackground(backgroundColor);
    }

    private void drawTopPanel(boolean isWhitesTurn) {

        this.isWhitesTurn = isWhitesTurn;

        // Create a new JPanel for the buttons container
        topPanel = new JPanel(new GridLayout(1, 3));

        String output;
        if (isWhitesTurn) output = "White's Turn";
        else output = "Black's Turn";

        JLabel labelTurn = new JLabel(output, SwingConstants.CENTER);
        labelTurn.setFont(arial);
        labelTurn.setForeground(textColor);

        JButton unusedButton = new JButton("<Unused>");
        unusedButton.setBackground(buttonColor);
        unusedButton.setForeground(textColor);
        unusedButton.setFont(arial);

        JButton confirmButton = new JButton("Confirm Move");
        confirmButton.setBackground(buttonColor);
        confirmButton.setForeground(textColor);
        confirmButton.setFont(arial);

        confirmButton.addActionListener(e -> {
            if (selectedFirst && selectedSecond) {
                int firstX = firstSelectedX;
                int secondX = secondSelectedX;
                int firstY = firstSelectedY;
                int secondY = secondSelectedY;
                if (this.rotated) { //fix the rotation players index being all screwed up
                    firstY = 8 - firstSelectedY - 1;
                    firstX = 8 - firstSelectedX - 1;
                    secondX = 8 - secondSelectedX - 1;
                    secondY = 8 - secondSelectedY - 1;
                }
//                System.out.println("Client wants to send move " + Board.toChar(firstX) + "" + firstY + " to " + Board.toChar(secondX) + "" + secondY);
                String move = firstX + "" + firstY + "" + secondX + "" + secondY;
//                System.out.println("[UI:146] DEBUG: movement is requested: " + move);
                try {
                    moveQueue.put(move); // add the move to the blocking queue
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                    System.err.println("help me please");
                }
                selectedFirst = false;
                selectedSecond = false;
            } else {
                labelOutput.setText("Invalid selection.");
            }
        });

        topPanel.add(labelTurn);
        topPanel.add(unusedButton);
        topPanel.add(confirmButton);
        topPanel.setBackground(backgroundColor);
    }

    private void drawBoardPanel(Piece[][] pieces, boolean rotated) {
        boardPanel = new JPanel(new GridLayout(10, 10));
        // add letters along top and bottom rows
        addLabels(rotated);
        if (rotated) pieces = rotatePieces(pieces);

        for (int i = 8; i >= 1; i--) {
            JLabel labelStart;
            if (rotated) labelStart = new JLabel(String.valueOf(9 - i), SwingConstants.CENTER);
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

                Piece[][] finalPieces = pieces;
                button.addActionListener(e -> {
                    Color ogColor;
                    if ((finali + finalj) % 2 == 0) {
                        ogColor = darkSquareColor;
                    } else {
                        ogColor = lightSquareColor;
                    }

                    // NOTE: This doesn't quite work right for the rotated player, we check that on the confirm button
//                    System.out.println("[UI:99] DEBUG: Location clicked was: " + finalPieces[finali][finalj]);
                    if (finalPieces[finali][finalj] != null && !selectedFirst && !selectedSecond && ((isWhitesTurn && finalPieces[finali][finalj].isWhite()) || (!isWhitesTurn && !finalPieces[finali][finalj].isWhite()))) {
//                        System.err.println(Board.toChar(finalj+1) + " " + (finali+1));
                        firstSelectedX = finalj;
                        firstSelectedY = finali;
                        selectedFirst = true;
                        button.setBackground(selectedPieceColor);
                    } else if (selectedFirst && !selectedSecond && !(finalj == firstSelectedX && finali == firstSelectedY)) {
                        secondSelectedX = finalj;
                        secondSelectedY = finali;
                        selectedSecond = true;
                        button.setBackground(selectedMoveColor);
                    } else if (finalj == secondSelectedX && finali == secondSelectedY && selectedFirst) {

                        selectedSecond = false;
                        button.setBackground(ogColor);
                    } else if (finalj == firstSelectedX && finali == firstSelectedY && selectedFirst && !selectedSecond) {

                        selectedFirst = false;
                        button.setBackground(ogColor);
                    }
                });

                button.setPreferredSize(new Dimension(50, 50));
                if ((i + j) % 2 == 0) {
                    button.setBackground(darkSquareColor);
                } else {
                    button.setBackground(lightSquareColor);
                }
                boardPanel.add(button);
            }

            JLabel labelEnd;
            if (rotated) labelEnd = new JLabel(String.valueOf(9 - i), SwingConstants.CENTER);
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
     * A method that redraws or refreshes the UI
     *
     * @param pieces  2D Array of pieces fetched from the board's method.
     * @param rotated If true, the board will be drawn rotated for players
     * @param promote If the UI is required to draw the promotion
     */
    public void redrawBoard(Piece[][] pieces, boolean rotated, boolean isWhitesTurn, int moveResult, boolean promote) {
        frame.remove(topPanel);
        frame.remove(boardPanel);
        frame.remove(bottomPanel);
        frame.remove(boardContainer);

        this.rotated = rotated;

        drawTopPanel(isWhitesTurn);
        drawBoardPanel(pieces, rotated);
        drawBottomPanel(moveResult);

        // Create a new JPanel for the board container
        boardContainer = new JPanel();
        boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
        boardContainer.add(topPanel);
        boardContainer.add(boardPanel);
        boardContainer.add(bottomPanel);
        boardContainer.setBackground(backgroundColor);

        frame.add(boardContainer);

        if (promote) {
            drawPromotePopup();
            try {
                promoteQueue.put(promoteType); // add the move to the blocking queue
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                System.err.println("help me please");
            }
        }

        frame.validate();
        frame.repaint();
    }

    private void drawPromotePopup() {
        JDialog popup = new JDialog(frame, "Promotion Required", true);

        popup.setResizable(false);
        popup.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JPanel promoteContainer = new JPanel();
        promoteContainer.setLayout(new BoxLayout(promoteContainer, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel(new GridLayout(1, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // center horizontally

        JPanel choicesPanel = new JPanel(new GridLayout(1, 4));
        choicesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        choicesPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // center horizontally


        JLabel headerLabel = new JLabel("Choose promotion type:");
        headerPanel.add(headerLabel);

        Button queenButton = new Button("Queen");
        Button knightButton = new Button("Knight");
        Button rookButton = new Button("Rook");
        Button bishopButton = new Button("Bishop");

        queenButton.addActionListener(e -> {
            promoteType = 'Q';
            popup.dispose();
        });
        knightButton.addActionListener(e -> {
            promoteType = 'N';
            popup.dispose();
        });
        rookButton.addActionListener(e -> {
            promoteType = 'R';
            popup.dispose();
        });
        bishopButton.addActionListener(e -> {
            promoteType = 'B';
            popup.dispose();
        });

        choicesPanel.add(queenButton);
        choicesPanel.add(knightButton);
        choicesPanel.add(rookButton);
        choicesPanel.add(bishopButton);

        promoteContainer.add(headerPanel);
        promoteContainer.add(choicesPanel);

        popup.add(promoteContainer);
        popup.setLayout(new BoxLayout(popup.getContentPane(), BoxLayout.Y_AXIS));
        popup.pack();
        popup.setVisible(true);
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