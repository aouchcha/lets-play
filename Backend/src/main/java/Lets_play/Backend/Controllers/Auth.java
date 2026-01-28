package Lets_play.Backend.Controllers;

import java.util.Collection;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Lets_play.Backend.Configs.Jwt.Jwt;
import Lets_play.Backend.Configs.Jwt.Role;
import Lets_play.Backend.DTO.LoginDTO;
import Lets_play.Backend.DTO.RegisterDTO;
import Lets_play.Backend.Model.User;
import Lets_play.Backend.Repository.userRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class Auth {
    private final AuthenticationManager authenticationManager;
    private final Jwt jwt;
    private final PasswordEncoder passwordEncoder;
    private final userRepository userRepository;

    public Auth(AuthenticationManager authenticationManager, Jwt jwt, PasswordEncoder passwordEncoder,
            userRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginDTO body) {
        System.out.println("Ana F login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.getUsername(),
                            body.getPassword()));
            // Extract UserDetails from authentication
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            // Get username
            String username = userDetails.getUsername();

            // Get roles/authorities
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // Extract role as string (first role)
            String role = authorities.stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("");

            // Or get role without "ROLE_" prefix
            String roleWithoutPrefix = role.replace("ROLE_", "");

            System.out.println("Username: " + username);
            System.out.println("Role: " + roleWithoutPrefix);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // String jj = jwt.GenerateToken(auth.getPrincipal(), auth.getAuthorities());
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterDTO body) {
        User new_User = new User();
        new_User.setUsername(body.getUsername());
        new_User.setEmail(body.getEmail());
        new_User.setPassword(this.passwordEncoder.encode(body.getPassword()));
        new_User.setRole(Role.User.toString());
        userRepository.save(new_User);
        return ResponseEntity.ok(null);
    }
}
