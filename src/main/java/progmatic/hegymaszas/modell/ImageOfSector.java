package progmatic.hegymaszas.modell;

import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.services.UserService;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
public class ImageOfSector extends Image {

    @ManyToOne
    private Sector sector;


    public ImageOfSector() {
    }


    public ImageOfSector(MultipartFile image, Sector sector) throws IOException {
        this.sector = sector;
        this.image = image.getBytes();
        this.imageContentType = image.getContentType();
        this.creationDate = LocalDateTime.now();
        this.user = UserService.getMyUser();
    }


    public ImageOfSector(ImageOfSector originalImage, byte[] miniImage) {
        this.sector = originalImage.sector;
        this.image = miniImage;
        this.imageContentType = originalImage.imageContentType;
        this.creationDate = originalImage.creationDate;
        this.originalImgId = originalImage.id;
        this.user = originalImage.user;
    }


    public Sector getSector() {
        return sector;
    }


    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
