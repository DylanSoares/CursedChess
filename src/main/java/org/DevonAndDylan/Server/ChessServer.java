package org.DevonAndDylan.Server;

import org.DevonAndDylan.Pieces.Board;
import org.DevonAndDylan.Pieces.Location;
import org.DevonAndDylan.Pieces.Piece;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ChessServer {
    static ServerSocket serverSocket;
    private static final BlockingQueue<String> serverInfoQueue = new ArrayBlockingQueue<>(2);

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        ServerUI gui = new ServerUI(serverInfoQueue);

        String ip = serverInfoQueue.take();
        int port = Integer.parseInt(serverInfoQueue.take());

        serverSocket = new ServerSocket(port, 50, InetAddress.getByName(ip));


        Board board = new Board();

        Piece[][] pieces;

        // TODO get server to restart from UI
        //      And get server to handle server not dying
        // Wait for two clients to connect
        Socket client1 = serverSocket.accept();
        gui.sendLogMsg("Client 1 Connected from " + client1.getRemoteSocketAddress().toString() + " as WHITE");

        Socket client2 = serverSocket.accept();
        gui.sendLogMsg("Client 2 Connected from " + client1.getRemoteSocketAddress().toString() + " as BLACK");

        try {
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
            //noinspection InfiniteLoopStatement
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
                        gui.sendLogMsg("White has promoted their pawn to a " + convertPromoteToString(promotion));
                    } else {
                        char[] moveCommand = (char[]) in1.readObject();

                        char x1 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[0])) + 1);
                        char x2 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[2])) + 1);
                        int y1 = Integer.parseInt(String.valueOf(moveCommand[1])) + 1;
                        int y2 = Integer.parseInt(String.valueOf(moveCommand[3])) + 1;
                        gui.sendLogMsg("White has moved " + x1 + "" + y1 + " to " + x2 + "" + y2);

                        int result = board.move(new Location(x1, y1), new Location(x2, y2));
                        out1.writeObject(result);
                    }
                    out2.writeObject(null); // lift blocking and signal turn done
                } else {
                    out1.writeObject(false);
                    out2.writeObject(true);
                    if (promotionNeeded) {
                        char promotion = (char) in2.readObject();
                        board.promote(promotion);
                        gui.sendLogMsg("Black has promoted their pawn to a " + convertPromoteToString(promotion));
                    } else {

                        char[] moveCommand = (char[]) in2.readObject();

                        char x1 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[0])) + 1);
                        char x2 = Board.toChar(Integer.parseInt(String.valueOf(moveCommand[2])) + 1);
                        int y1 = Integer.parseInt(String.valueOf(moveCommand[1])) + 1;
                        int y2 = Integer.parseInt(String.valueOf(moveCommand[3])) + 1;
                        gui.sendLogMsg("Black has moved " + x1 + "" + y1 + " to " + x2 + "" + y2);

                        int result = board.move(new Location(x1, y1), new Location(x2, y2));
                        out2.writeObject(result);
                    }
                    out1.writeObject(null); // lift blocking and signal turn done
                }
            }
        } catch (SocketException ignored) {

        } finally {
            client1.close();
            client2.close();
            serverSocket.close();
        }
    }

    private static String convertPromoteToString(char promote) {
        String output;
        switch (promote) {
            case 'Q' -> output = "Queen";
            case 'N' -> output = "Knight";
            case 'R' -> output = "Rook";
            case 'B' -> output = "Bishop";
            default -> output = "ERROR";
        }
        return output;
    }
}
