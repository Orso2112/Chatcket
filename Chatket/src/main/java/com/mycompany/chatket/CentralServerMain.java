package com.mycompany.chatket;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class CentralServerMain {
    private final List<PrintWriter> clients = new ArrayList<PrintWriter>();
    public static void main(String[] args) throws Exception {
        CentralServerMain server = new CentralServerMain();
        server.start(7778);
    }
    public void start(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, this);
            Thread t = new Thread(handler);
            t.start();
            System.out.println("Server centrale in ascolto su pota 7778...");
        }
    }
    public void addClient(PrintWriter out) {
        synchronized (clients) {
            clients.add(out);
        }
    }
    public void removeClient(PrintWriter out) {
        synchronized (clients) {
            clients.remove(out);
        }
    }
    public void broadcast(String protocolLine) {
        synchronized (clients) {
            for (PrintWriter out : new ArrayList<PrintWriter>(clients)) {
                out.println(protocolLine);
                out.flush();
            }
        }
    }
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final CentralServerMain server;
        private PrintWriter out;
        public ClientHandler(Socket socket, CentralServerMain server) {
            this.socket = socket;
            this.server = server;
        }
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                server.addClient(out);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    server.broadcast(line);
                }
            } catch (Exception e) {
            } finally {
                if (out != null) {
                    server.removeClient(out);
                }
                try {
                    socket.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}