package progmatic.hegymaszas.services;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.*;
import progmatic.hegymaszas.modell.*;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.Feedback;
import progmatic.hegymaszas.repositories.ClimbingRepository;
import progmatic.hegymaszas.repositories.PostGisRepository;
import progmatic.hegymaszas.repositories.RouteRepository;
import progmatic.hegymaszas.repositories.SectorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClimbingService {

    private static final Logger logger = LoggerFactory.getLogger(ClimbingService.class);
    @PersistenceContext
    EntityManager em;

    private ClimbingRepository climbingRepository;
    private SectorRepository sectorRepository;
    private RouteRepository routeRepository;
    private ImageDisplayService imageDisplayService;
    private PostGisRepository postGisRepository;


    @Autowired
    public ClimbingService(ClimbingRepository climbingRepository,
                           SectorRepository sectorRepository,
                           RouteRepository routeRepository,
                           ImageDisplayService imageDisplayService,
                           PostGisRepository postGisRepository
    ) {
        this.climbingRepository = climbingRepository;
        this.sectorRepository = sectorRepository;
        this.routeRepository = routeRepository;
        this.imageDisplayService = imageDisplayService;
        this.postGisRepository = postGisRepository;
    }


    public ClimbingService() {
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
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
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
        if (idOfMiniImages != null) dto.setUrlOfImages(createUrlMapOfImages(idOfMiniImages, "route"));

        long idOfProfilePicture = sectorRepository.getProfileId(sectorId);
        if (idOfProfilePicture > 0) {
            dto.setUrlOfPicture(getUrlOfPictureOfSector(idOfProfilePicture));
        }

        long idOfMiniProfilePicture = sectorRepository.getMiniProfileId(sectorId);
        if (idOfMiniProfilePicture > 0) {
            dto.setUrlOfMiniPicture(getUrlOfPictureOfSector(idOfMiniProfilePicture));
        }

        return dto;
    }


    private String getUrlOfPictureOfSector(long idOfPicture) {
        return "localhost:8080/image/sector/" + idOfPicture;
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


    public static Map<Long, String> createUrlMapOfImages(List<Long> idOfMiniImages, String entity) {
        Map<Long, String> map = new TreeMap<>();
        for (Long id : idOfMiniImages) {
            map.put((id - 1), "/image/" + entity + "/" + id);
        }
        return map;
    }


    public void verifyRouteService(long id) {
        Route route = em.find(Route.class, id);
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        route.setVerificationCounter(route.getVerificationCounter() + 1);
        if (route.getVerificationCounter() >= 5 ||
                user.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN"))) ||
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


    public List<SectorsShowDto> getSectorByDistance(DistanceCheckerDto dto) {
        List<Route> routes = em.createQuery("SELECT r from Route r", Route.class).getResultList();
        List<Sector> sectorsByDistances = new ArrayList<>();
        for (Route route1 : routes) {
            if (distance(route1.getSector().getLatitude(), route1.getSector().getLongitude(),
                    dto.getUserLat(), dto.getUserLong()) <= dto.getDist()) {
                sectorsByDistances.add(route1.getSector());
            }
        }

        return convertSectorsToSectorsShowDto(sectorsByDistances);
    }


    public List<SectorsShowDto> convertSectorsToSectorsShowDto(List<Sector> sectors) {
        List<SectorsShowDto> list = new ArrayList<>();
        for (Sector sector : sectors) {
            int numOfRoutes = climbingRepository.getNumOfRoutesOfSector(sector.getId());
            int numOfFeedbacks = climbingRepository.getNumOfFeedbacksOfSector(sector.getId());
            SectorsShowDto dto = new SectorsShowDto(sector.getId(), sector.getName(), numOfRoutes, numOfFeedbacks);
            list.add(dto);
        }
        return list;
    }


    @Transactional
    public ResponseEntity<byte[]> showPictureOfRoute(long imageId) throws ImageNotFoundException {
        ImageOfRoute image = em.find(ImageOfRoute.class, imageId);
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


    @Transactional
    public ResponseEntity<byte[]> showPictureOfSector(long pictureId) throws ImageNotFoundException {
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
        return createUrlMapOfImages(idOfMiniPictures, "route");
    }


    @Transactional
    public void uploadsectorfromfile(MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL);
        List<CSVRecord> list = parser.getRecords();
        for (CSVRecord record : list) {
            Sector sector = new Sector();
            GeometryFactory geometryFactory = new GeometryFactory();
            Point point = geometryFactory.createPoint(new Coordinate(Double.parseDouble(record.get(2)), Double.parseDouble(record.get(1))));
            sector.setLocation(point);
            sector.setName(record.get(3));
            sector.setClimbingPlace(em.find(ClimbingPlace.class, Long.parseLong(record.get(4))));
            em.persist(sector);
        }
    }


    public List<FeedbackShowDto> createFeedbackShow(List<Feedback> feedbacks) {
        List<FeedbackShowDto> feedbackShowList = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            feedbackShowList.add(new FeedbackShowDto(feedback));
        }
        return feedbackShowList;
    }


    public List<ClimbingLogShowDto> createClimbingLogShow(List<ClimbingLog> climbingLogs) {
        List<ClimbingLogShowDto> climbingLogShowList = new ArrayList<>();
        for (ClimbingLog climbingLog : climbingLogs) {
            climbingLogShowList.add(new ClimbingLogShowDto(climbingLog));
        }
        return climbingLogShowList;
    }


    public List<String> showSectorsWithinDistance(LocationDto dto) {
        logger.info("{}", dto);
        List<Sector> sectorList = postGisRepository.getSectorsWithinDistanceOfLocation2(dto.getLon(), dto.getLat(), dto.getDist());
        if (sectorList == null) {
            return new ArrayList<>();
        }
        return sectorList.stream().map(Sector::getName).collect(Collectors.toList());
    }


    public ShowCoordinateDto vmi(long id) {
        Sector sector = em.find(Sector.class, id);
        return new ShowCoordinateDto(sector.getLocation());
    }
}
