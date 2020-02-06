package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import progmatic.hegymaszas.dto.ClimbingPlaceDto;
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
}
