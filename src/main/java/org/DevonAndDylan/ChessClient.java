package org.DevonAndDylan;

import org.DevonAndDylan.Pieces.*;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ChessClient {

    public static void main(String[] args) throws IOException {

        //just gonna put this here
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
