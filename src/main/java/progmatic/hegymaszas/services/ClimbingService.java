package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progmatic.hegymaszas.dto.ClimbingPlaceDto;
import progmatic.hegymaszas.dto.RouteCreateDto;
import progmatic.hegymaszas.dto.RoutesShowDto;
import progmatic.hegymaszas.dto.SectorsShowDto;
import progmatic.hegymaszas.exceptions.ClimbingPlaceNotFoundException;
import progmatic.hegymaszas.exceptions.RouteNameForSectorAlreadyExistsException;
import progmatic.hegymaszas.exceptions.SectorNotFoundException;
import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.repositories.ClimbingRepository;
import progmatic.hegymaszas.repositories.RouteRepository;
import progmatic.hegymaszas.repositories.SectorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClimbingService {

    @Autowired
    private ClimbingRepository climbingRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private RouteRepository routeRepository;

    @PersistenceContext
    EntityManager em;


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
        routeRepository.save(newRoute);

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
}
