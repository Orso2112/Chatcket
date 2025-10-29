package server;

import java.io.*;
import java.net.Socket;

public class AccessHandler implements Runnable{
    private Socket client;

    public AccessHandler(Socket sock){
        this.client = sock;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            String credenziali;
            while ((credenziali = in.readLine()) != null) {
                System.out.println("Ricevuto: " + credenziali);
                
                if (credenziali.startsWith("u:")) {
                    String[] split = credenziali.substring(2).split(":");
                    if (split.length == 2) {
                        String username = split[0];
                        String password = split[1];

                        newAcc(username, password, out);
                    } else {
                        out.println("ERROR: Formato Invalido");
                    }
                } else if (credenziali.startsWith("l:")) {
                    String[] split = credenziali.substring(2).split(":");
                    if (split.length == 2) {
                        String username = split[0];
                        String password = split[1];

                        login(username, password, out);
                    } else {
                        out.println("ERROR: Formato Invalido");
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Errore nella Gestione Client: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Errore nella Chiusura del sScket: " + e.getMessage());
            }
        }
    }

    public void newAcc(String username, String password, PrintWriter out) {
        File userFile = new File("users.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username)) {
                    out.println("ERROR: Username Gia' Esistente");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            out.println("ERROR: ERRORE NELLA RICERCA DELL' USERNAME");
            return;
        }

        try (FileWriter fw = new FileWriter(userFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {


            pw.println(username + ":" + password);
            out.println("SUCCESS: Account Creato con Successo");
            System.out.println("Nuovo account creato: " + username);

        } catch (IOException e) {
            out.println("ERROR: Fallimento nella Creazione dell'Account");
            System.err.println("Errore nella Scrittura del File: " + e.getMessage());
        }
    }


    public void login(String username, String password, PrintWriter out) {
        File userFile = new File("users.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials.length >= 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    out.println("SUCCESS: Login Avvenuto con Successo");
                    System.out.println("Client " + username + " Login Riuscito");
                    
                    // TODO: Handle redirection to port 7778 when chat server is ready



                    return;
                }
            }
            out.println("ERROR: Credenziali Invalide");
        } catch (FileNotFoundException e) {
            out.println("ERROR: Nessun utente registrato");
        } catch (IOException e) {
            out.println("ERROR: Errore nella verifica delle credenziali");
            System.err.println("Error checking credentials: " + e.getMessage());
        }
    }

}