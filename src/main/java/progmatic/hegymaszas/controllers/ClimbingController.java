package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.ClimbingPlaceDto;
import progmatic.hegymaszas.dto.RouteCreateDto;
import progmatic.hegymaszas.dto.SectorDto;
import progmatic.hegymaszas.services.ClimbingService;

import java.util.List;

@RestController
public class ClimbingController {

    @Autowired
    private ClimbingService climbingService;


    @GetMapping("areas")
    public List<ClimbingPlaceDto> showClimbingPlaces() {
        return climbingService.showClimbingPlaces();
    }


    @GetMapping("areas/{climbingPlaceId}")
    public List<SectorDto> showSectorsOfClimbingPlace(@PathVariable(value = "climbingPlaceId") Integer id) {
        return climbingService.showSectorsOfClimbingPlace(id);
    }


    @GetMapping("areas/route")
    public RouteCreateDto showCreateRoute() {
        return new RouteCreateDto();
    }


    @PostMapping("areas/route")
    public void createRoute(@RequestParam("route") RouteCreateDto route) throws Exception {
        climbingService.createRoute(route);
    }
}

