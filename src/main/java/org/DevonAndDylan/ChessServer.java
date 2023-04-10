package org.DevonAndDylan;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);

        //waits for connection

        Socket clientSocket = serverSocket.accept(); // this blocks so no other client can connect now.
        //itll need to be threaded, and somehow we will have to make these communicate.

        InputStream inputStream = clientSocket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        System.out.println(dataInputStream.readUTF());
        serverSocket.close();
        clientSocket.close();
    }
}
