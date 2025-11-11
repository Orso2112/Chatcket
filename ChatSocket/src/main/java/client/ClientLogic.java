package client;

import gui.MainFrame;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
    private final List<PrintWriter> p2pConnectedClients = new CopyOnWriteArrayList<>();

    public ClientLogic(String username, String psw) throws IOException {
        this.username = username;
        this.psw = psw;
        try {
            accessSock = new Socket(SERVER, ACCESS_PORT);
            accessOut = new PrintWriter(accessSock.getOutputStream(), true);
            accessIn = new BufferedReader(new InputStreamReader(accessSock.getInputStream()));
        } catch (IOException e) {
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
        return accessIn.readLine();
    }

    public boolean connectToChatServer(String username) throws IOException {
        try {
            chatSock = new Socket(SERVER, CHAT_PORT);
            chatOut = new PrintWriter(chatSock.getOutputStream(), true);
            chatIn = new BufferedReader(new InputStreamReader(chatSock.getInputStream()));
            chatOut.println(username);
            nome = username;
            executor.submit(this::listenForMessages);
            return true;
        } catch (IOException e) {
            throw e;
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = chatIn.readLine()) != null) {
            }
        } catch (IOException e) {
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
        }
    }

    public void closeChatResources() {
        try {
            if (chatOut != null) chatOut.close();
            if (chatIn != null) chatIn.close();
            if (chatSock != null && !chatSock.isClosed()) chatSock.close();
        } catch (IOException e) {
        }
    }

    public void closeP2PResources() {
        try {
            if (p2pChatOut != null) p2pChatOut.close();
            if (p2pChatIn != null) p2pChatIn.close();
            if (p2pChatSocket != null && !p2pChatSocket.isClosed()) p2pChatSocket.close();
            if (ownServerSocket != null && !ownServerSocket.isClosed()) {
                ownServerSocket.close();
            }
            p2pConnectedClients.clear();
        } catch (IOException e) {
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
            return null;
        }
    }

    public void createOtcChatSession(int port, MainFrame mainFrame) throws IOException {
        if (this.username == null || this.username.length() < 2) {
            javax.swing.JOptionPane.showMessageDialog(null, "Il tuo username è troppo corto per creare questa chat (minimo 2 caratteri).", "Errore Username", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        ownServerSocket = new ServerSocket(port);
        isModerator = true;
        p2pConnectedClients.clear();
        executor.submit(() -> {
            try {
                while (!ownServerSocket.isClosed()) {
                    Socket connectionSocket = ownServerSocket.accept();
                    ChatClientHandler handler = new ChatClientHandler(connectionSocket, null, username, null, mainFrame, "Chat_OTC_" + port);
                    executor.submit(handler);
                }
            } catch (IOException e) {
            }
        });
        startP2PConnection("localhost", port, true, mainFrame, "Chat_OTC_" + port);
    }

    public void createNonOtcChatSession(int port, String logFileName, String configFileName, String passKey, MainFrame mainFrame) throws IOException {
        if (this.username == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Il tuo username è inesistente.", "Errore Username", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        ownServerSocket = new ServerSocket(port);
        ownLogFileName = logFileName;
        isModerator = true;
        p2pConnectedClients.clear();
        executor.submit(() -> {
            try {
                while (!ownServerSocket.isClosed()) {
                    Socket connectionSocket = ownServerSocket.accept();
                    ChatClientHandler handler = new ChatClientHandler(connectionSocket, configFileName, username, logFileName, mainFrame, "Chat_NON_OTC_" + port);
                    executor.submit(handler);
                }
            } catch (IOException e) {
            }
        });
        startP2PConnection("localhost", port, true, mainFrame, "Chat_NON_OTC_" + port);
    }

    public boolean joinChat(String ip, int port, String passKey, MainFrame mainFrame, String chatName) {
        if (this.username == null || this.username.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Il tuo username non è valido. Impossibile connettersi alla chat.", "Errore Username", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Socket chatSocket = new Socket(ip, port);
            PrintWriter outToModerator = new PrintWriter(chatSocket.getOutputStream(), true);
            BufferedReader inFromModerator = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));

            outToModerator.println("PASSKEY_REQUEST:" + passKey);

            String response = inFromModerator.readLine();
            if ("true".equals(response)) {
                this.p2pChatSocket = chatSocket;
                this.p2pChatOut = outToModerator;
                this.p2pChatIn = inFromModerator;
                this.isModerator = false;
                mainFrame.addChatTab(chatName, this);
                executor.submit(() -> {
                    try {
                        String line;
                        boolean logbookLoaded = false;
                        StringBuilder logbookBuffer = new StringBuilder();
                        while ((line = inFromModerator.readLine()) != null) {
                            if (!logbookLoaded && line.equals("!LOGBOOK_START!")) {
                                while ((line = inFromModerator.readLine()) != null && !line.equals("!LOGBOOK_END!")) {
                                    logbookBuffer.append(line).append("\n");
                                }
                                logbookLoaded = true;
                                MessageListener listener = chatListeners.get(chatName);
                                if (listener != null && logbookBuffer.length() > 0) {
                                    for (String msg : logbookBuffer.toString().split("\n")) {
                                        if (!msg.trim().isEmpty()) listener.onMessageReceived(msg);
                                    }
                                }
                                continue;
                            }
                            MessageListener listener = chatListeners.get(chatName);
                            if (listener != null) {
                                listener.onMessageReceived(line);
                            }
                        }
                    } catch (IOException e) {
                    }
                });
                outToModerator.println("Ciao dal client " + this.username + "!");
                return true;
            } else {
                chatSocket.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void startP2PConnection(String ip, int port, boolean isModeratorConnection, MainFrame mainFrame, String chatName) {
        try {
            p2pChatSocket = new Socket(ip, port);
            p2pChatOut = new PrintWriter(p2pChatSocket.getOutputStream(), true);
            p2pChatIn = new BufferedReader(new InputStreamReader(p2pChatSocket.getInputStream()));
            mainFrame.addChatTab(chatName, this);
            executor.submit(() -> {
                try {
                    String line;
                    boolean logbookLoaded = false;
                    StringBuilder logbookBuffer = new StringBuilder();
                    while ((line = p2pChatIn.readLine()) != null) {
                        if (!logbookLoaded && line.equals("!LOGBOOK_START!")) {
                            while ((line = p2pChatIn.readLine()) != null && !line.equals("!LOGBOOK_END!")) {
                                logbookBuffer.append(line).append("\n");
                            }
                            logbookLoaded = true;
                            MessageListener listener = chatListeners.get(chatName);
                            if (listener != null && logbookBuffer.length() > 0) {
                                for (String msg : logbookBuffer.toString().split("\n")) {
                                    if (!msg.trim().isEmpty()) listener.onMessageReceived(msg);
                                }
                            }
                            continue;
                        }
                        MessageListener listener = chatListeners.get(chatName);
                        if (listener != null) {
                            listener.onMessageReceived(line);
                        }
                    }
                } catch (IOException e) {
                    if (isModerator && isModeratorConnection) {
                        closeP2PResources();
                        isModerator = false;
                    }
                }
            });
            if (isModeratorConnection) {
                p2pConnectedClients.add(p2pChatOut);
                p2pChatOut.println("SERVER: Benvenuto nella chat (creata da te)!");
            }
        } catch (IOException e) {
        }
    }

    public boolean isModeratorOfP2PChat() {
        return isModerator && p2pChatSocket != null && p2pChatSocket.isConnected() && !p2pChatSocket.isClosed();
    }

    public void exitP2PChat() {
        if (isModeratorOfP2PChat()) {
            closeP2PResources();
            isModerator = false;
        }
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    public void registerP2PMessageListener(String chatName, MessageListener listener) {
        chatListeners.put(chatName, listener);
    }

    public void sendP2PMessage(String message) {
        if (isModeratorOfP2PChat()) {
            for (PrintWriter out : p2pConnectedClients) {
                out.println(message);
            }
            for (MessageListener listener : chatListeners.values()) {
                listener.onMessageReceived(message);
            }
        } else if (p2pChatOut != null) {
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
            PrintWriter outToClient = null;
            try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                p2pConnectedClients.add(outToClient);
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
                        sendLogbookToClient(outToClient, logFileName);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        if (logFileName != null) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
                                String timestampedWelcome = "[" + LocalDateTime.now().format(formatter) + "] CLIENT: Connesso correttamente.";
                                writer.write(timestampedWelcome);
                                writer.newLine();
                            } catch (IOException e) {
                            }
                        }
                        MessageListener listener = chatListeners.get(chatName);
                        if (listener != null) {
                            listener.onMessageReceived("SERVER: Nuovo client esterno connesso.");
                        }
                        String p2pMessage;
                        while ((p2pMessage = inFromClient.readLine()) != null) {
                            for (PrintWriter out : p2pConnectedClients) {
                                out.println(p2pMessage);
                            }
                            for (MessageListener l : chatListeners.values()) {
                                l.onMessageReceived(p2pMessage);
                            }
                            if (logFileName != null) {
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
                                    String timestampedMessage = "[" + LocalDateTime.now().format(formatter) + "] CLIENT: " + p2pMessage;
                                    writer.write(timestampedMessage);
                                    writer.newLine();
                                } catch (IOException e) {
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            } finally {
                if (outToClient != null) p2pConnectedClients.remove(outToClient);
                try { clientSocket.close(); } catch (IOException e) { }
            }
        }

        private void sendLogbookToClient(PrintWriter outToClient, String logFileName) {
            if (logFileName == null) return;
            File logFile = new File(logFileName);
            if (!logFile.exists()) return;
            outToClient.println("!LOGBOOK_START!");
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outToClient.println(line);
                }
            } catch (IOException e) { }
            outToClient.println("!LOGBOOK_END!");
        }
    }
}