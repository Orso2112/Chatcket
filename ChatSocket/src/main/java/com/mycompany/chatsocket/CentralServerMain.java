package com.mycompany.chatsocket;

import server.CentralServer;
import java.io.IOException;


public class CentralServerMain {
    public static void main(String[] args) {
        try {
            CentralServer chatServer = new CentralServer();
            System.out.println("Starting chat server on port 7778...");
            chatServer.startServer();
        } catch (IOException e) {
            System.err.println("Failed to start chat server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
