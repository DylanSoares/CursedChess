package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.*;

import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ChessClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<String> moveQueue = new ArrayBlockingQueue<>(1);
        Board board = new Board();

        Piece[][] pieces = board.toPieceArray();

        // Create the UI for the chessboard
        ChessUI gui = new ChessUI(pieces, false, moveQueue);


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


        // Some awful temporary driver code for UI <-> Client communication
        int result = -1;
        while(true) {
            pieces = board.toPieceArray();
            //yes I made it flip for literally no reason, its just fun for this driver code
            gui.redrawBoard(pieces, !board.getWhoseTurn(), board.getWhoseTurn(), result);

            char[] moveCommand = moveQueue.take().toCharArray(); // this is blocking!

            char x1  = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[0]))+1);
            char x2  = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[2]))+1);
            int y1 = Integer.parseInt(String.valueOf(moveCommand[1]))+1;
            int y2 = Integer.parseInt(String.valueOf(moveCommand[3]))+1;

            result = board.move(new Location(x1, y1), new Location(x2, y2));
            System.err.println("[Client:55] DEBUG: Attempted to move to: " + x1 + "" + y1 + " to " + x2 + "" + y2);
            System.err.println("[Client:57] DEBUG: Moving result " + result);
        }

    }
}
