package progmatic.hegymaszas.modell;

import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.services.UserService;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
public class ImageOfRoute extends Image {
    @ManyToOne
    private Route route;


    public ImageOfRoute() {
    }


    public ImageOfRoute(Route route, MultipartFile image) throws IOException {
        this.route = route;
        this.image = image.getBytes();
        this.imageContentType = image.getContentType();
        this.creationDate = LocalDateTime.now();
        this.user = UserService.getMyUser();
    }


    public ImageOfRoute(ImageOfRoute originalImage, byte[] miniImage) {
        this.creationDate = originalImage.creationDate;
        this.imageContentType = originalImage.imageContentType;
        this.route = originalImage.route;
        this.user = originalImage.user;
        this.image = miniImage;
        this.originalImgId = originalImage.getId();
    }


    public Route getRoute() {
        return route;
    }


    public void setRoute(Route route) {
        this.route = route;
    }
}
