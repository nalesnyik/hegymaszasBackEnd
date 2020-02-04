package progmatic.hegymaszas.modell.messages;

import org.springframework.format.annotation.DateTimeFormat;
import progmatic.hegymaszas.modell.MyUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private MyUser user;

    private String text;

    @DateTimeFormat(pattern = "yyyy/MMM/dd HH:mm")
    private LocalDateTime creationDate;

    private boolean isDeleted = false;


    public Message() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public MyUser getUser() {
        return user;
    }


    public void setUser(MyUser author) {
        this.user = author;
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


    public boolean isDeleted() {
        return isDeleted;
    }


    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
