package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.*;

//import java.util.ArrayList;
//import java.io.DataOutputStream;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
//import java.io.OutputStream;
//import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ChessClient {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException {
        BlockingQueue<String> serverInfoQueue = new ArrayBlockingQueue<>(2);
        BlockingQueue<String> moveQueue = new ArrayBlockingQueue<>(1);
        BlockingQueue<Character> promoteQueue = new ArrayBlockingQueue<>(1);

        new LauncherUI(serverInfoQueue);

        String serverIP = serverInfoQueue.take();
        int serverPort = Integer.parseInt(serverInfoQueue.take());
        try {
            Socket socket = new Socket(serverIP, serverPort);

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            boolean playersChoice = (boolean) input.readObject();

            Piece[][] pieces = (Piece[][]) input.readObject();

            ChessUI gui = new ChessUI(pieces, playersChoice, moveQueue, promoteQueue);


            int result = -1;
            //noinspection InfiniteLoopStatement
            while (true) {
                //Pieces
                pieces = (Piece[][]) input.readObject();
                //Promotion
                boolean needPromotion = (boolean) input.readObject();
                //isWhiteTurn
                boolean isWhiteTurn = (boolean) input.readObject();

                gui.redrawBoard(pieces, playersChoice, isWhiteTurn, result, needPromotion);

                boolean isClientsTurn = (boolean) input.readObject();
                if (isClientsTurn) { // I can go!
                    if (needPromotion) {
                        //TODO implement client and server promotion
                        output.writeObject(promoteQueue.take());
//                board.promote(promoteQueue.take());
                    } else {
                        char[] moveCommand = moveQueue.take().toCharArray(); // this is blocking!
                        output.writeObject(moveCommand);
                        result = (int) input.readObject();
                    }
                } else {
                    input.readObject();
                }
            }
        } catch (ConnectException e) {
            drawPopup("Connection Refused", "<html>Provided server info may be incorrect.<br>Please try again.</html>");
        }
    }

    static private void drawPopup(String title, String message) {
        JFrame frame = new JFrame();
        final Font arial = new Font("Arial", Font.PLAIN, 20);

        JDialog popup = new JDialog(frame, title, true);

        popup.setResizable(false);
        popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel headerPanel = new JPanel(new GridLayout(1, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // center horizontally

        JLabel headerLabel = new JLabel(message);
        headerLabel.setFont(arial);
        headerPanel.add(headerLabel);

        Button continueButton = new Button("Continue");

        continueButton.addActionListener(e -> {
            popup.dispose();
            System.exit(1);
        });

        popup.add(headerPanel);
        popup.add(continueButton);
        popup.setLayout(new BoxLayout(popup.getContentPane(), BoxLayout.Y_AXIS));
        popup.pack();
        popup.setVisible(true);
    }
}
