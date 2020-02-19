package progmatic.hegymaszas.repositories;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserRepositoryCustom {
    void storeProfilePicture(MultipartFile image) throws IOException;

    byte[] getMyProfilePicture();

    List<Long> idOfMiniImagesOfUser(String username);
}
