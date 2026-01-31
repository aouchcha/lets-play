package Lets_play.Backend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Lets_play.Backend.Model.User;
import Lets_play.Backend.Services.userService;

@RestController
@RequestMapping("/api/users")
public class Users {

    private final userService userService;

    public Users(userService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> GetUsers() {
        return userService.users();
    }
    
}
