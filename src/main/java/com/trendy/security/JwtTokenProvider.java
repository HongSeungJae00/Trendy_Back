package com.trendy.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

    private Key secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("JWT secret key initialized");
    }


    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();

        log.debug("Created token for user: {}, expires at: {}", username, validity);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            if (token == null) {
                log.warn("Token is null");
                return false;
            }

            log.debug("Validating token...");
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

            // 만료 시간 확인
            Date expiration = getExpiration(token);
            boolean isValid = expiration.after(new Date());
            
            log.debug("Token validation result: {}, expires at: {}", isValid, expiration);
            return isValid;

        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("JWT validation error", e);
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String username = claims.getSubject();
            log.debug("Extracted username from token: {}", username);
            return username;

        } catch (Exception e) {
            log.error("Error extracting username from token", e);
            return null;
        }
    }

    private Date getExpiration(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
    }
}
