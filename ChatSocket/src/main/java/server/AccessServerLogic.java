package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class AccessServerLogic {
    PrintWriter out;
    BufferedReader in;
    ServerSocket serverSock;


    public AccessServerLogic() throws IOException {
        try {
            serverSock = new ServerSocket(7777);
            System.out.println("Server in ascolto sulla porta 7777...");
        } catch (IOException e) {
            System.err.println("Impossibile avviare il server sulla porta 7777: " + e.getMessage());
            throw e;
        }
    }

    public void startServer() {
        while (true) {
            try {
                Socket clientSocket = serverSock.accept();
                System.out.println("Client connesso: " + clientSocket.getInetAddress());
            
                AccessHandler handler = new AccessHandler(clientSocket);
                Thread clientThread = new Thread(handler);
                clientThread.start();
            
            } catch (IOException e) {
                System.err.println("Errore nell'accettare il client: " + e.getMessage());
            }
        }
    }

    public void shutdown() {
        try {
            if (serverSock != null && !serverSock.isClosed()) {
                serverSock.close();
                System.out.println("Server arrestato.");
            }
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del server: " + e.getMessage());
        }
    }
}
