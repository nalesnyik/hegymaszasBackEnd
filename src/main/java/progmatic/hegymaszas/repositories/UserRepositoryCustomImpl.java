package progmatic.hegymaszas.repositories;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.modell.MyUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    public void storeProfilePicture(MultipartFile image) throws IOException {
        MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser user = em.find(MyUser.class, myUser.getName());
        user.setProfilePictureContentType(image.getContentType());
        user.setProfilePicture(image.getBytes());
    }


    public byte[] getMyProfilePicture() {
        MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return em.find(MyUser.class, myUser.getName()).getProfilePicture();
    }
}
