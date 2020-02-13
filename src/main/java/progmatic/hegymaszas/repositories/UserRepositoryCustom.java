package progmatic.hegymaszas.repositories;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserRepositoryCustom {
    void storeProfilePicture(MultipartFile image) throws IOException;

    byte[] getMyProfilePicture();
}
