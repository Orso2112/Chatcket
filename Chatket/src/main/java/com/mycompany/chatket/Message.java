package com.mycompany.chatket;

public class Message {
    public final String id;
    public final String sender;
    public final String text;
    public Message(String id, String sender, String text) {
        this.id = id;
        this.sender = sender;
        this.text = text;
    }
    public String toProtocolString() {
        return id + "::" + escape(sender) + "::" + escape(text);
    }
    public static Message fromProtocolString(String s) {
        String[] parts = s.split("::", 3);
        if (parts.length < 3) {
            return new Message("0", "unknown", s);
        }
        return new Message(parts[0], unescape(parts[1]), unescape(parts[2]));
    }
    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("::", "\\:");
    }
    private static String unescape(String s) {
        return s.replace("\\:", "::").replace("\\\\", "\\");
    }
}