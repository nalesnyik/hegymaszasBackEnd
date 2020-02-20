package progmatic.hegymaszas.repositories;

import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    public void storeProfilePicture(MultipartFile image) throws IOException {
        MyUser myUser = UserService.getMyUser();
        myUser = em.merge(myUser);
        myUser.setProfilePictureContentType(image.getContentType());
        myUser.setProfilePicture(image.getBytes());
    }


    public byte[] getMyProfilePicture() {
        MyUser myUser = UserService.getMyUser();
        return em.find(MyUser.class, myUser.getName()).getProfilePicture();
    }


    public List<Long> idOfMiniImagesOfUser(String username) {
        return em.createQuery("SELECT i.id FROM Route r JOIN r.images i WHERE i.user.name=:name", Long.class)
                .setParameter("name", username)
                .getResultList();
    }
}
