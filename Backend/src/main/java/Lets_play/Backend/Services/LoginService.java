package Lets_play.Backend.Services;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import Lets_play.Backend.Configs.Jwt.Jwt;
import Lets_play.Backend.DTO.LoginDTO;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final Jwt jwt;

    public LoginService(AuthenticationManager authenticationManager, Jwt jwt) {
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
    }

    public ResponseEntity<?> login(LoginDTO body) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.getUsername(),
                            body.getPassword()));
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String username = userDetails.getUsername();

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            String role = authorities.stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("");

            String token = jwt.GenerateToken(username, role);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token, "usernam", username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
