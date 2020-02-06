package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progmatic.hegymaszas.dto.ClimbingPlaceDto;
import progmatic.hegymaszas.dto.RouteCreateDto;
import progmatic.hegymaszas.dto.SectorDto;
import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.repositories.ClimbingRepository;
import progmatic.hegymaszas.repositories.RouteRepository;
import progmatic.hegymaszas.repositories.SectorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClimbingService {

    @Autowired
    private ClimbingRepository climbingRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private RouteRepository routeRepository;


    public List<ClimbingPlaceDto> showClimbingPlaces() {
        List<ClimbingPlace> climbingPlaces = climbingRepository.findAll();

        return climbingPlaces.stream().map(c -> {
            long id = c.getId();
            return new ClimbingPlaceDto(id, c.getName(),
                    climbingRepository.getNumOfRoutesOfClimbingPlace(id),
                    climbingRepository.getNumOfFeedbacksOfClimbingPlace(id));
        }).collect(Collectors.toList());
    }


    public List<SectorDto> showSectorsOfClimbingPlace(long climbingPlaceId) {
        List<Sector> list = climbingRepository.getSectorsOfClimbingPlace(climbingPlaceId);
        return list.stream().map(s -> {
            long sectorId = s.getId();
            return new SectorDto(sectorId,
                    s.getName(),
                    climbingRepository.getNumOfRoutesOfSector(sectorId),
                    climbingRepository.getNumOfFeedbacksOfSector(sectorId));
        }).collect(Collectors.toList());
    }


    public void createRoute(RouteCreateDto route) throws Exception {
        Sector sector = sectorRepository.findByName(route.getSectorName());
        if (sector == null) {
            throw new Exception();
        }
        if (routeRepository.existsRouteBySectorAndName(sector, route.getRouteName())) {
            throw new Exception();
        }

//        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Route newRoute = new Route(route, sector);
        routeRepository.save(newRoute);

    }
}
