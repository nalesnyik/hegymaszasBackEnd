package progmatic.hegymaszas.modell;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Lob
    protected byte[] image;
    protected String imageContentType;
    protected LocalDateTime creationDate;
    @ManyToOne
    protected MyUser user;

    protected long originalImgId;


    public Image() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getImageContentType() {
        return imageContentType;
    }


    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public MyUser getUser() {
        return user;
    }


    public void setUser(MyUser user) {
        this.user = user;
    }


    public long getOriginalImgId() {
        return originalImgId;
    }


    public void setOriginalImgId(long originalImgId) {
        this.originalImgId = originalImgId;
    }
}
