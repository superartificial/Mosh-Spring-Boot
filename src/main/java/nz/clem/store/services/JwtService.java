package nz.clem.store.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private final String secret = "secret";

    public String generateToken(String email) {
        final long tokenExpiration = 86_400; // 1 day
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
            .signWith(Keys.hmacShaKeyFor(("secret").getBytes()))
            .compact();
    }

}
