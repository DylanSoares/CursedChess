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

        Piece[][] pieces = board.toPieceArray();

        // Create the UI for the chessboard
        ChessUI guiWhite = new ChessUI(pieces, false);
        ChessUI guiBlack = new ChessUI(pieces, true);

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

        guiWhite.redrawBoard(pieces, false, board.getWhoseTurn());
        guiBlack.redrawBoard(pieces, true, board.getWhoseTurn());

    }
}
