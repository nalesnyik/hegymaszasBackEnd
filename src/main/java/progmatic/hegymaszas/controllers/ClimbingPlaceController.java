package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.services.ClimbingPlaceService;

import java.util.List;

@RestController
@RequestMapping("/climbingplace")
public class ClimbingPlaceController {

    private ClimbingPlaceService climbingPlaceService;

    @Autowired
    public void ClimbingPlaceController(ClimbingPlaceService climbingPlaceService){
        this.climbingPlaceService = climbingPlaceService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ClimbingPlace> allClimbingPlace(){
        List<ClimbingPlace> climbingPlaces = climbingPlaceService.getAllClimbingPlace();
        return climbingPlaces;
    }
}
