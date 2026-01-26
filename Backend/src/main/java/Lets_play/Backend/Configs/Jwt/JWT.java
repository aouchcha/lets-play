package Lets_play.Backend.Configs.Jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;

@Component
public class JWT {
    private final SecretKey secretKey;
    private final long experationTime;

    public JWT(@Value("${jwtKey}") String Secret, @Value("{jwtExperation}") Long experationTime) {
        this.experationTime = experationTime;
        this.secretKey = Keys.hmacShaKeyFor(Secret.getBytes());
    }

    public String GenerateToken(@NonNull String Username, Role role) {
        return Jwts
                .builder()
                .subject(Username)
                .claim("role", role)
                .expiration(new Date(System.currentTimeMillis() + experationTime))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        if (token == null || token.isEmpty())
            return false;
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Role getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",
                Role.class);
    }
}
