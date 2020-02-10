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


}
