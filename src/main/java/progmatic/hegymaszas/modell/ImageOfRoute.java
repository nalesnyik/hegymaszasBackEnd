package progmatic.hegymaszas.modell;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
public class ImageOfRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    protected Route route;

    @Lob
    protected byte[] image;
    protected String imageContentType;
    protected LocalDateTime creationDate;
    @ManyToOne
    protected MyUser user;

    protected long originalImgId;


    public ImageOfRoute() {
    }


    public ImageOfRoute(Route route, MultipartFile image) throws IOException {
        this.route = route;
        this.image = image.getBytes();
        this.imageContentType = image.getContentType();
        this.creationDate = LocalDateTime.now();
        this.user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public ImageOfRoute(ImageOfRoute image, byte[] miniImage) {
        this.creationDate = image.creationDate;
        this.imageContentType = image.imageContentType;
        this.route = image.route;
        this.user = image.user;
        this.image = miniImage;
        this.originalImgId = image.getId();
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public Route getRoute() {
        return route;
    }


    public void setRoute(Route route) {
        this.route = route;
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
}
