package main;

import server.AccessServerLogic;

import java.io.IOException;

public class AccessServerMain {
    public static void main(String[] args) {
        try {
            AccessServerLogic server = new AccessServerLogic();
            System.out.println("Starting server...");
            server.startServer();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
