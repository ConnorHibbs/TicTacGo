package hibbscm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Connor Hibbs
 */
public class Client extends Connection{

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void start(){
        try {
            Socket socket = new Socket(ip, port);
            out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            in = new Scanner(new BufferedInputStream(socket.getInputStream()));

            server = new Thread(new Listener());
            server.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}