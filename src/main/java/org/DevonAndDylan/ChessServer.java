package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.Board;
import org.DevonAndDylan.Pieces.Piece;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket serverSocket = new ServerSocket(7777);

        Board board = new Board();

        Piece[][] pieces;

        // Wait for two clients to connect
        Socket client1 = serverSocket.accept();
        System.out.println("Client 1 connected");
        Socket client2 = serverSocket.accept();
        System.out.println("Client 2 connected");

        // Create input and output streams for each client
        ObjectInputStream in1 = new ObjectInputStream(client1.getInputStream());
        ObjectOutputStream out1 = new ObjectOutputStream(client1.getOutputStream());
        ObjectInputStream in2 = new ObjectInputStream(client2.getInputStream());
        ObjectOutputStream out2 = new ObjectOutputStream(client2.getOutputStream());

//        out1.writeObject((Boolean)false); // player one is white
//        out2.writeObject((Boolean)true); // player two is black


        // Initially deliver the board to clients
        pieces = board.toPieceArray();
        out1.writeObject(pieces);
        out2.writeObject(pieces);

        // Wait for messages from the clients and forward them to the other client
        while (true) {
            // Write pieces from board to the client
            boolean isWhiteTurn = board.getWhoseTurn();
            boolean promotionNeeded = board.isPromote();

            pieces = board.toPieceArray();
            out1.writeObject(pieces);
            out2.writeObject(pieces);
            out1.writeObject(promotionNeeded);
            out2.writeObject(promotionNeeded);
            out1.writeObject(isWhiteTurn);
            out2.writeObject(isWhiteTurn);
            out1.writeObject(board.toPieceArray());
            out2.writeObject(board.toPieceArray());

            out1.flush();
            out2.flush();

        }
    }
}
