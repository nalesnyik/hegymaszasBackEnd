package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.*;
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


    @GetMapping("/route/{routeId}")
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
}

