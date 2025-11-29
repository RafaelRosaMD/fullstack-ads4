// src/main/java/com/senac/full/infra/security/JwtService.java
package com.senac.full.infra.security;

import com.senac.full.domain.usuario.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String secret;
    private final long expirationMillis;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-millis:300000}") long expirationMillis // default 5 min
    ) {
        this.secret = secret;
        this.expirationMillis = expirationMillis;
    }

    private byte[] getSigningKey() {
        return secret.getBytes(StandardCharsets.UTF_8);
    }

    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("id", usuario.getId())
                .claim("role", usuario.getRole())
                .signWith(Keys.hmacShaKeyFor(getSigningKey()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractSubject(String token) {
        return getClaims(token).getBody().getSubject();
    }

    public boolean isValid(String token) {
        try {
            var claims = getClaims(token).getBody();
            Date exp = claims.getExpiration();
            return exp != null && exp.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSigningKey()))
                .build()
                .parseClaimsJws(token);
    }
}