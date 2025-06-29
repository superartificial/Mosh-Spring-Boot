package nz.clem.store.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import nz.clem.store.users.Role;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {

    public Jwt(Claims claims, SecretKey secretKey) {
        this.secretKey = secretKey;
        this.claims = claims;
    }

    private final SecretKey secretKey;
    private final Claims claims;

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId() {
        return Long.valueOf( claims.getSubject() );
    }

    public Role getRole() {
        return Role.valueOf( claims.get("role", String.class) );
    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
