package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.CreateRouteDto;
import progmatic.hegymaszas.dto.RouteDto;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.enums.Orientation;
import progmatic.hegymaszas.services.RouteService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/")
public class RestRouteController {

    private RouteService routeService;

    @Autowired
    public void RestController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RouteDto> allRoute() {
        List<Route> routes = routeService.getAllRoute();
        List<RouteDto> routeDtos = new ArrayList<>();

        for (Route route : routes) {
            RouteDto routeDto = new RouteDto();
            routeDto.setName(route.getName());
            routeDto.setGrade(String.valueOf(route.getGrade()));
            routeDto.setId(route.getId());
            routeDtos.add(routeDto);
        }
        return routeDtos;
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Route> filteredRoutes(@RequestParam(name = "grade", defaultValue = "", required = false) String grade,
                                      @RequestParam(name = "routeName", defaultValue = "", required = false) String routeName,
                                      @RequestParam(name = "climbingPlaceName", defaultValue = "", required = false) String climbingPlaceName,
                                      @RequestParam(name = "beautyRating", defaultValue = "1", required = false) int beautyRating,
                                      @RequestParam(name = "difficultyRating", defaultValue = "1", required = false) int difficultyRating,
                                      @RequestParam(name = "safetyRating", defaultValue = "1", required = false) int safetyRating,
                                      @RequestParam(name = "orientation", defaultValue = "", required = false) Orientation orientation) {

        return routeService.loadFilteredRoutes(grade, routeName, climbingPlaceName, beautyRating, difficultyRating, safetyRating, orientation);
    }
}