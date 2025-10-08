package com.prabhas.model.token;

import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "myverysecretkeymyverysecretkey12345";
    private static final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hour
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails user) {
        try {
            String username = extractUsername(token);
            return username.equals(user.getUsername()) && !extractExpiration(token).before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
}