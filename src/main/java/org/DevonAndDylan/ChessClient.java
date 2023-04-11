package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.*;

//import java.util.ArrayList;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.OutputStream;
//import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ChessClient {

    @SuppressWarnings({"CommentedOutCode", "RedundantThrows"})
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException {
        BlockingQueue<Boolean> choiceQueue = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> moveQueue = new ArrayBlockingQueue<>(1);
        BlockingQueue<Character> promoteQueue = new ArrayBlockingQueue<>(1);

        new LauncherUI(choiceQueue);
        boolean playersChoice = choiceQueue.take(); //false if white
        // TODO Implement choice from user for color (Server currently ignores this)

        Socket socket = new Socket("localhost", 7777);
        System.err.println("Connected to server");


        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

        playersChoice = (boolean) input.readObject();

        Piece[][] pieces = (Piece[][]) input.readObject();

        ChessUI gui = new ChessUI(pieces, playersChoice, moveQueue, promoteQueue);


        // Some awful temporary driver code for UI <-> Client communication
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
            if(isClientsTurn) { // I can go!
                if (needPromotion) {
                    //TODO implement client and server promotion
//                board.promote(promoteQueue.take());
                } else {
                    char[] moveCommand = moveQueue.take().toCharArray(); // this is blocking!
                    output.writeObject(moveCommand);
                    result = (int) input.readObject();
//                result = board.move(new Location(x1, y1), new Location(x2, y2));
                }
            }else{
                System.out.print(input.readObject());
            }
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

//        socket.close();
    }
}
