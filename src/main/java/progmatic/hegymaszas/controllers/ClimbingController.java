package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.dto.*;
import progmatic.hegymaszas.exceptions.*;
import progmatic.hegymaszas.services.ClimbingService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ClimbingController {

    @Autowired
    private ClimbingService climbingService;


    @GetMapping("/areas/")
    public Map<String, List<ClimbingPlaceDto>> showClimbingPlaces() {
        return climbingService.showClimbingPlaces();
    }


    @GetMapping("/areas/{climbingPlaceId}")
    public Map<String, List<SectorsShowDto>> showSectorsOfClimbingPlace(@PathVariable(value = "climbingPlaceId") long id) {
        return climbingService.showSectorsOfClimbingPlace(id);
    }


    @GetMapping("/areas/sector/{sectorId}")
    public SectorChosenShowDto showChosenSector(@PathVariable long sectorId) throws SectorNotFoundException {
        return climbingService.showChosenSector(sectorId);
    }


    @GetMapping("/image/sector/{pictureId}")
    public ResponseEntity<byte[]> showPictureOfSector(
            @PathVariable long pictureId
    ) throws ImageNotFoundException {
        return climbingService.showPictureOfSector(pictureId);
    }


    @GetMapping("/areas/route/{routeId}")
    public RouteChosenShowDto showChosenRoute(@PathVariable long routeId) throws RouteNotFoundException {
        return climbingService.showChosenRoute(routeId);
    }


    @GetMapping("/image/route/{routeId}/photos")
    public Map<Long, String> showPhotosOfChosenRoute(@PathVariable long routeId) throws RouteNotFoundException {
        return climbingService.showPhotosOfChosenRoute(routeId);
    }


    @GetMapping("/image/sector/{sectorId}/photos")
    public Map<Long, String> showPhotosOfChosenSector(@PathVariable long sectorId) throws SectorNotFoundException {
        return climbingService.showPhotosOfChosenSector(sectorId);
    }


    @PostMapping("/route")
    public void createRoute(@RequestBody RouteCreateDto route) throws RouteNameForSectorAlreadyExistsException, ClimbingPlaceNotFoundException, SectorNotFoundException {
        climbingService.createRoute(route);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/uploadsectorfromfile")
    public void uploadsectorfromfile(@RequestBody MultipartFile file) throws IOException {
        climbingService.uploadsectorfromfile(file);
    }


    @GetMapping("/image/route/{imageId}")
    public ResponseEntity<byte[]> showPictureOfRoute(
            @PathVariable long imageId) throws ImageNotFoundException {
        return climbingService.showPictureOfRoute(imageId);
    }


    @PostMapping("/{routeId}")
    public void verifyRouteByUser(@PathVariable long routeId) {
        climbingService.verifyRouteService(routeId);
    }


    @PostMapping("/{sectorId}/{routeId}")
    public List<SectorsShowDto> showSectorsByDistance(@RequestBody DistanceCheckerDto dto) {
        return climbingService.getSectorByDistance(dto);
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

