package Lets_play.Backend.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Lets_play.Backend.DTO.LoginDTO;
import Lets_play.Backend.DTO.RegisterDTO;
import Lets_play.Backend.Services.LoginService;
import Lets_play.Backend.Services.RegisterService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class Auth {

    private final RegisterService registerService;
    private final LoginService loginService;

    public Auth(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody LoginDTO body) {
        return loginService.login(body);
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@Valid @RequestBody RegisterDTO body) {
        return registerService.register(body);
    }
}
