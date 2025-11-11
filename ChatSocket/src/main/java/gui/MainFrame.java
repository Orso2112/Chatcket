package gui;
import client.ClientLogic;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFrame extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());
    private String username;
    private ClientLogic client;
    private final ExecutorService chatExecutor = Executors.newCachedThreadPool();

    public MainFrame(String username, ClientLogic client) {
        this.username = username;
        this.client = client;
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        jLabel1.setText("CIAO, " + username + "!");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(37, 37, 38));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(37, 37, 38));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel2.setBackground(new java.awt.Color(37, 37, 38));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CIAO,  username!");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 18));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html> Questa è la pagina principale: <br> Sulla sinistra troverai tutte le diverse chat in cui ti trovi. <br> Sotto queste istruzioni troverai alcune<br> opzioni come il nome visualizzato ecc... </html>");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("IMPOSTAZIONI:");

        jButton2.setBackground(new java.awt.Color(37, 37, 38));
        jButton2.setFont(new java.awt.Font("Verdana", 1, 12));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("AGGIUNGI Chat");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(37, 37, 38));
        jButton3.setFont(new java.awt.Font("Verdana", 1, 12));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("RIMUOVI Chat");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(37, 37, 38));
        jButton5.setFont(new java.awt.Font("Verdana", 1, 12));
        jButton5.setForeground(new java.awt.Color(255, 0, 0));
        jButton5.setText("LOG-OUT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(37, 37, 38));
        jButton6.setFont(new java.awt.Font("Verdana", 0, 10));
        jButton6.setForeground(new java.awt.Color(102, 0, 0));
        jButton6.setText("SIGN-OUT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(37, 37, 38));
        jButton7.setFont(new java.awt.Font("Verdana", 1, 12));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("CREA Chat");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 54, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(163, 163, 163)
                                                .addComponent(jLabel1))
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(97, 97, 97))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(259, 259, 259)
                                .addComponent(jLabel3)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                                                .addComponent(jButton6))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        jTabbedPane1.addTab("Main", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        AddChatDialog acd = new AddChatDialog(this, true, client, this);
        acd.setVisible(true);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        RemoveChatDialog rcd = new RemoveChatDialog(this, true);
        rcd.setVisible(true);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        if (client.isModeratorOfP2PChat()) {
            System.out.println("Sei il moderatore di una chat P2P. Chiudendola prima del log-out...");
            client.exitP2PChat();
        }
        client.closeAllResources();
        chatExecutor.shutdown();
        AccesFrame accesFrame = new AccesFrame();
        accesFrame.setVisible(true);
        this.dispose();
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "NON IMPLEMENTATA --> DA FARE: RIMOZIONE DAL FILE E USCITA DAL MAIN",
                "Info",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        CreateChatDialog ccd = new CreateChatDialog(this, true, client, this);
        ccd.setVisible(true);
    }

    public void addChatTab(String chatName, ClientLogic chatClient) {
        ChatPanel chatPanel = new ChatPanel(chatClient, chatName, this);
        jTabbedPane1.addTab(chatName, chatPanel);
        chatExecutor.submit(chatPanel::startListening);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            try {
                ClientLogic testClient = new ClientLogic("test", "test");
                new MainFrame("TestUser", testClient).setVisible(true);
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Failed to create MainFrame", e);
            }
        });
    }

    private class ChatPanel extends JPanel {
        private final JTextArea chatArea;
        private final JTextField messageField;
        private final JButton sendButton;
        private final JButton exitButton; // Nuovo pulsante Esci
        private final ClientLogic chatClient;
        private final String chatName;
        private final MainFrame mainFrame; // Riferimento al MainFrame
        private boolean listening = false;

        public ChatPanel(ClientLogic chatClient, String chatName, MainFrame mainFrame) {
            this.chatClient = chatClient;
            this.chatName = chatName;
            this.mainFrame = mainFrame; // Imposta il riferimento
            setLayout(new BorderLayout());
            chatArea = new JTextArea();
            chatArea.setEditable(false);
            chatArea.setBackground(new java.awt.Color(37, 37, 38));
            chatArea.setForeground(new java.awt.Color(255, 255, 255));
            chatArea.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));
            add(new JScrollPane(chatArea), BorderLayout.CENTER);
            JPanel inputPanel = new JPanel();
            inputPanel.setBackground(new java.awt.Color(37, 37, 38));
            messageField = new JTextField(20);
            messageField.setBackground(new java.awt.Color(60, 60, 60));
            messageField.setForeground(new java.awt.Color(255, 255, 255));
            messageField.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));
            sendButton = new JButton("Invia");
            sendButton.setBackground(new java.awt.Color(50, 50, 50));
            sendButton.setForeground(new java.awt.Color(255, 255, 255));
            sendButton.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));
            exitButton = new JButton("Esci Chat"); // Crea il pulsante
            exitButton.setBackground(new java.awt.Color(150, 50, 50)); // Colore rosastro
            exitButton.setForeground(new java.awt.Color(255, 255, 255));
            exitButton.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));
            inputPanel.add(messageField);
            inputPanel.add(sendButton);
            inputPanel.add(exitButton); // Aggiungi il pulsante Esci al pannello
            add(inputPanel, BorderLayout.SOUTH);
            sendButton.addActionListener(e -> sendMessage());
            messageField.addActionListener(e -> sendMessage());
            exitButton.addActionListener(e -> exitChat()); // Azione per il pulsante Esci
        }

        private void sendMessage() {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                String fullMessage = username + ": " + message;
                SwingUtilities.invokeLater(() -> chatArea.append(fullMessage + "\n"));
                messageField.setText("");
                if (chatClient.isP2PConnected()) {
                    chatClient.sendP2PMessage(fullMessage);
                } else {
                    if (chatClient.isConnectedToChat()) {
                        chatClient.getChatOut().println(fullMessage);
                    }
                }
            }
        }

        private void exitChat() { // Metodo per uscire dalla chat
            if (chatClient.isP2PConnected()) {
                chatClient.closeP2PResources();
                System.out.println("Disconnesso dalla chat P2P.");
                // Rimuove questa scheda dal JTabbedPane
                jTabbedPane1.remove(this);
            } else {
                System.out.println("Nessuna connessione P2P attiva da chiudere.");
            }
        }

        public void startListening() {
            if (listening) return;
            listening = true;
            // Registra il listener passando il nome della chat corrente
            chatClient.registerP2PMessageListener(chatName, msg -> SwingUtilities.invokeLater(() -> chatArea.append(msg + "\n")));
        }
    }

    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
}