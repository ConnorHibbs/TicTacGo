package hibbscm;

import java.io.PrintWriter;
import java.util.*;

/**
 * @author Connor Hibbs
 */
public abstract class Connection {

    List<String> buffer;

    String ip;
    int port;

    private Collection<ResultReceiver> receivers;

    PrintWriter out = null;
    Scanner in = null;

    Thread server;

    public Connection(){
        receivers = new ArrayList<>();
        buffer = new LinkedList<>();
    }

    public void addReceiver(ResultReceiver receiver) {
        receivers.add(receiver);
    }

    public void send(String s) {
        writeln(s);
    }

    public void buffer(String s) {
        buffer.add(s);
    }

    public void flush() {
        buffer.forEach(this::writeln);
        buffer.clear();
    }

    private void write(String s) {
        out.print(s);
        receivers.forEach(r -> r.receiveMessage("PC", s));
        out.flush();
    }

    private void writeln(String s){
        write(s + "\n");
    }

    public abstract void start();

    protected class Listener implements Runnable {
        @Override
        public void run() {
            while(in.hasNextLine()) { //blocks waiting for new line
                String line = in.nextLine();
                receivers.forEach(r -> r.receiveMessage("Android", line));
            }
        }
    }

    public interface ResultReceiver {
        void receiveMessage(String from, String message);
    }
}
