package client;

import com.mycompany.chatket.Message;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class ChatClient {
    private final String host;
    private final int port;
    private final String username;
    private PrintWriter out;
    private BufferedReader in;
    private final Set<String> sentIds = new HashSet<String>();
    private final ChatWindow gui;
    public ChatClient(String host, int port, String username, ChatWindow gui) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.gui = gui;
    }
    public void connect() throws Exception {
        Socket socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread reader = new Thread(new Runnable() {
            public void run() {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        Message msg = Message.fromProtocolString(line);
                        synchronized (sentIds) {
                            if (sentIds.contains(msg.id)) {
                                sentIds.remove(msg.id);
                                continue;
                            }
                        }
                        gui.appendMessage(msg.sender + ": " + msg.text);
                    }
                } catch (Exception e) {
                }
            }
        });
        reader.start();
    }
    public void sendText(String text) {
        String id = generateId();
        Message msg = new Message(id, username, text);
        String proto = msg.toProtocolString();
        synchronized (sentIds) {
            sentIds.add(id);
        }
        gui.appendMessage(username + ": " + text);
        out.println(proto);
        out.flush();
    }
    private String generateId() {
        long t = System.currentTimeMillis();
        int r = new Random().nextInt(1000000);
        return Long.toString(t) + "_" + Integer.toString(r);
    }
}
