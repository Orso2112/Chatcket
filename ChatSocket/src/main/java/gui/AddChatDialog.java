package gui;

import client.ClientLogic;
import java.awt.Frame;
import javax.swing.JOptionPane;

public class AddChatDialog extends javax.swing.JDialog {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AddChatDialog.class.getName());
    public static ClientLogic client;
    private final MainFrame mainFrame;

    public AddChatDialog(Frame parent, boolean modal, ClientLogic client, MainFrame mainFrame) {
        super(parent, modal);
        this.client = client;
        this.mainFrame = (MainFrame) mainFrame;
        initComponents();
        setLocationRelativeTo(parent);
        setResizable(false);
        jButton1.setEnabled(jCheckBox1.isSelected());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(37, 37, 38));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NUOVA Chat");
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("IP:");
        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PassKey:");
        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Porta:");
        jTextField1.setText("0.0.0.0");
        jTextField1.addActionListener(evt -> jTextField1ActionPerformed(evt));
        jTextField2.setText("8000");
        jTextField2.addActionListener(evt -> jTextField2ActionPerformed(evt));
        jPasswordField1.setText("passkey");
        jButton2.setBackground(new java.awt.Color(37, 37, 38));
        jButton2.setFont(new java.awt.Font("Verdana", 0, 12));
        jButton2.setForeground(new java.awt.Color(204, 0, 0));
        jButton2.setText("CANCELLA");
        jButton2.setActionCommand("CANCELLA");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
        jButton1.setBackground(new java.awt.Color(37, 37, 38));
        jButton1.setFont(new java.awt.Font("Verdana", 0, 12));
        jButton1.setForeground(new java.awt.Color(51, 204, 0));
        jButton1.setText("CONFERMA");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jCheckBox1.setBackground(new java.awt.Color(37, 37, 38));
        jCheckBox1.setFont(new java.awt.Font("Verdana", 0, 8));
        jCheckBox1.setForeground(new java.awt.Color(200, 200, 200));
        jCheckBox1.setText("<html> Spuntando questa casella accetti la crittografia <br> delle chat e il funzionamento della responsabilità dell'utente. </html>");
        jCheckBox1.addActionListener(evt -> jCheckBox1ActionPerformed(evt));
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(32, 32, 32)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel3)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jPasswordField1))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel4)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                .addGap(19, 19, 19))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jButton1))
                                .addGap(19, 19, 19))
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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        jButton1.setEnabled(jCheckBox1.isSelected());
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { dispose(); }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String ip = jTextField1.getText();
        String portaString = jTextField2.getText();
        String passKey = new String(jPasswordField1.getPassword());
        boolean acceptedTerms = jCheckBox1.isSelected();
        if (!acceptedTerms) {
            JOptionPane.showMessageDialog(this, "Devi accettare i termini di crittografia.", "Errore di Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ip.isEmpty() || passKey.isEmpty() || portaString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "IP, Porta e PassKey non possono essere vuoti.", "Errore di Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int port;
        try {
            port = Integer.parseInt(portaString);
            if (port < 1024 || port > 65535) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci un numero di porta valido (1024-65535).", "Errore di Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String chatName = "Chat_" + ip + "_" + port;
        boolean success = client.joinChat(ip, port, passKey, mainFrame, chatName);
        if (success) {
            JOptionPane.showMessageDialog(this, "Connessione alla chat avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Connessione fallita. Controlla IP, Porta, PassKey o lo stato del moderatore.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AddChatDialog dialog = new AddChatDialog(new javax.swing.JFrame(), true, null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}