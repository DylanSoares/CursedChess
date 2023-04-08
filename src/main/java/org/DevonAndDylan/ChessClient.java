package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.*;

import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ChessClient {

    public static void main(String[] args) throws IOException {
        Board board = new Board();
//    	System.out.println(board);

        Piece[][] pieces = board.toPieceArray();

        // Create the UI for the chessboard
        ChessUI gui_White = new ChessUI(pieces, false);
        ChessUI gui_Black = new ChessUI(pieces, true);

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

        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('b', 1), new Location('c', 3)));
        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('d', 8), new Location('d', 3)));
        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('c', 1), new Location('g', 5)));
        System.out.println("[Client:36] DEBUG: Moving result " + board.move(new Location('a', 8), new Location('a', 4)));
        pieces = board.toPieceArray();
        
        gui_Black.redrawUI(pieces, true);
        gui_White.redrawUI(pieces, false);
//        gui.rotateBoard(pieces);
    }
}
