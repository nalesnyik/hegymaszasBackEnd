package progmatic.hegymaszas.services;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.exceptions.RouteNotFoundException;
import progmatic.hegymaszas.modell.ImageOfRoute;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.repositories.RouteRepository;
import progmatic.hegymaszas.repositories.UserRepository;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileUploadService {

    @PersistenceContext
    EntityManager em;

    UserRepository userRepository;
    RouteRepository routeRepository;
    ClimbingService climbingService;


    @Autowired
    public FileUploadService(UserRepository userRepository, RouteRepository routeRepository, ClimbingService climbingService) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.climbingService = climbingService;
    }


    public void init() {
    }


    @Transactional
    public void storeProfilePicture(MultipartFile file) throws IOException {
        userRepository.storeProfilePicture(file);
    }


    public Stream<Path> loadAll() {
        return null;
    }


    public Path load(String filename) {
        return null;
    }


    public byte[] loadAsResource(String filename) throws IOException {
        byte[] bytes = new byte[1];
        return bytes;
    }


    public void deleteAll() {
    }


    /*@Transactional
    public void storePictureForRoute(long routeId, MultipartFile image) throws IOException, RouteNotFoundException {
        Route route = em.find(Route.class, routeId);
        climbingService.routeValidator(route);
        ImageOfRoute img = new ImageOfRoute(route, image);
        em.persist(img);

        byte[] imgMiniByteArray = resize(image);
        ImageOfRoute imgMini = new ImageOfRoute(img, imgMiniByteArray);
        em.persist(imgMini);
    }*/


    public static byte[] resize(MultipartFile image) throws IOException {
        BufferedImage inputImage = ImageIO.read(image.getInputStream());
        BufferedImage outputImage = Scalr.resize(inputImage, 200, 200);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }


}
