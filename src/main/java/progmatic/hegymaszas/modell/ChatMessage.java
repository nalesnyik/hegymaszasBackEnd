package progmatic.hegymaszas.modell;

import java.time.LocalDate;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String sentTo;
    private LocalDate time;

    public ChatMessage(String sender, String sentTo, LocalDate time){
        this.sentTo = sentTo;
        this.sender = sender;
        this.time = time;
    }

    public ChatMessage(String sender, String content, String sentTo) {
        this.sender = sender;
        this.content = content;
        this.sentTo = sentTo;
    }

    public String getSentTo() {
        return sentTo;
    }

    public enum MessageType{
        CHAT, JOIN, LEAVE
    }

    public ChatMessage() {
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
