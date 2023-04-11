package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.*;

//import java.util.ArrayList;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.OutputStream;
//import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ChessClient {

    @SuppressWarnings({"CommentedOutCode", "RedundantThrows"})
    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<Boolean> choiceQueue = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> moveQueue = new ArrayBlockingQueue<>(1);
        Board board = new Board();

        Piece[][] pieces = board.toPieceArray();

        new LauncherUI(choiceQueue);


        boolean playersChoice = choiceQueue.take(); //false if white
        ChessUI gui = new ChessUI(pieces, playersChoice, moveQueue);

        // Some awful temporary driver code for UI <-> Client communication
        int result = -1;
        //noinspection InfiniteLoopStatement
        while (true) {

            boolean needPromotion = board.isPromote(); //replace later with server call
            boolean whoseTurn = board.getWhoseTurn(); // replace later with server call (true if white)
            pieces = board.toPieceArray(); //replace later with server call

            gui.redrawBoard(pieces, playersChoice, whoseTurn, result, needPromotion);

            char[] moveCommand = moveQueue.take().toCharArray(); // this is blocking!

            char x1 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[0])) + 1);
            char x2 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[2])) + 1);
            int y1 = Integer.parseInt(String.valueOf(moveCommand[1])) + 1;
            int y2 = Integer.parseInt(String.valueOf(moveCommand[3])) + 1;

            result = board.move(new Location(x1, y1), new Location(x2, y2));
        }


        //        String move = "e4 Nc3";
//
//        Socket socket = new Socket("localhost", 7777);
//        System.out.println("DEBUG: Connected to server.");
//
//        OutputStream outputStream = socket.getOutputStream(); // get the output stream from the socket.
//        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//
//        // write the message we want to send
//        dataOutputStream.writeUTF(move);
//        dataOutputStream.flush(); // send the message
//        dataOutputStream.close(); // close the output stream when we're done.
//        socket.close();

//        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('b', 1), new Location('c', 3)));
//        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('d', 8), new Location('d', 3)));
//        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('c', 1), new Location('g', 5)));
//        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('a', 8), new Location('a', 4)));

    }
}
