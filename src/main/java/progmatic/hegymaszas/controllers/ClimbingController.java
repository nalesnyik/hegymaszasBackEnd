package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.*;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.services.ClimbingService;
import java.util.List;
import java.util.Map;

@RestController
public class ClimbingController {

    @Autowired
    private ClimbingService climbingService;


    @GetMapping
    public Map<String, List<ClimbingPlaceDto>> showClimbingPlaces() {
        return climbingService.showClimbingPlaces();
    }


    @GetMapping("areas/{climbingPlaceId}")
    public Map<String, List<SectorsShowDto>> showSectorsOfClimbingPlace(@PathVariable(value = "climbingPlaceId") long id) {
        return climbingService.showSectorsOfClimbingPlace(id);
    }


    @GetMapping("areas/{climbingPlaceId}/{sectorId}")
    public Map<String, List<RoutesShowDto>> showRoutesOfSector(@PathVariable String climbingPlaceId, @PathVariable long sectorId) throws SectorNotFoundException {
        return climbingService.showRoutesOfSector(sectorId);
    }


    @GetMapping("areas/route/{routeId}")
    public RouteChosenShowDto showCreateRoute(@PathVariable long routeId) throws RouteNotFoundException {
        return climbingService.showChosenRoute(routeId);
    }


    @PostMapping("/route")
    public void createRoute(@RequestBody RouteCreateDto route) throws RouteNameForSectorAlreadyExistsException, ClimbingPlaceNotFoundException, SectorNotFoundException {
        climbingService.createRoute(route);
    }


    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> showImgOfRoute(
            @PathVariable long imageId) throws ImageNotFoundException {
        return climbingService.showImgOfRoute(imageId);
    }


    @PostMapping("/{routeId}")
    public void verifyRouteByUser(@PathVariable long routeId) {
        climbingService.verifyRouteService(routeId);
    }


    @PostMapping("/{sectorId}/{routeId}")
    public List<Sector> showSectorsByDistance(@RequestBody int dist, double userLong, double userLat) {
        return climbingService.getSectorByDistance(dist, userLat, userLong);
    }


    @PostMapping("/route/{routeId}/feedback")
    public FeedbackShowDto createFeedback(
            @PathVariable(value = "routeId") long routeId,
            @RequestBody FeedbackCreateDto feedback) throws RouteNotFoundException, NotAppropriateNumberOfStarsForRatingException {
        return climbingService.createFeedback(feedback, routeId);
    }


    @PostMapping("/route/{routeId}/log")
    public ClimbingLogShowDto createLog(
            @PathVariable(value = "routeId") long routeId,
            @RequestBody ClimbingLogCreateDto log) throws RouteNotFoundException, WrongAscentTypeException {
        return climbingService.createLog(log, routeId);
    }


}

