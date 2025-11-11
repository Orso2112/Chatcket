package server;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

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
                    String[] split = credenziali.substring(2).split(":", 2);
                    if (split.length == 2) {
                        String username = split[0];
                        String password = split[1];
                        newAcc(username, password, out);
                    } else {
                        out.println("ERROR: Formato Invalido");
                    }
                } else if (credenziali.startsWith("l:")) {
                    String[] split = credenziali.substring(2).split(":", 2);
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
                System.err.println("Errore nella Chiusura del Socket: " + e.getMessage());
            }
        }
    }

    private String deriveKey(String username) throws Exception {
        if (username == null || username.length() < 2) {
             throw new IllegalArgumentException("Username troppo corto per derivare la chiave.");
        }
        String firstChar = String.valueOf(username.charAt(0));
        String lastChar = String.valueOf(username.charAt(username.length() - 1));
        String keyMaterial = firstChar + lastChar;
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(keyMaterial.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(key);
    }

    private String encrypt(String plainText, String key64) throws Exception {
        byte[] key = Base64.getDecoder().decode(key64);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(iv); // Genera un IV casuale
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
        System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(encryptedWithIv);
    }

    private String decrypt(String encryptedText, String key64) throws Exception {
        byte[] key = Base64.getDecoder().decode(key64);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] encryptedWithIv = Base64.getDecoder().decode(encryptedText);
        byte[] iv = new byte[cipher.getBlockSize()];
        System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        byte[] encrypted = new byte[encryptedWithIv.length - iv.length];
        System.arraycopy(encryptedWithIv, iv.length, encrypted, 0, encrypted.length);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, "UTF-8");
    }

    public void newAcc(String username, String password, PrintWriter out) {
        File userFile = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":", 2);
                if (credentials.length >= 1 && credentials[0].equals(username)) {
                    out.println("ERROR: Username Gia' Esistente");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            out.println("ERROR: ERRORE NELLA RICERCA DELL' USERNAME");
            return;
        }

        String encryptedPassword;
        try {
            String key64 = deriveKey(username);
            encryptedPassword = encrypt(password, key64);
        } catch (Exception e) {
            out.println("ERROR: Errore nella generazione della chiave o crittografia");
            System.err.println("Errore crittografia registrazione: " + e.getMessage());
            return;
        }

        try (FileWriter fw = new FileWriter(userFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(username + ":" + encryptedPassword);
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
                String[] credentials = line.split(":", 2);
                if (credentials.length >= 2 && credentials[0].equals(username)) {
                    String storedEncryptedPassword = credentials[1];
                    String key64;
                    String decryptedPassword;
                    try {
                        key64 = deriveKey(username);
                        decryptedPassword = decrypt(storedEncryptedPassword, key64);
                    } catch (Exception e) {
                         out.println("ERROR: Errore nella verifica delle credenziali (decrittografia)");
                         System.err.println("Errore decrittografia login: " + e.getMessage());
                         return;
                    }
                    if (decryptedPassword.equals(password)) {
                        out.println("SUCCESS: Login Avvenuto con Successo");
                        System.out.println("Client " + username + " Login Riuscito");
                        return;
                    } else {
                        out.println("ERROR: Credenziali Invalide");
                        return;
                    }
                }
            }
            out.println("ERROR: Credenziali Invalide");
        } catch (FileNotFoundException e) {
            out.println("ERROR: Nessun utente registrato");
        } catch (Exception e) {
            out.println("ERROR: Errore nella verifica delle credenziali");
            System.err.println("Error checking credentials: " + e.getMessage());
        }
    }
}