package progmatic.hegymaszas.dto;

import java.time.LocalDateTime;

public abstract class MessageShowDto {
    protected long id;
    protected String text;
    protected LocalDateTime creationDate;
    protected String username;


    public MessageShowDto() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
}
