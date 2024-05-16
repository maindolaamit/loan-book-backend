package org.hayo.finance.loanbook.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class JwtService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    public LocalDateTime getExpirationDateFromToken(String token) {
        val expiration = getClaimFromToken(token).getExpiration();
        return expiration.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean isTokenExpired(String token) {
        final LocalDateTime expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }

    private Claims getClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(String username) {
        return doGenerateToken(username);
    }

    private String doGenerateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
