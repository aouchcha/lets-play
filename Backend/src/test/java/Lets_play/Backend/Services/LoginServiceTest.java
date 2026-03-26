package Lets_play.Backend.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import Lets_play.Backend.Configs.Jwt.Jwt;
import Lets_play.Backend.Configs.Jwt.Role;
import Lets_play.Backend.DTO.LoginDTO;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private LoginService loginService;


    private LoginDTO loginDTO;
    private UserDetails userDetails;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO();
        loginDTO.setUsername("aouchcha");
        loginDTO.setPassword("Achraf1303@@");
        Collection<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority(Role.User.toString())
        );
        userDetails = User.builder()
            .username("aouchcha")
            .password("Achraf1303@@")
            .authorities(authorities)
            .build();

        authentication = mock(Authentication.class);
    }

    @Test
    void login_With_Valid_Credientiels() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwt.GenerateToken("aouchcha", Role.User.toString())).thenReturn("TokenWaaaaaaaaaaaaaah");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        ResponseEntity<?> response = loginService.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals(body.get("token"), "TokenWaaaaaaaaaaaaaah");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwt).GenerateToken("aouchcha", Role.User.toString());
    }

    @Test
    void loginWith_Invalid_Username() {
        LoginDTO dto = new LoginDTO("test", "Achraf1303@@");
        ResponseEntity<?> response = loginService.login(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertNotNull(response.getBody());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwt, never()).GenerateToken("test", Role.User.toString());
    }

    @Test
    void loginWith_Invalid_Password() {
         LoginDTO dto = new LoginDTO("aouchcha", "Achraf1303");
        ResponseEntity<?> response = loginService.login(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertNotNull(response.getBody());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwt, never()).GenerateToken("aouchcha", Role.User.toString());
    }
}
