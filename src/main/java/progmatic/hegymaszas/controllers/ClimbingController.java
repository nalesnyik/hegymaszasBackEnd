package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.ClimbingPlaceNotFoundException;
import progmatic.hegymaszas.exceptions.RouteNameForSectorAlreadyExistsException;
import progmatic.hegymaszas.exceptions.RouteNotFoundException;
import progmatic.hegymaszas.exceptions.SectorNotFoundException;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.services.ClimbingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("areas")
public class ClimbingController {

    @Autowired
    private ClimbingService climbingService;


    @GetMapping
    public Map<String, List<ClimbingPlaceDto>> showClimbingPlaces() {
        return climbingService.showClimbingPlaces();
    }


    @GetMapping("/{climbingPlaceId}")
    public Map<String, List<SectorsShowDto>> showSectorsOfClimbingPlace(@PathVariable(value = "climbingPlaceId") long id) {
        return climbingService.showSectorsOfClimbingPlace(id);
    }


    @GetMapping("/{climbingPlaceId}/{sectorId}")
    public Map<String, List<RoutesShowDto>> showRoutesOfSector(@PathVariable String climbingPlaceId, @PathVariable long sectorId) throws SectorNotFoundException {
        return climbingService.showRoutesOfSector(sectorId);
    }


    @GetMapping("/{climbingPlaceId}/{sectorId}/{routeId}")
    public RouteChosenShowDto showCreateRoute(@PathVariable long climbingPlaceId, @PathVariable long sectorId, @PathVariable long routeId) throws RouteNotFoundException {
        return climbingService.showChosenRoute(routeId);
    }


    @PostMapping("/route")
    public void createRoute(@RequestBody RouteCreateDto route) throws RouteNameForSectorAlreadyExistsException, ClimbingPlaceNotFoundException, SectorNotFoundException {
        climbingService.createRoute(route);
    }

    @PostMapping("/{routeId}")
    public void verifyRouteByUser(@PathVariable long routeId) {
        climbingService.verifyRouteService(routeId);
    }

    @PostMapping("/{sectorId}/{routeId}")
    public List<Sector> showSectorsByDistance(@RequestBody int dist, double userLong, double userLat) {
        return climbingService.getSectorByDistance(dist, userLat, userLong);
    }

}

