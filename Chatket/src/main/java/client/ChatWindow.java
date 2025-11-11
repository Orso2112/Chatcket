package client;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatWindow extends JFrame {
    private final JTextArea area = new JTextArea();
    private final JTextField input = new JTextField();
    private final JButton send = new JButton("Send");
    private final ChatClient client;
    public ChatWindow(ChatClient client) {
        this.client = client;
        area.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(input, BorderLayout.SOUTH);
        add(send, BorderLayout.EAST);
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String t = input.getText().trim();
                if (t.length() > 0) {
                    client.sendText(t);
                    input.setText("");
                }
            }
        });
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void appendMessage(String s) {
        area.append(s + "\n");
    }
}
