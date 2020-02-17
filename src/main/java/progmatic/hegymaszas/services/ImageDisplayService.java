package progmatic.hegymaszas.services;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import progmatic.hegymaszas.modell.Image;
import progmatic.hegymaszas.modell.ImageOfRoute;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ImageDisplayService {

    @PersistenceContext
    EntityManager em;


    public ResponseEntity<byte[]> convertImageToResponseEntity(byte[] image, String contentType) {
        MediaType mediaType;
        switch (contentType) {
            case "image/png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "image/jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            default:
                mediaType = MediaType.IMAGE_JPEG;
        }
        return ResponseEntity.ok().contentType(mediaType).body(image);
    }


    public ResponseEntity<byte[]> convertImageToResponseEntity(Image image) {
        MediaType mediaType;
        switch (image.getImageContentType()) {
            case "image/png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "image/jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            default:
                mediaType = MediaType.IMAGE_JPEG;
        }
        return ResponseEntity.ok().contentType(mediaType).body(image.getImage());
    }
}
