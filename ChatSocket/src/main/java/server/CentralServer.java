package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CentralServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> connectedClients;

    public CentralServer() throws IOException {
        try {
            serverSocket = new ServerSocket(7778);
            connectedClients = new ArrayList<>();
            System.out.println("Chat server in ascolto sulla porta 7778...");
        } catch (IOException e) {
            System.err.println("Impossibile avviare il chat server sulla porta 7778: " + e.getMessage());
            throw e;
        }
    }

    public void startServer() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connesso al chat server: " + clientSocket.getInetAddress());
                
                ClientHandler handler = new ClientHandler(clientSocket, this);
                connectedClients.add(handler);
                Thread clientThread = new Thread(handler);
                clientThread.start();
                
            } catch (IOException e) {
                System.err.println("Errore nell'accettare il client: " + e.getMessage());
            }
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : connectedClients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        connectedClients.remove(client);
        System.out.println("Client disconnesso. Clients rimanenti: " + connectedClients.size());
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private CentralServer server;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket, CentralServer server) {
            this.socket = socket;
            this.server = server;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // First message should be the username
                username = in.readLine();
                System.out.println("User " + username + " connected to chat");

                // Send welcome message
                out.println("SERVER: Benvenuto " + username + "!");

                // Listen for messages
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(username + ": " + message);
                    server.broadcastMessage(username + ": " + message, this);
                }

            } catch (IOException e) {
                System.err.println("Errore nella gestione del client: " + e.getMessage());
            } finally {
                cleanup();
            }
        }

        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }

        private void cleanup() {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null && !socket.isClosed()) socket.close();
                server.removeClient(this);
            } catch (IOException e) {
                System.err.println("Errore durante la chiusura: " + e.getMessage());
            }
        }
    }

    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Chat server arrestato.");
            }
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del server: " + e.getMessage());
        }
    }
}
