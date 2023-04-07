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
    	System.out.println(board);

        // Create the UI for the chessboard
        new ChessUI();
        // TODO catch when the client closes the UI, should close the sockets and such.
        //      and disconnect the game/forfeit etc.

        ArrayList<Piece> pieces = board.getPieces();
        System.out.println(pieces.get(0).getLoc());

//        just gonna put this here
        String move = "e4 Nc3";

        Socket socket = new Socket("localhost", 7777);
        System.out.println("DEBUG: Connected to server.");

        OutputStream outputStream = socket.getOutputStream(); // get the output stream from the socket.
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        // write the message we want to send
        dataOutputStream.writeUTF(move);
        dataOutputStream.flush(); // send the message
        dataOutputStream.close(); // close the output stream when we're done.
        socket.close();
    }
}
