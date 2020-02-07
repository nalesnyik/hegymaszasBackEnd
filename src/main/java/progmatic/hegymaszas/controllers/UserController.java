package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.dto.MyUserDto;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public void register(@Valid @RequestBody MyUserDto user) {
        userService.createUser(user);
    }

    @PostMapping(path = "/filter")
    public void filter(@RequestBody String grade, String name, int rating, int height, String climbingPlaceName) {
        List<Route> filteredRoutes = userService.loadFilteredRoutes(grade, name, rating, height, climbingPlaceName);
    }
}
