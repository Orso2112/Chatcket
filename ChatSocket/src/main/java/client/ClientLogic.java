package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientLogic {
    public static final int ACCESS_PORT = 7777;
    public static final int CHAT_PORT = 7778;
    public static final String SERVER = "127.0.0.1";
    public String nome;
    
    private String username;
    private String psw;

    Socket accessSock;
    PrintWriter accessOut;
    BufferedReader accessIn;

    Socket chatSock;
    PrintWriter chatOut;
    BufferedReader chatIn;


    public ClientLogic(String username, String psw) throws IOException {
        this.username = username;
        this.psw = psw;

        try {
            accessSock = new Socket(SERVER, ACCESS_PORT);
            System.out.println("Connesso al server di accesso");
            
            accessOut = new PrintWriter(accessSock.getOutputStream(), true);
            accessIn = new BufferedReader(new InputStreamReader(accessSock.getInputStream()));
        } catch (IOException e) {
            System.err.println("Impossibile connettersi al server su " + SERVER + ":" + ACCESS_PORT);
            System.err.println("Assicurarsi che il server sia in esecuzione.");
            closeAccessResources();
            throw e;
        }
    }

    public String login(String username, String psw) throws IOException {
        accessOut.println("l:" + username + ":" + psw);
        String response = accessIn.readLine();

        if (response != null && response.startsWith("SUCCESS")) {
            try {
                connectToChatServer(username);
            } catch (IOException e) {
                return "ERROR: Fallimento nella Connessione al Server Centrale";
            }
        }
        
        return response;
    }

    public String register(String username, String psw) throws IOException {
        accessOut.println("u:" + username + ":" + psw);
        String response = accessIn.readLine();
        return response;
    }
    
    public boolean connectToChatServer(String username) throws IOException {
        try {
            chatSock = new Socket(SERVER, CHAT_PORT);
            chatOut = new PrintWriter(chatSock.getOutputStream(), true);
            chatIn = new BufferedReader(new InputStreamReader(chatSock.getInputStream()));

            chatOut.println(username);
            
            System.out.println("Connesso al Server Chat sulla Porta " + CHAT_PORT);
            nome = username;
            return true;
        } catch (IOException e) {
            System.err.println("Impossibile connettersi al Server Centrale: " + e.getMessage());
            throw e;
        }
    }
    
    public boolean isConnectedToChat() {
        return chatSock != null && chatSock.isConnected() && !chatSock.isClosed();
    }

    public void closeAccessResources() {
        try {
            if (accessOut != null) accessOut.close();
            if (accessIn != null) accessIn.close();
            if (accessSock != null && !accessSock.isClosed()) accessSock.close();
        } catch (IOException e) {
            System.err.println("Errore durante la Chiusura delle Risorse: " + e.getMessage());
        }
    }
    
    public void closeChatResources() {
        try {
            if (chatOut != null) chatOut.close();
            if (chatIn != null) chatIn.close();
            if (chatSock != null && !chatSock.isClosed()) chatSock.close();
        } catch (IOException e) {
            System.err.println("Errore durante la Chiusura delle Risorse: " + e.getMessage());
        }
    }
    
    public void closeAllResources() {
        closeAccessResources();
        closeChatResources();
    }

    public PrintWriter getChatOut() {
        return chatOut;
    }

    public BufferedReader getChatIn() {
        return chatIn;
    }
}
