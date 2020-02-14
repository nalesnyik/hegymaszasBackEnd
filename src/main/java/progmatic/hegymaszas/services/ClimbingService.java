package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.*;
import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.modell.ImageOfRoute;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.repositories.ClimbingRepository;
import progmatic.hegymaszas.repositories.RouteRepository;
import progmatic.hegymaszas.repositories.SectorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Route newRoute = new Route(route, sector);
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            newRoute.setRouteVerified(true);
            routeRepository.save(newRoute);
        } else {
            newRoute.setRouteVerified(false);
            newRoute.setVerificationCounter(0);
            routeRepository.save(newRoute);
        }
    }


    public Map<String, List<RoutesShowDto>> showRoutesOfSector(long sectorId) throws SectorNotFoundException {
        Sector sector = sectorRepository.findWithRoutesById(sectorId);
        if (sector == null) {
            throw new SectorNotFoundException("Sector name \"" + sectorId + "\" not found.");
        }
        List<RoutesShowDto> dtos = sector.getRoutes().stream().map(RoutesShowDto::new).collect(Collectors.toList());
        Map<String, List<RoutesShowDto>> map = new HashMap<>();
        map.put("routes", dtos);
        return map;
    }


    public RouteChosenShowDto showChosenRoute(long routeId) throws RouteNotFoundException {
        Route route = routeRepository.routeWithEverything(routeId);
        //routeValidator(route);
        RouteChosenShowDto dto = new RouteChosenShowDto(route);
        List<Long> idOfMiniImages = routeRepository.idOfMiniImagesOfRoute(route.getId());
        Map<Long, String> map = dto.getUrlOfImages();
        for (Long id : idOfMiniImages) {
            map.put(id - 1, "/areas/image/" + id);
        }
        return dto;
    }

    public void verifyRouteService(long id) {
        Route route = em.find(Route.class, id);
        route.setVerificationCounter(route.getVerificationCounter()+1);
        if (route.getVerificationCounter() >= 5) {
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


    public ResponseEntity<byte[]> showImgOfRoute(long imageId) throws ImageNotFoundException {
        ImageOfRoute image = em.find(ImageOfRoute.class, imageId);
        if (image == null) throw new ImageNotFoundException();

        return imageDisplayService.convertImageToResponseEntity(image);
    }


    public void routeValidator(Route route) throws RouteNotFoundException {
        if (route == null) {
            throw new RouteNotFoundException();
        }
    }
}
