package Lets_play.Backend.Configs.Jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;

@Component
public class Jwt {
    private final SecretKey secretKey;
    private final long experationTime;

    public Jwt(@Value("${jwtKey}") String Secret, @Value("${jwtExperation}") Long experationTime) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(experationTime);
        System.out.println("--------------------------------------------------------------------------------------");
        this.experationTime = experationTime;
        this.secretKey = Keys.hmacShaKeyFor(Secret.getBytes());
    }

    public String GenerateToken(@NonNull String Username, String role) {
        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + experationTime);

        System.out.println("=== TOKEN CREATION ===");
        System.out.println("Created at: " + now);
        System.out.println("Expires at: " + expiry);
        System.out.println("Expiration time (ms): " + experationTime);
        System.out.println("======================");
        return Jwts
                .builder()
                .subject(Username)
                .claim("role", role.replace("ROLE_", ""))
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
            System.err.println(e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",
                String.class);
    }
}
