package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.dto.MyUserChosenShowDto;
import progmatic.hegymaszas.dto.MyUserDto;
import progmatic.hegymaszas.exceptions.RouteNotFoundException;
import progmatic.hegymaszas.exceptions.UserNotFoundException;
import progmatic.hegymaszas.modell.MyAuthority;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    EntityManager em;

    PasswordEncoder passwordEncoder;
    EmailService emailService;
    UserRepository userRepository;
    ClimbingService climbingService;
    ImageDisplayService imageDisplayService;


    @Autowired
    public UserService(PasswordEncoder passwordEncoder, EmailService emailService, UserRepository userRepository, ClimbingService climbingService, ImageDisplayService imageDisplayService) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.climbingService = climbingService;
        this.imageDisplayService = imageDisplayService;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user;
        try {
            user = em.find(MyUser.class, username);
        } catch (NoResultException ex) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }


    @Transactional
    public void createUser(MyUserDto userDto) {
        MyUser user = new MyUser();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        MyAuthority authority = em.find(MyAuthority.class, "ROLE_USER");
        authority.getUsers().add(user);
        user.setClimbingLogs(new ArrayList<>());
        em.persist(user);
        emailService.sendRegistrationEmail(user);
    }


    @Transactional
    public boolean userExists(String username) {
        return em.find(MyUser.class, username) != null;
    }


    @Transactional
    public boolean userEmailExists(String email) {
        try {
            em.createQuery("SELECT c FROM MyUser c where c.email = :email", MyUser.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }


    public MyUserChosenShowDto showMyProfile() {
        MyUser user = getMyUser();
        return new MyUserChosenShowDto(em.find(MyUser.class, user.getName()));
    }


    public ResponseEntity<byte[]> showProfilePicture() {
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        byte[] image = user.getProfilePicture();
        return imageDisplayService.convertImageToResponseEntity(image, user.getProfilePictureContentType());
    }


    public static MyUser getMyUser() {
        return (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public List<Long> get9idOfMiniImagesOfUser(String userName) {
        List<Long> idList;
        try {
            idList = em.createQuery("SELECT i.id FROM MyUser s  LEFT JOIN s.images i WHERE s.name=:userName AND i.originalImgId>0 ORDER BY i.originalImgId DESC", Long.class)
                    .setMaxResults(9)
                    .setParameter("userName", userName)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
        return idList;
    }


    public MyUserChosenShowDto showChosenUser(String userName) throws RouteNotFoundException {
        MyUser user = em.find(MyUser.class, userName);
        MyUserChosenShowDto dto = new MyUserChosenShowDto(user);
        List<Long> idOfMiniImages = get9idOfMiniImagesOfUser(userName);
        Map<Long, String> map = ClimbingService.createUrlMapOfImages(idOfMiniImages, "user");
        dto.setUserImages(map);
        return dto;
    }


    public Map<Long, String> showPhotosOfChosenUser(String username) throws UserNotFoundException {
        userValidator(userRepository.existsMyUserByName(username));
        List<Long> idOfMiniPictures = userRepository.idOfMiniImagesOfUser(username);
        return ClimbingService.createUrlMapOfImages(idOfMiniPictures, "route");
    }


    public static void userValidator(boolean doesExist) throws UserNotFoundException {
        if (!doesExist) throw new UserNotFoundException();
    }
}
