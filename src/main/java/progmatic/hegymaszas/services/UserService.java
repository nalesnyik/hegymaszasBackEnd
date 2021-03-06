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
import progmatic.hegymaszas.dto.ClimbingLogCreateDto;
import progmatic.hegymaszas.dto.FeedbackShowDto;
import progmatic.hegymaszas.dto.MyUserChosenShowDto;
import progmatic.hegymaszas.dto.MyUserDto;
import progmatic.hegymaszas.exceptions.UserNotFoundException;
import progmatic.hegymaszas.exceptions.WrongAscentTypeException;
import progmatic.hegymaszas.modell.MyAuthority;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.Feedback;
import progmatic.hegymaszas.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        user.setRegistrationDate(LocalDateTime.now());
        user.setClimbingLogs(new ArrayList<>());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setDateOfFirstClimb(userDto.getDateOfFirstClimb());
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


    @Transactional
    public ResponseEntity<byte[]> showProfilePicture() {
        MyUser user = em.find(MyUser.class, UserService.getMyUser().getName());
        byte[] image = user.getProfilePicture();
        return imageDisplayService.convertImageToResponseEntity(image, user.getProfilePictureContentType());
    }


    public static MyUser getMyUser() {
        return (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public List<Long> get9idOfMiniImagesOfUser(String userName) {
        List<Long> idList;
        try {
            idList = em.createQuery("SELECT i.id FROM MyUser u LEFT JOIN u.images i WHERE u.name=:userName AND i.originalImgId>0 ORDER BY i.originalImgId DESC", Long.class)
                    .setMaxResults(9)
                    .setParameter("userName", userName)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
        return idList;
    }


    @Transactional
    public MyUserChosenShowDto showChosenUser(String userName) {
        MyUser user = em.find(MyUser.class, userName);
        ClimbingService climbingService = new ClimbingService();
        MyUserChosenShowDto dto = new MyUserChosenShowDto(user, climbingService);
        List<Long> idOfMiniImages = get9idOfMiniImagesOfUser(userName);
        Map<Long, String> map = ClimbingService.createUrlMapOfImages(idOfMiniImages, "route");
        dto.setUserImages(map);
        dto.setProfilePictureUrl("/me/picture");
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


    public void createUserLog(long routeId, String type) throws WrongAscentTypeException {
        Route route = em.find(Route.class, routeId);
        ClimbingLogCreateDto logCreate = new ClimbingLogCreateDto();
        logCreate.setType(type);
        ClimbingLog log = new ClimbingLog(logCreate, route);
        log.setDateOfClimb(LocalDate.now());
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ClimbingLog> userLogs = user.getClimbingLogs();
        userLogs.add(log);
        user.setClimbingLogs(userLogs);
    }
}
