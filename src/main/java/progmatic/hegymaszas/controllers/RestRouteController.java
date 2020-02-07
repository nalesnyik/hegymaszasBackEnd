package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import progmatic.hegymaszas.dto.CreateRouteDto;
import progmatic.hegymaszas.dto.RouteDto;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.services.RouteService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/route")
public class RestRouteController {

    private RouteService routeService;

    @Autowired
    public void RestController(RouteService routeService){
        this.routeService = routeService;
    }
/*
    @RequestMapping(method =  RequestMethod.GET)
    public List<Route> allRoutes(){
        List<Route> routes = routeService.getAllRoute();
        return routes;
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public List<RouteDto> allRoute(){
        List<Route> routes = routeService.getAllRoute();
        List<RouteDto> routeDtos = new ArrayList<>();

        for (Route route : routes) {
            RouteDto routeDto = new RouteDto();
            routeDto.setName(route.getName());
            routeDto.setGrade(route.getGrade());
            routeDto.setId(route.getId());

            routeDtos.add(routeDto);

        }
        return routeDtos;

    }

}
