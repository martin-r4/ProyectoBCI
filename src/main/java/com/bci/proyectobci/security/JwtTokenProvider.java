package com.bci.proyectobci.security;

import com.bci.proyectobci.exception.TokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${key.jwt-secret}")
    private String jwtSecret;

    @Value("${key.jwt-expiration}")
    private int jwtExpiration;

    public String generateTokens(String name) {
        String username = name;
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new TokenException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new TokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new TokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new TokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new TokenException("JWT claims string is empty.");
        }
    }

}
