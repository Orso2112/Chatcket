/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import client.ClientLogic;

import java.io.IOException;

public class AccesFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AccesFrame.class.getName());
    private ClientLogic client;

    public AccesFrame() {
        initComponents();
        setVisible(true);
        

        try {
            client = new ClientLogic("", "");
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "CONNESSIONE AL SERVER FALLITA", e);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Impossibile connettersi al server.",
                    "Connection Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(37, 37, 38));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ACCESS");
        jLabel1.setToolTipText("");

        jTextField1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jTextField1.setText("username");

        jPasswordField1.setText("password");

        jButton1.setBackground(new java.awt.Color(37, 37, 38));
        jButton1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 204, 51));
        jButton1.setText("LOG-IN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("USERNAME:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PASSWORD:");

        jButton2.setBackground(new java.awt.Color(37, 37, 38));
        jButton2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 51));
        jButton2.setText("Sign-in");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                        .addComponent(jPasswordField1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(99, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(108, 108, 108))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String username = jTextField1.getText();
        String password = new String(jPasswordField1.getPassword());
    
        if (username.isEmpty() || password.isEmpty() || username.length() <2) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Username e password non possono essere vuoti o più corti di 2 caratteri",
                    "Input Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        if (client == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "NON CONNESSO AL SERVER RIAVVIARE L'APPLICAZIONE",
                    "Connection Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            String response = client.login(username, password);
        
            if (response != null && response.startsWith("SUCCESS")) {
                // Check if connected to chat server
                if (client.isConnectedToChat()) {
                    // Close access frame and open main frame
                    MainFrame mainFrame = new MainFrame(username, client);
                    mainFrame.setVisible(true);
                    this.dispose();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Login riuscito ma impossibile connettersi al chat server",
                            "Connection Error",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            } else if (response != null && response.startsWith("ERROR")) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        response.substring(7),
                        "Login Fallito",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Risposta inaspettata dal server",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to login", e);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Errore di connessione: " + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String username = jTextField1.getText();
        String password = new String(jPasswordField1.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Username e password non possono essere vuoti",
                    "Input Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (client == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Not connected to server. Please restart the application.",
                    "Connection Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String response = client.register(username, password);
            
            if (response != null && response.startsWith("SUCCESS")) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Account created successfully! You can now login.",
                        "Success",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else if (response != null && response.startsWith("ERROR")) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        response.substring(7), // Remove "ERROR: " prefix
                        "Registration Failed",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Unexpected response from server",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to register", e);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Connection error: " + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new AccesFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
