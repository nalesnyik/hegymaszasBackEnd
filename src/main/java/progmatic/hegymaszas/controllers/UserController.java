package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.MyUserDto;
import progmatic.hegymaszas.services.UserService;

import javax.validation.Valid;
import java.time.LocalDate;

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
