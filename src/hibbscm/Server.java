package hibbscm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class will open a connection to the local network and listen for a message
 * sent from an Android phone
 * @author Connor Hibbs
 */
public class Server extends Connection{

    private int port;

    public Server(int port){
        this.port = port;
    }

    public void start(){
        try {
            System.out.println("Opening connection to socket " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected");

            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream());

            server = new Thread(new Listener());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
