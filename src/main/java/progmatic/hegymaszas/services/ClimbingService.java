package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.*;
import progmatic.hegymaszas.modell.*;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.Feedback;
import progmatic.hegymaszas.repositories.ClimbingRepository;
import progmatic.hegymaszas.repositories.RouteRepository;
import progmatic.hegymaszas.repositories.SectorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClimbingService {

    @PersistenceContext
    EntityManager em;

    private ClimbingRepository climbingRepository;
    private SectorRepository sectorRepository;
    private RouteRepository routeRepository;
    private ImageDisplayService imageDisplayService;


    @Autowired
    public ClimbingService(ClimbingRepository climbingRepository, SectorRepository sectorRepository, RouteRepository routeRepository, ImageDisplayService imageDisplayService) {
        this.climbingRepository = climbingRepository;
        this.sectorRepository = sectorRepository;
        this.routeRepository = routeRepository;
        this.imageDisplayService = imageDisplayService;
    }


    public Map<String, List<ClimbingPlaceDto>> showClimbingPlaces() {
        List<ClimbingPlace> climbingPlaces = climbingRepository.findAll();

        List<ClimbingPlaceDto> dtos = climbingPlaces.stream().map(c -> {
            long id = c.getId();
            return new ClimbingPlaceDto(id, c.getName(),
                    climbingRepository.getNumOfRoutesOfClimbingPlace(id),
                    climbingRepository.getNumOfFeedbacksOfClimbingPlace(id));
        }).collect(Collectors.toList());
        Map<String, List<ClimbingPlaceDto>> map = new HashMap<>();
        map.put("places", dtos);
        return map;
    }


    public Map<String, List<SectorsShowDto>> showSectorsOfClimbingPlace(long climbingPlaceId) {
        List<Sector> list = climbingRepository.getSectorsOfClimbingPlace(climbingPlaceId);
        List<SectorsShowDto> dtos = list.stream().map(s -> {
            long sectorId = s.getId();
            return new SectorsShowDto(sectorId,
                    s.getName(),
                    climbingRepository.getNumOfRoutesOfSector(sectorId),
                    climbingRepository.getNumOfFeedbacksOfSector(sectorId));
        }).collect(Collectors.toList());
        Map<String, List<SectorsShowDto>> map = new HashMap<>();
        map.put("zones", dtos);
        return map;
    }


    public void createRoute(RouteCreateDto route)
            throws ClimbingPlaceNotFoundException, SectorNotFoundException, RouteNameForSectorAlreadyExistsException {
        ClimbingPlace climbingPlace = em.find(ClimbingPlace.class, route.getClimbingPlaceId());
        if (climbingPlace == null) {
            throw new ClimbingPlaceNotFoundException();
        }

        Sector sector = em.find(Sector.class, route.getSectorId());
        if (sector == null) {
            throw new SectorNotFoundException("Sector name \"" + route.getSectorId() + "\" not found.");
        }
        if (routeRepository.existsRouteBySectorAndName(sector, route.getRouteName())) {
            throw new RouteNameForSectorAlreadyExistsException("Route name \"" + route.getRouteName() +
                    "\" for sector \"" + route.getSectorId() + "\" already exists.");
        }

        Route newRoute = new Route(route, sector);
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))||
                user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ORGANIZATION"))) {
            newRoute.setRouteVerified(true);
        } else {
            newRoute.setRouteVerified(false);
            newRoute.setVerificationCounter(0);
        }
        routeRepository.save(newRoute);
    }


    public SectorChosenShowDto showChosenSector(long sectorId) throws SectorNotFoundException {
        Sector sector = sectorRepository.findWithRoutesById(sectorId);
        ClimbingService.sectorValidator(sector);
        SectorChosenShowDto dto = new SectorChosenShowDto(sector);
        List<Long> idOfMiniImages = sectorRepository.get9idOfMiniImagesOfSector(sectorId);
        if (idOfMiniImages != null) dto.setUrlOfImages(createUrlMapOfImages(idOfMiniImages, "sector"));

        long idOfProfilePicture = sectorRepository.getProfileId(sectorId);
        if (idOfProfilePicture > 0) {
            dto.setUrlOfPicture(getUrlOfPictureOfSector(sectorId, idOfProfilePicture));
        }

        long idOfMiniProfilePicture = sectorRepository.getMiniProfileId(sectorId);
        if (idOfMiniProfilePicture > 0) {
            dto.setUrlOfMiniPicture(getUrlOfPictureOfSector(sectorId, idOfMiniProfilePicture));
        }

        return dto;
    }


    private String getUrlOfPictureOfSector(long sectorId, long idOfPicture) {
        return "localhost:8080/image/" + sectorId + "/" + idOfPicture;
    }


    public RouteChosenShowDto showChosenRoute(long routeId) throws RouteNotFoundException {
        Route route = routeRepository.routeWithEverything(routeId);
        routeValidator(route);
        RouteChosenShowDto dto = new RouteChosenShowDto(route);
        List<Long> idOfMiniImages = routeRepository.get9idOfMiniImagesOfRoute(routeId);
        Map<Long, String> map = createUrlMapOfImages(idOfMiniImages, "route");
        dto.setUrlOfImages(map);
        return dto;
    }


    public Map<Long, String> createUrlMapOfImages(List<Long> idOfMiniImages, String entity) {
        Map<Long, String> map = new TreeMap<>();
        StringBuilder url = new StringBuilder("localhost:8080/image/");
        for (Long id : idOfMiniImages) {
            map.put((id - 1), url.toString() + "/" + entity + "/" + id);
        }
        return map;
    }


    public void verifyRouteService(long id) {
        Route route = em.find(Route.class, id);
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        route.setVerificationCounter(route.getVerificationCounter() + 1);
        if (route.getVerificationCounter() >= 5 ||
            user.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))||
                user.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ORGANIZATION")))) {
            route.setRouteVerified(true);
        }
    }


    public double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.853159616;
            return (dist);
        }
    }


    public List<Sector> getSectorByDistance(int dist, double userLat, double userLong) {
        List<Route> routes = em.createQuery("SELECT r from Route r", Route.class).getResultList();
        List<Sector> sectorsByDistances = new ArrayList<>();
        for (Route route1 : routes) {
            if (distance(route1.getSector().getLatitude(), route1.getSector().getLongitude(), userLat, userLong) <= dist) {
                sectorsByDistances.add(route1.getSector());
            }
        }
        return sectorsByDistances;
    }


    public ResponseEntity<byte[]> showPictureOfRoute(long imageId) throws ImageNotFoundException {
        ImageOfRoute image = em.find(ImageOfRoute.class, imageId);
        if (image == null) throw new ImageNotFoundException();

        return imageDisplayService.convertImageToResponseEntity(image);
    }


    @Transactional
    public FeedbackShowDto createFeedback(FeedbackCreateDto feedback, long routeId) throws RouteNotFoundException, NotAppropriateNumberOfStarsForRatingException {
        RatingService.checkRatingOfCreateDto(feedback.getRating(), 1, 5);
        Route route = em.find(Route.class, routeId);
        routeValidator(route);

        Feedback newFeedback = new Feedback(feedback, route);
        em.persist(newFeedback);

        return new FeedbackShowDto(newFeedback);
    }


    @Transactional
    public ClimbingLogShowDto createLog(ClimbingLogCreateDto log, long routeId) throws RouteNotFoundException, WrongAscentTypeException {
        Route route = em.find(Route.class, routeId);
        routeValidator(route);

        ClimbingLog newLog = new ClimbingLog(log, route);
        em.persist(newLog);

        return new ClimbingLogShowDto(newLog);
    }


    public static void routeValidator(Route route) throws RouteNotFoundException {
        if (route == null) throw new RouteNotFoundException();
    }


    public static void routeValidator(boolean doesExist) throws RouteNotFoundException {
        if (!doesExist) throw new RouteNotFoundException();
    }


    public static void sectorValidator(Sector sector) throws SectorNotFoundException {
        if (sector == null) throw new SectorNotFoundException();
    }


    public static void sectorValidator(boolean doesExist) throws SectorNotFoundException {
        if (!doesExist) throw new SectorNotFoundException();
    }


    public ResponseEntity<byte[]> showPictureOfSector(long pictureId) {
        ImageOfSector image = em.find(ImageOfSector.class, pictureId);
        return imageDisplayService.convertImageToResponseEntity(image);
    }


    public Map<Long, String> showPhotosOfChosenRoute(long routeId) throws RouteNotFoundException {
        routeValidator(routeRepository.existsRouteById(routeId));
        List<Long> idOfMiniPictures = routeRepository.idOfMiniImagesOfRoute(routeId);
        return createUrlMapOfImages(idOfMiniPictures, "route");

    }


    public Map<Long, String> showPhotosOfChosenSector(long sectorId) throws SectorNotFoundException {
        sectorValidator(sectorRepository.existsSectorById(sectorId));
        List<Long> idOfMiniPictures = sectorRepository.idOfMiniImagesOfSector(sectorId);
        return createUrlMapOfImages(idOfMiniPictures, "sector");
    }
}
