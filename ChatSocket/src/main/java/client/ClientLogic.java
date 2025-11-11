package client;

import gui.MainFrame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Socket p2pChatSocket;
    private PrintWriter p2pChatOut;
    private BufferedReader p2pChatIn;
    private boolean isModerator = false;
    private ServerSocket ownServerSocket;
    private String ownLogFileName;
    private final Map<String, MessageListener> chatListeners = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

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
            this.username = username;
            this.psw = psw;
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
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    listenForMessages();
                }
            });
            return true;
        } catch (IOException e) {
            System.err.println("Impossibile connettersi al Server Centrale: " + e.getMessage());
            throw e;
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = chatIn.readLine()) != null) {
                System.out.println("Ricevuto (centrale): " + message);
            }
        } catch (IOException e) {
            System.err.println("Errore nella ricezione messaggi (centrale): " + e.getMessage());
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

    public void closeP2PResources() {
        try {
            if (p2pChatOut != null) p2pChatOut.close();
            if (p2pChatIn != null) p2pChatIn.close();
            if (p2pChatSocket != null && !p2pChatSocket.isClosed()) p2pChatSocket.close();
            if (ownServerSocket != null && !ownServerSocket.isClosed()) {
                ownServerSocket.close();
                System.out.println("Server locale chiuso.");
            }
        } catch (IOException e) {
            System.err.println("Errore durante la Chiusura delle Risorse P2P: " + e.getMessage());
        }
    }

    public void closeAllResources() {
        executor.shutdown();
        closeAccessResources();
        closeChatResources();
        closeP2PResources();
    }

    public PrintWriter getChatOut() {
        return chatOut;
    }

    public BufferedReader getChatIn() {
        return chatIn;
    }

    private String deriveKey(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        try {
            String firstChar = String.valueOf(username.charAt(0));
            String lastChar = String.valueOf(username.charAt(username.length() - 1));
            String keyMaterial = firstChar + lastChar;
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(keyMaterial.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(key);
        } catch (Exception e) {
            System.err.println("Errore nella derivazione della chiave: " + e.getMessage());
            return null;
        }
    }

    public void createOtcChatSession(int port, MainFrame mainFrame) throws IOException {
        if (this.username == null || this.username.length() < 2) {
            System.err.println("Errore: Username del moderatore troppo corto per creare la chat P2P.");
            javax.swing.JOptionPane.showMessageDialog(null, "Il tuo username è troppo corto per creare questa chat (minimo 2 caratteri).", "Errore Username", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        ownServerSocket = new ServerSocket(port);
        System.out.println("Chat OTC in ascolto su porta: " + port);
        isModerator = true;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("In attesa di connessioni OTC su porta " + port + "...");
                    while (!ownServerSocket.isClosed()) {
                        Socket connectionSocket = ownServerSocket.accept();
                        System.out.println("Nuovo client connesso alla chat OTC su localhost:" + port);
                        ChatClientHandler handler = new ChatClientHandler(connectionSocket, null, username, null, mainFrame, "Chat_OTC_" + port);
                        executor.submit(handler);
                    }
                } catch (IOException e) {
                    if (!ownServerSocket.isClosed()) {
                        System.err.println("Errore durante l'attesa o la gestione delle connessioni OTC: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
        startP2PConnection("localhost", port, true, mainFrame, "Chat_OTC_" + port);
    }

    public void createNonOtcChatSession(int port, String logFileName, String configFileName, String passKey, MainFrame mainFrame) throws IOException {
        if (this.username == null) {
            System.err.println("Errore: Username del moderatore troppo corto per creare la chat P2P.");
            javax.swing.JOptionPane.showMessageDialog(null, "Il tuo username è inesistente.", "Errore Username", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        ownServerSocket = new ServerSocket(port);
        ownLogFileName = logFileName;
        System.out.println("Chat NON-OTC in ascolto su porta: " + port + ", logbook: " + logFileName + ", config: " + configFileName);
        isModerator = true;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("In attesa di connessioni NON-OTC su porta " + port + "...");
                    while (!ownServerSocket.isClosed()) {
                        Socket connectionSocket = ownServerSocket.accept();
                        System.out.println("Nuovo client richiedente connessione alla chat NON-OTC su localhost:" + port);
                        ChatClientHandler handler = new ChatClientHandler(connectionSocket, configFileName, username, logFileName, mainFrame, "Chat_NON_OTC_" + port);
                        executor.submit(handler);
                    }
                } catch (IOException e) {
                    if (!ownServerSocket.isClosed()) {
                        System.err.println("Errore durante l'attesa o la gestione delle connessioni NON-OTC: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
        startP2PConnection("localhost", port, true, mainFrame, "Chat_NON_OTC_" + port);
    }

    public boolean joinChat(String ip, int port, String passKey, MainFrame mainFrame, String chatName) {
        if (this.username == null || this.username.isEmpty()) {
            System.err.println("Errore: Username non valido (nullo o vuoto) per connettersi alla chat P2P.");
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Il tuo username non è valido. Impossibile connettersi alla chat.",
                    "Errore Username",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        try {
            Socket chatSocket = new Socket(ip, port);
            System.out.println("Connesso al moderatore su " + ip + ":" + port);
            PrintWriter outToModerator = new PrintWriter(chatSocket.getOutputStream(), true);
            BufferedReader inFromModerator = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));

            outToModerator.println("PASSKEY_REQUEST:" + passKey);

            String response = inFromModerator.readLine();
            if ("true".equals(response)) {
                System.out.println("=== Connessione accettata dal moderatore. Connessione P2P stabilita. ===");
                this.p2pChatSocket = chatSocket;
                this.p2pChatOut = outToModerator;
                this.p2pChatIn = inFromModerator;
                this.isModerator = false;
                mainFrame.addChatTab(chatName, this);
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String p2pMessage;
                            while ((p2pMessage = inFromModerator.readLine()) != null) {
                                System.out.println("Messaggio P2P ricevuto: " + p2pMessage);
                                MessageListener listener = chatListeners.get(chatName);
                                if (listener != null) {
                                    listener.onMessageReceived(p2pMessage);
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Errore lettura messaggi P2P: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
                outToModerator.println("Ciao dal client " + this.username + "!");
                System.out.println("=== RETURN: true ===");
                return true;
            } else {
                System.out.println("=== Connessione rifiutata dal moderatore o passkey errata. ===");
                chatSocket.close();
                System.out.println("=== RETURN: false ===");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Errore durante la connessione alla chat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void startP2PConnection(String ip, int port, boolean isModeratorConnection, MainFrame mainFrame, String chatName) {
        try {
            p2pChatSocket = new Socket(ip, port);
            System.out.println("Connesso alla propria chat su " + ip + ":" + port + (isModeratorConnection ? " (come moderatore)." : " (come client esterno)."));
            p2pChatOut = new PrintWriter(p2pChatSocket.getOutputStream(), true);
            p2pChatIn = new BufferedReader(new InputStreamReader(p2pChatSocket.getInputStream()));
            mainFrame.addChatTab(chatName, this);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        String p2pMessage;
                        while ((p2pMessage = p2pChatIn.readLine()) != null) {
                            System.out.println("Messaggio P2P ricevuto: " + p2pMessage);
                            MessageListener listener = chatListeners.get(chatName);
                            if (listener != null) {
                                listener.onMessageReceived(p2pMessage);
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Errore lettura messaggi P2P (" + (isModeratorConnection ? "moderatore" : "client") + "): " + e.getMessage());
                        if (isModerator && isModeratorConnection) {
                            System.out.println("Il moderatore ha perso la connessione. Chiudendo la chat P2P...");
                            closeP2PResources();
                            isModerator = false;
                        }
                    }
                }
            });
            if (isModeratorConnection) {
                p2pChatOut.println("SERVER: Benvenuto nella chat (creata da te)!");
                System.out.println("Messaggio inviato come moderatore.");
            }
        } catch (IOException e) {
            System.err.println("Errore durante la connessione alla propria chat (" + (isModeratorConnection ? "moderatore" : "client") + "): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isModeratorOfP2PChat() {
        return isModerator && p2pChatSocket != null && p2pChatSocket.isConnected() && !p2pChatSocket.isClosed();
    }

    public void exitP2PChat() {
        if (isModeratorOfP2PChat()) {
            System.out.println("Moderatore esce dalla chat. Chiudendo la chat P2P e il proprio server...");
            closeP2PResources();
            isModerator = false;
            System.out.println("Chat P2P chiusa.");
        } else {
            System.out.println("Non sei il moderatore di una chat P2P attiva.");
        }
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    public void registerP2PMessageListener(String chatName, MessageListener listener) {
        chatListeners.put(chatName, listener);
    }

    public void sendP2PMessage(String message) {
        if (p2pChatOut != null) {
            p2pChatOut.println(message);
        }
    }

    public boolean isP2PConnected() {
        return p2pChatSocket != null && p2pChatSocket.isConnected() && !p2pChatSocket.isClosed();
    }

    private class ChatClientHandler implements Runnable {
        private final Socket clientSocket;
        private final String configFileName;
        private final String moderatorUsername;
        private final String logFileName;
        private final MainFrame mainFrame;
        private final String chatName;

        public ChatClientHandler(Socket socket, String configFileName, String moderatorUsername, String logFileName, MainFrame mainFrame, String chatName) {
            this.clientSocket = socket;
            this.configFileName = configFileName;
            this.moderatorUsername = moderatorUsername;
            this.logFileName = logFileName;
            this.mainFrame = mainFrame;
            this.chatName = chatName;
        }

        @Override
        public void run() {
            try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String requestLine = inFromClient.readLine();
                if (requestLine != null && requestLine.startsWith("PASSKEY_REQUEST:")) {
                    String receivedPassKey = requestLine.substring("PASSKEY_REQUEST:".length());

                    String storedPassKey = null;
                    if (configFileName != null) {
                        try (BufferedReader configReader = new BufferedReader(new FileReader(configFileName))) {
                            String line;
                            while ((line = configReader.readLine()) != null) {
                                if (line.startsWith("PASSKEY:")) {
                                    storedPassKey = line.substring("PASSKEY:".length());
                                    break;
                                }
                            }
                        }
                    }

                    boolean isValid = (configFileName == null) || (storedPassKey != null && storedPassKey.equals(receivedPassKey));

                    outToClient.println(String.valueOf(isValid));

                    if (isValid) {
                        System.out.println("Client connesso correttamente.");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        if (logFileName != null) {
                            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(logFileName, true))) {
                                String timestampedWelcome = "[" + LocalDateTime.now().format(formatter) + "] CLIENT: Connesso correttamente.";
                                writer.write(timestampedWelcome);
                                writer.newLine();
                            } catch (IOException e) {
                                System.err.println("Errore scrittura logbook (connessione): " + e.getMessage());
                            }
                        }
                        MessageListener listener = chatListeners.get(chatName);
                        if (listener != null) {
                            listener.onMessageReceived("SERVER: Nuovo client esterno connesso.");
                        }
                        String p2pMessage;
                        while ((p2pMessage = inFromClient.readLine()) != null) {
                            System.out.println("Messaggio P2P ricevuto dal client: " + p2pMessage);
                            for (MessageListener l : chatListeners.values()) {
                                l.onMessageReceived(p2pMessage);
                            }
                            if (logFileName != null) {
                                try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(logFileName, true))) {
                                    String timestampedMessage = "[" + LocalDateTime.now().format(formatter) + "] CLIENT: " + p2pMessage;
                                    writer.write(timestampedMessage);
                                    writer.newLine();
                                } catch (IOException e) {
                                    System.err.println("Errore scrittura logbook (messaggio): " + e.getMessage());
                                }
                            }
                        }
                    } else {
                        System.out.println("Client rifiutato: passkey non corretta.");
                    }
                } else {
                    System.out.println("Connessione ricevuta ma nessun messaggio di richiesta passkey inviato immediatamente.");
                }
            } catch (Exception e) {
                System.err.println("Errore nella gestione del client come moderatore: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Connessione con il client richiedente chiusa.");
                } catch (IOException e) {
                    System.err.println("Errore chiusura socket client: " + e.getMessage());
                }
            }
        }
    }
}