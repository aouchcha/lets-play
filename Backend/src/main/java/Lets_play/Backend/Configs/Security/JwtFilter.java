package Lets_play.Backend.Configs.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import Lets_play.Backend.Configs.Jwt.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final Jwt jwt;

    public JwtFilter(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        System.out.println(jwt.validateToken(token));
        if (token != null && jwt.validateToken(token)) {
            // System.out.println("TOOOOOOOOOOOOOKEN ===========~" + token);
            String username = jwt.getUsername(token);
            String role = jwt.getRole(token);
            System.out.println(role);

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (role != null && !role.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            System.out.println(authorities.get(0));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {
        // System.out.println("Hanniiiiiiiiiiiiiiiiiiiiiiiiiiii");
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
