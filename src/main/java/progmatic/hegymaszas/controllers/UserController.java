package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import progmatic.hegymaszas.dto.MyUserChosenShowDto;
import progmatic.hegymaszas.dto.MyUserDto;
import progmatic.hegymaszas.services.UserService;

import javax.validation.Valid;
import java.io.IOException;

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


    @GetMapping("/user")
    public MyUserChosenShowDto showMyProfile() {
        return userService.showMyProfile();
    }


    @GetMapping("/user/me/picture")
    public ResponseEntity<byte[]> showProfilePicture() throws IOException {
        return userService.showProfilePicture();
    }

}
