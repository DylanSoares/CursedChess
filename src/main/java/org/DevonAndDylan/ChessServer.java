package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.Board;
import org.DevonAndDylan.Pieces.Location;
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

        ObjectOutputStream out1 = new ObjectOutputStream(client1.getOutputStream());

        ObjectOutputStream out2 = new ObjectOutputStream(client2.getOutputStream());

        ObjectInputStream in1 = new ObjectInputStream(client1.getInputStream());
        ObjectInputStream in2 = new ObjectInputStream(client2.getInputStream());

        //playersChoice
        out1.writeObject(false); // player one is white
        out2.writeObject(true); // player two is black


        // Initially deliver the board to clients
        pieces = board.toPieceArray();
        out1.writeObject(pieces);
        out2.writeObject(pieces);

//         Wait for messages from the clients and forward them to the other client
        while (true) {
            // Write pieces from board to the client
            boolean isWhiteTurn = board.getWhoseTurn();
            boolean promotionNeeded = board.isPromote();

            pieces = board.toPieceArray();
            //Pieces
            out1.writeObject(pieces);
            out2.writeObject(pieces);
            //Promotion
            out1.writeObject(promotionNeeded);
            out2.writeObject(promotionNeeded);
            //isWhiteTurn
            out1.writeObject(isWhiteTurn);
            out2.writeObject(isWhiteTurn);


            if (isWhiteTurn) {
                out1.writeObject(true);
                out2.writeObject(false);
                if (promotionNeeded) {
                    char promotion = (char) in1.readObject();
                    board.promote(promotion);
                } else {
                    int result = makeMove(board, in1);
//                    System.err.println("Result of move in1: " + result);
                    out1.writeObject(result);
                }
                out2.writeObject("Other player has finished their turn.");
            } else {
                out1.writeObject(false);
                out2.writeObject(true);
                if (promotionNeeded) {
                    //TODO implement client and server promotion
                    char promotion = (char) in2.readObject();
                    board.promote(promotion);
                } else {
                    int result = makeMove(board, in2);
//                    System.err.println("Result of move in2: " + result);
                    out2.writeObject(result);
                }
                out1.writeObject(null);
            }
        }
//        client1.close();
//        client2.close();
//        serverSocket.close();
    }

    private static int makeMove(Board board, ObjectInputStream in2) throws IOException, ClassNotFoundException {
        char[] moveCommand = (char[]) in2.readObject();

        char x1 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[0])) + 1);
        char x2 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[2])) + 1);
        int y1 = Integer.parseInt(String.valueOf(moveCommand[1])) + 1;
        int y2 = Integer.parseInt(String.valueOf(moveCommand[3])) + 1;

        return board.move(new Location(x1, y1), new Location(x2, y2));
    }
}
